package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import listener.Event;
import listener.Listener;
import system.EventDispatcher;
import system.SystemUser;

import javax.management.relation.Role;

import dto.request.UserRequest;
import dto.response.ResponseWrapper;
import dto.response.UserResponse;

public class RegisterGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Register Application");

        // Set up grid layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Add register form components
        Label userLabel = new Label("Username:");
        TextField userTextField = new TextField();
        Label passLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Label confirmPassLabel = new Label("Confirm Password:");
        PasswordField confirmPasswordField = new PasswordField();
        Label roleLabel = new Label("Role:");

        ComboBox<String> roleComboBox = new ComboBox<>();
        UIUtilities.handleRole(roleComboBox);

        Button submitButton = new Button("Submit");
        Button backButton = new Button("Back");

        gridPane.add(userLabel, 0, 0);
        gridPane.add(userTextField, 1, 0);
        gridPane.add(passLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(confirmPassLabel, 0, 2);
        gridPane.add(confirmPasswordField, 1, 2);
        gridPane.add(roleLabel, 0, 3);
        gridPane.add(roleComboBox, 1, 3);
        gridPane.add(submitButton, 0, 4);
        gridPane.add(backButton, 1, 4);

        submitButton.setOnAction(event -> {
            String username = userTextField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            String role = roleComboBox.getSelectionModel().getSelectedItem();

            boolean valid = UIUtilities.validateUserName(username);
            valid = valid && UIUtilities.validatePassword(password);
            valid = valid && password.equals(confirmPassword);

            if (valid) {
                UserRequest userRequest = new UserRequest();
                userRequest.setUserName(username);
                userRequest.setPassword(password);
                userRequest.setRole(dao.Role.valueOf(role));

                EventDispatcher.invoke(Event.Register, userRequest);
                EventDispatcher.invoke(Event.HandleRegister);
                primaryStage.close(); // Close register window
            } else {
                UIUtilities.showAlert("Registration Error", "Not valid password or user name", AlertType.ERROR);
            }
        });

        backButton.setOnAction(event -> {
            primaryStage.close(); // Close register window
            try {
                this.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Scene scene = new Scene(gridPane, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
