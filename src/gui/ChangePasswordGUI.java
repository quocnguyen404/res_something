package gui;

import dto.request.ChangePasswordRequest;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import listener.Event;
import system.EventDispatcher;

public class ChangePasswordGUI extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Change Password");

        // Grid layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Components
        Label currentPassLabel = new Label("Current Password:");
        PasswordField currentPasswordField = new PasswordField();
        Label newPassLabel = new Label("New Password:");
        PasswordField newPasswordField = new PasswordField();
        Label confirmPassLabel = new Label("Confirm New Password:");
        PasswordField confirmPasswordField = new PasswordField();

        Button submitButton = new Button("Submit");

        gridPane.add(currentPassLabel, 0, 0);
        gridPane.add(currentPasswordField, 1, 0);
        gridPane.add(newPassLabel, 0, 1);
        gridPane.add(newPasswordField, 1, 1);
        gridPane.add(confirmPassLabel, 0, 2);
        gridPane.add(confirmPasswordField, 1, 2);
        gridPane.add(submitButton, 1, 3);


        submitButton.setOnAction(event -> {
            String currentPassword = currentPasswordField.getText();
            String newPassword = newPasswordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if(!UIUtilities.validateInput(currentPassword) || !UIUtilities.validateInput(newPassword) || !UIUtilities.validateInput(confirmPassword)) {
                UIUtilities.showAlert("Information", "Please fill all field", AlertType.INFORMATION);
                return;
            }
            ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(null, currentPassword, newPassword, confirmPassword);
            EventDispatcher.invoke(Event.HandleChangePassword, changePasswordRequest);
        });

        Scene scene = new Scene(gridPane, 400, 200);
        stage.setScene(scene);
        stage.show();
    }

    // private void handleLogin
}
