package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import listener.Event;
import system.EventDispatcher;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.text.Utilities;

import dto.request.UserRequest;

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

        // Change roleComboBox to only have "EMPLOYEE"
        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().add("EMPLOYEE"); // Only allow "EMPLOYEE"
        roleComboBox.setValue("EMPLOYEE"); // Default role is "EMPLOYEE"

        Button submitButton = new Button("Submit");
        Button backButton = new Button("Back to Login");

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
            // String role = roleComboBox.getValue(); // Get selected role (should always be "EMPLOYEE")
            boolean valid = utilities.AppUtilities.validateUserName(username);
            valid = valid && utilities.AppUtilities.validatePassword(password);
            valid = valid && password.equals(confirmPassword);

            if (valid) {
                UserRequest userRequest = new UserRequest();
                userRequest.setUserName(username);
                userRequest.setPassword(password);

                EventDispatcher.invoke(Event.Register, userRequest);
                EventDispatcher.invoke(Event.HandleRegister);
                primaryStage.close(); // Close register window
            } else {
                UIUtilities.showAlert("Registration Error", "Not valid password or user name", AlertType.ERROR);
            }
        });

        backButton.setOnAction(event -> {
            EventDispatcher.invoke(Event.LoginUI, new Stage());
            try {
                this.stop();
                primaryStage.close(); // Close register window
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Scene scene = new Scene(gridPane, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Save user to file with role
    private void saveUser(String username, String password, String role) {
        String directoryPath = "D:\\demo (4)\\res_something\\user_data.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(directoryPath, true))) {
            writer.write(username + ":" + password + ":" + role);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
