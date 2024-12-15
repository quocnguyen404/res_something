package gui;

import dto.request.ChangePasswordRequest;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import listener.Event;
import listener.Listener;
import system.EventDispatcher;
import system.SystemUser;

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
            
            EventDispatcher.invoke(Event.GetSystemUser);
            Listener listener = EventDispatcher.getListener(Event.GetSystemUser);
            SystemUser user = listener.getData();
            listener.clearData();
            
            ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(user.getUserName(), currentPassword, newPassword, confirmPassword);
            EventDispatcher.invoke(Event.ChangePassword, changePasswordRequest);
            EventDispatcher.invoke(Event.HandleChangePassword);

            stage.close();
            try {
                this.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Scene scene = new Scene(gridPane, 400, 200);
        stage.setScene(scene);
        stage.show();
    }

    // private void handleLogin
}
