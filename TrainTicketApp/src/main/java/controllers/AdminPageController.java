package controllers;

import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.Service;

import java.util.ArrayList;

public class AdminPageController {

    private static final Logger logger= LogManager.getLogger();

    public TableView<Train> tableTrains;
    public TableColumn<Train, Long> tableTrainsIdColumn;
    public TableColumn<Train, String> tableTrainsNameColumn;
    public TableColumn<Train, Integer> tableTrainsNumberOfSeatsColumn;
    private final ObservableList<Train> modelTrains = FXCollections.observableArrayList();

    public TextField numberOfSeatsTrainInput;
    public TextField nameTrainInput;
    public TextField idTrainInput;

    public TableView<Booking> tableBookings;
    public TableColumn<Booking, Long> tableBookingsIdColumn;
    public TableColumn<Booking, Long> tableBookingsUserIdColumn;
    public TableColumn<Booking, Integer> tableBookingsPriceColumn;
    private final ObservableList<Booking> modelBookings = FXCollections.observableArrayList();

    public TableView<Route> tableRoutes;
    public TableColumn<Route, Long> tableRoutesIdColumn;
    public TableColumn<Route, String> tableRoutesNameColumn;
    private final ObservableList<Route> modelRoutes = FXCollections.observableArrayList();

    public TextField idRouteInput;
    public TextField nameRouteInput;

    public TableView<Station> tableStation;
    public TableColumn<Station, Long> tableStationIdColumn;
    public TableColumn<Station, String> tableStationNameColumn;
    private final ObservableList<Station> modelStations = FXCollections.observableArrayList();

    public Label errorMessageLabel;
    public TextField delayInput;
    public TextField hourOfArrivalInput;
    public TextField hourOfDepartureInput;
    private Service service;
    private User user;

    public void setService(Service service, User mainUser) {
        this.service = service;
        this.user = mainUser;
        init();
    }

    @FXML
    public void initialize() {
        logger.traceEntry("initialize");

        tableTrainsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableTrainsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableTrainsNumberOfSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfSeats"));

        tableStationIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableStationNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        tableBookingsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableBookingsUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        tableBookingsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableRoutesIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableRoutesNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        tableTrains.setItems(modelTrains);
        tableBookings.setItems(modelBookings);
        tableRoutes.setItems(modelRoutes);
        tableStation.setItems(modelStations);

        tableTrains.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            modelBookings.clear();
            modelBookings.addAll(service.getAllBookings());
        });

        logger.traceExit();
    }

    private void init() {
        logger.traceEntry("init");

        modelTrains.clear();
        modelTrains.addAll(service.getAllTrains());

        modelStations.clear();
        modelStations.addAll(service.getAllStations());

        modelRoutes.clear();
        modelRoutes.addAll(service.getAllRoutes());

    }

    public void onAddTrain(ActionEvent actionEvent) {
        String name = nameTrainInput.getText();
        int numberOfSeats = Integer.parseInt(numberOfSeatsTrainInput.getText());
        Route route = tableRoutes.getSelectionModel().getSelectedItem();
        Train train = new Train(name, route, numberOfSeats);
        service.addTrain(train);
    }

    public void onUpdateTrain(ActionEvent actionEvent) {
        long id = Long.parseLong(idTrainInput.getText());
        String name = nameTrainInput.getText();
        int numberOfSeats = Integer.parseInt(numberOfSeatsTrainInput.getText());
        Route route = tableRoutes.getSelectionModel().getSelectedItem();
        Train train = new Train(id, name, route, numberOfSeats);
        service.updateTrain(train);
    }

    public void onRemoveTrain(ActionEvent actionEvent) {
        Train train = tableTrains.getSelectionModel().getSelectedItem();
        service.deleteTrain(train);
    }

    public void onAddStationToRoute(ActionEvent actionEvent) {
        Route route = tableRoutes.getSelectionModel().getSelectedItem();
        Station station = tableStation.getSelectionModel().getSelectedItem();
        int arrivalTime = Integer.parseInt(hourOfArrivalInput.getText());
        int departureTime = Integer.parseInt(hourOfDepartureInput.getText());

        service.addStationToRoute(route, station, arrivalTime, departureTime);
    }

    public void onNotifyTrainDelay(ActionEvent actionEvent) {
        Train train = tableTrains.getSelectionModel().getSelectedItem();
        int delay = Integer.parseInt(delayInput.getText());
        service.notifyTrainDelay(train, delay);
    }

    public void onAddRoute(ActionEvent actionEvent) {
        String name = nameRouteInput.getText();
        Route route = new Route(name);
        service.addRoute(route);
    }

    public void onUpdateRoute(ActionEvent actionEvent) {
        Route route = tableRoutes.getSelectionModel().getSelectedItem();
        long id = Long.parseLong(idRouteInput.getText());
        String name = nameRouteInput.getText();
        Route updatedRoute = new Route(id,name, route.getStationRoutes());
        service.updateRoute(updatedRoute);
    }

    public void onRemoveRoute(ActionEvent actionEvent) {
        Route route = tableRoutes.getSelectionModel().getSelectedItem();
        service.deleteRoute(route);
    }


    public void onRefresh(ActionEvent actionEvent) {
        init();
    }
}
