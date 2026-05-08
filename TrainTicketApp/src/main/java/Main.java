import controllers.LogInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repositories.*;
import services.Service;

public class Main extends Application{

    @Override
    public void start(Stage stage) throws Exception {

        UserRepositoryInterface userRepository = new UserRepository();
        StationRepositoryInterface stationRepository = new StationRepository();
        RouteRepositoryInterface routeRepository = new RouteRepository();
        TrainRepositoryInterface trainRepository = new TrainRepository();
        TicketRepositoryInterface ticketRepository = new TicketRepository();
        BookingRepositoryInterface bookingRepository = new BookingRepository();

        Service service = new Service(
                userRepository,
                stationRepository,
                routeRepository,
                trainRepository,
                ticketRepository,
                bookingRepository
        );

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Admin");
        stage.setScene(scene);

        LogInController logInController = fxmlLoader.getController();
        logInController.setService(service);
        stage.show();

    }
}