package controllers;

import domain.Station;
import domain.Train;
import domain.User;
import exceptions.ServiceException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketsController {


    private static final Logger logger= LogManager.getLogger();

    public TableView<Train> tableTrainsBought;
    public TableColumn<Train, Long> tableTrainsIdColumnBought;
    public TableColumn<Train,String> tableTrainsNameColumnBought;
    public TableColumn<Train, Integer> tableTrainsNumberOfSeatsColumnBought;
    private final ObservableList<Train> modelTrains = FXCollections.observableArrayList();

    public TableView<Train> tableTrains;
    public TableColumn<Train, Long> tableTrainsIdColumn;
    public TableColumn<Train, String> tableTrainsNameColumn;
    public TableColumn<Train, Integer> tableTrainsNumberOfSeatsColumn;
    private final ObservableList<Train> modelTrainsBought = FXCollections.observableArrayList();
    public DatePicker datePicker;

    List<Train> trainsBooked = new ArrayList<>();

    public Label errorMessageLabel;
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

        tableTrainsIdColumnBought.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableTrainsNameColumnBought.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableTrainsNumberOfSeatsColumnBought.setCellValueFactory(new PropertyValueFactory<>("numberOfSeats"));

        tableTrainsBought.setItems(modelTrainsBought);
        tableTrains.setItems(modelTrains);

        logger.traceExit();
    }


    public void init(){
        modelTrains.clear();
        modelTrains.setAll(service.getAllTrains());
    }

    public void onAddToCart(ActionEvent actionEvent) {
        trainsBooked.add(tableTrains.getSelectionModel().getSelectedItem());
        modelTrainsBought.add(tableTrains.getSelectionModel().getSelectedItem());
    }

    public void onBook(ActionEvent actionEvent) {
        errorMessageLabel.setVisible(false);
        Date date = java.util.Date.from(datePicker
                .getValue()
                .atStartOfDay(ZoneId.of( "America/Montreal" ))
                .toInstant());

        try{
            service.bookTickets(user, trainsBooked, date);
            errorMessageLabel.setVisible(true);
            errorMessageLabel.setText("Tickets booked successfully!");
        }
        catch(ServiceException e){
            errorMessageLabel.setText(e.getMessage());
            errorMessageLabel.setVisible(true);
        }

        modelTrainsBought.clear();
    }
}
