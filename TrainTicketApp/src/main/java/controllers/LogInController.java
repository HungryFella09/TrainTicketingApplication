package controllers;

import domain.User;
import domain.UserRole;
import exceptions.RepositoryException;
import exceptions.ServiceException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.Service;

import java.io.IOException;

public class LogInController {

    public TextField textFieldUsername;
    public PasswordField passwordField;
    public Label errorMessageLabel;

    private Service service;

    public void setService(Service service){
        this.service=service;
    }

    public void onLogin(ActionEvent actionEvent) {

        String username = textFieldUsername.getText();
        String password = passwordField.getText();

        try {
            User u = service.logIn(username, password);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            var stage = new Stage();
            stage.setTitle("Duck Social Network");
            stage.setScene(scene);

            MainPageController controller = fxmlLoader.getController();
            controller.setService(service, stage, u);
            stage.show();

        }
        catch (ServiceException | RepositoryException e) {
            errorMessageLabel.setText(e.getMessage());
            errorMessageLabel.setVisible(true);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
