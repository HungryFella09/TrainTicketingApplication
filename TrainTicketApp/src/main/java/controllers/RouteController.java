package controllers;

import domain.RouteCalculationResponse;
import domain.Station;
import domain.Train;
import domain.User;
import exceptions.RepositoryException;
import exceptions.ServiceException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.Service;

public class RouteController {

    private static final Logger logger= LogManager.getLogger();

    public TableView<Station> tableDeparture;
    public TableColumn<Station, Long> tableDepartureIdColumn;
    public TableColumn<Station, String> tableDepartureNameColumn;
    private final ObservableList<Station> modelDepartureStations = FXCollections.observableArrayList();


    public TableView<Station> tableArrival;
    public TableColumn<Station, Long> tableArrivalIdColumn;
    public TableColumn<Station, String> tableArrivalNameCsolumn;
    private final ObservableList<Station> modelArrivalStations = FXCollections.observableArrayList();

    public Label startTimeLabel;
    public Label endTimeLabel;

    public TableView<Train> tableTrains;
    public TableColumn<Train,Long> tableTrainsIdColumn;
    public TableColumn<Train, String> tableTrainsNameColumn;
    public TableColumn<Train, Integer> tableTrainsNumberOfSeatsColumn;
    private final ObservableList<Train> modelTrains = FXCollections.observableArrayList();
    public Label errorMessageLabel;

    private Service service;
    private User user;


    @FXML
    public void initialize() {
        logger.traceEntry("initialize");

        tableDepartureIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableDepartureNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableArrivalIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableArrivalNameCsolumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        tableTrainsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableTrainsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableTrainsNumberOfSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfSeats"));

        tableDeparture.setItems(modelDepartureStations);
        tableArrival.setItems(modelArrivalStations);
        tableTrains.setItems(modelTrains);

        logger.traceExit();
    }


    public void setService(Service service, User mainUser) {
        this.service = service;
        this.user = mainUser;
        initTablesStations();
    }


    public void initTablesStations(){
        logger.traceEntry("initTablesStations");
        modelArrivalStations.clear();
        modelArrivalStations.addAll(service.getAllStations());
        modelDepartureStations.clear();
        modelDepartureStations.addAll(service.getAllStations());
        logger.traceExit();

    }

    public void onCompute(ActionEvent actionEvent) {
        errorMessageLabel.setVisible(false);

        try{
            Station departureStation = tableDeparture.getSelectionModel().getSelectedItem();
            Station arrivalStation = tableArrival.getSelectionModel().getSelectedItem();

            RouteCalculationResponse routeCalculationResponse = service.calculateRoute(departureStation, arrivalStation);
            modelTrains.clear();
            modelTrains.addAll(routeCalculationResponse.getTrainsToTake());

            startTimeLabel.setText(String.valueOf(routeCalculationResponse.getDepartureTime()));
            endTimeLabel.setText(String.valueOf(routeCalculationResponse.getArrivalTime()));

        }
        catch(ServiceException | RepositoryException e){
            errorMessageLabel.setText(e.getMessage());
            errorMessageLabel.setVisible(true);
        }
    }
}
