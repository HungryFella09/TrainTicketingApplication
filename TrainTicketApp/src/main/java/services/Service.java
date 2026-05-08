package services;

import domain.*;
import exceptions.RepositoryException;
import exceptions.ServiceException;
import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repositories.*;
import utils.PasswordEncryptor;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Service {

    private UserRepositoryInterface userRepository;
    private StationRepositoryInterface stationRepository;
    private RouteRepositoryInterface routeRepository;
    private TrainRepositoryInterface trainRepository;
    private TicketRepositoryInterface ticketRepository;
    private BookingRepositoryInterface bookingRepository;

    private static final Logger logger= LogManager.getLogger();

    public Service(UserRepositoryInterface userRepository,
                   StationRepositoryInterface stationRepository,
                   RouteRepositoryInterface routeRepository,
                   TrainRepositoryInterface trainRepository,
                   TicketRepositoryInterface ticketRepository,
                   BookingRepositoryInterface bookingRepository) {
        this.userRepository = userRepository;
        this.stationRepository = stationRepository;
        this.routeRepository = routeRepository;
        this.trainRepository = trainRepository;
        this.ticketRepository = ticketRepository;
        this.bookingRepository = bookingRepository;
    }

    public User logIn(String username, String password) {
        User user = userRepository.findUserByUsername(username);

        password = PasswordEncryptor.encryptPassword(password);
        if(!user.getPassword().equals(password)){
            throw new ServiceException("Wrong password");
        }

        return user;
    }

    public List<Station> getAllStations(){
        return stationRepository.findAll();
    }

    public RouteCalculationResponse calculateRoute(Station startStation, Station endStation){

        List<Route> startingRoutes = routeRepository.routesOfStation(startStation);

        logger.trace(startingRoutes.toString());

        //first we search for a direct route and return the first one
        for(Route route : startingRoutes){
            int startTime = -1;
            int endTime = -1;
            for (StationRoute stationRoute : route.getStationRoutes()){
                if (stationRoute.getStation().equals(startStation)){
                    startTime=stationRoute.getHourOfDeparture();
                    break;
                }
            }
            logger.trace(startTime);
            for (StationRoute stationRoute : route.getStationRoutes()){
                //we need to check that the station we arrive at is after the station we came from
                if (stationRoute.getStation().equals(endStation) && stationRoute.getHourOfArrival() > startTime){
                    endTime=stationRoute.getHourOfArrival();
                }
            }
            logger.trace(endTime);
            if (endTime != -1 && startTime != -1){
                List<Train> trains = trainRepository.getAllByRoute(route);
                logger.trace(trains.toString());
                if(!trains.isEmpty()){
                    List<Train> trainsToTake = new ArrayList<>();
                    trainsToTake.add(trains.get(0));
                    return new RouteCalculationResponse(startTime, endTime, trainsToTake);
                }
            }
        }

        //if none are found then we try to find 2 routes that can be used to connect the 2 stations

        List<Route> endRoutes =  routeRepository.routesOfStation(endStation);
        //we check routes that can be merged with another route

        Map<Station, StationRoute> stationsReachableFromStart = new HashMap<>();

        for(Route route : startingRoutes){
            int startTime = -1;
            //this is the time this route reaches the starting point
            for (StationRoute stationRoute : route.getStationRoutes()){
                if (stationRoute.getStation().equals(startStation)){
                    startTime=stationRoute.getHourOfDeparture();
                    break;
                }
            }
            //we will only look at stations after this point
            for (StationRoute stationRoute : route.getStationRoutes()){
                if(stationRoute.getHourOfArrival() > startTime){
                    if(stationsReachableFromStart.containsKey(stationRoute.getStation())){
                        if (stationsReachableFromStart.get(stationRoute.getStation()).getHourOfArrival()
                                < stationRoute.getHourOfArrival()){
                            //aka we retain the route that takes us here at the earliest time
                            stationsReachableFromStart.put(stationRoute.getStation(), stationRoute);
                        }
                    }
                    else {
                        stationsReachableFromStart.put(stationRoute.getStation(), stationRoute);
                    }
                }
            }
        }



        // we have a map of stations reachable from the starting point
        // with the routes that take them at the earliest time
        for(Route route : endRoutes){
            int endTime = -1;
            for (StationRoute stationRoute : route.getStationRoutes()){
                if (stationRoute.getStation().equals(endStation)){
                    endTime=stationRoute.getHourOfDeparture();
                    break;
                }
            }

            // we find out when this route reaches the end point and check the stations it visits beforehand
            for (StationRoute stationRoute : route.getStationRoutes()){
                if (stationRoute.getHourOfArrival() < endTime){

                    //the stations the route reaches before the end time
                    if(stationsReachableFromStart.containsKey(stationRoute.getStation())) {

                        //if this station is relevant to the ones we can reach from the start
                        if (stationsReachableFromStart.get(stationRoute.getStation()).getHourOfArrival() < stationRoute.getHourOfDeparture()) {
                            //only if it is possible to reach the train
                            //this means we have found our 2 routes
                            Route routeStart = routeRepository.findOne(stationsReachableFromStart.get(stationRoute.getStation()).getRouteId());
                            Route routeEnd = route;
                            int startTime = -1;


                            //we find the start time of the original route
                            for (StationRoute sr : routeStart.getStationRoutes()) {
                                if (sr.getStation().equals(startStation)) {
                                    startTime = sr.getHourOfDeparture();
                                    break;
                                }
                            }
                            Train t1 = trainRepository.getAllByRoute(routeStart).get(0);
                            Train t2 = trainRepository.getAllByRoute(routeEnd).get(0);
                            List<Train> trainsToTake = new ArrayList<>();
                            trainsToTake.add(t1);
                            trainsToTake.add(t2);

                            return new RouteCalculationResponse(startTime, endTime, trainsToTake);
                        }
                    }
                }
            }

        }


        throw new ServiceException("Route not found");
    }

    public List<Train> getAllTrains(){
        return trainRepository.findAll();
    }

    public void bookTickets(User user, List<Train> trainsToBook, Date date){

        Booking booking = new Booking();
        Map<Long, Ticket> ticketsMap = new HashMap<>();
        for (Train train : trainsToBook){
            if (!ticketsMap.containsKey(train.getId())){
                ticketsMap.put(train.getId(), new Ticket(train, booking, 1));
            }
            else{
                ticketsMap.get(train.getId()).setNoTickets(ticketsMap.get(train.getId()).getNoTickets() + 1);
            }
        }

        List<Ticket> tickets = new ArrayList<>();
        for (Ticket ticket : ticketsMap.values()){
            long no_seats = ticketRepository.countByTrainAndDate(ticket.getTrain(), date);
            if (ticket.getNoTickets() + no_seats > ticket.getTrain().getNumberOfSeats()){
                throw new ServiceException("There are not enough seats available for train: " + ticket.getTrain().getName());
            }
            tickets.add(ticket);
        }

        booking.setTickets(tickets);
        booking.setDayOfTrip(date);
        booking.setUserId(user.getId());
        booking.setPrice(trainsToBook.size() * 20);
//
//
//        bookingRepository.save(booking);
//        for(Ticket ticket : tickets){
//            ticketRepository.save(ticket);
//        }

        String message = "Successfully booked tickets!\n" + booking.toString();

        try {
            sendMail(user.getEmail(), message);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }

    }


    public void sendMail(String userMail, String messageToSend) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.mail.yahoo.com");
        props.put("mail.smtp.port", "587");


        Properties emailprops=new Properties();
        try {
            emailprops.load(new FileReader("D:\\school\\practice_for_internships\\siemens\\TrainTicketingApplication\\TrainTicketApp\\src\\main\\resources\\email.properties"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailprops.getProperty("email"), emailprops.getProperty("emailpassword"));
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(emailprops.getProperty("email")));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(userMail));
        message.setSubject("From CFR");

        message.setText(messageToSend);

        Transport.send(message);
        System.out.println("Mail successfully sent!");

    }
}

