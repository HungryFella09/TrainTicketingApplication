package controllers;

import domain.User;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.Service;

public class MainPageController {

    private static final Logger logger= LogManager.getLogger();
    private Service service;
    private Stage stage;
    private User user;

    @FXML
    private TicketsController TicketsController;

    @FXML
    private RouteController RouteController;

    public void setService(Service service, Stage stage, User mainUser) {
        this.service = service;
        this.stage = stage;
        this.user = mainUser;

        TicketsController.setService(service, mainUser);
        RouteController.setService(service, mainUser);
    }




}
