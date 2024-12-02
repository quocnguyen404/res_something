
package gui;

import listener.Event;
import listener.Listener;
import system.EventDispatcher;
import dto.response.UserResponse;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login application.App");

        // Set up grid layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Add login form components
        Label userLabel = new Label("Username:");
        TextField userTextField = new TextField();
        Label passLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");

        gridPane.add(userLabel, 0, 0);
        gridPane.add(userTextField, 1, 0);
        gridPane.add(passLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginButton, 1, 2);

        loginButton.setOnAction(event -> {
            String username = userTextField.getText();
            String password = passwordField.getText();

            EventDispatcher.invoke(Event.Login, username, password);
            Listener lis = EventDispatcher.getEvent(Event.Login);
            UserResponse user = (UserResponse)lis.getResponse().getData();
            
            System.out.println(lis.getResponse().getMessage());
            System.out.println(lis.getResponse().isOK());
            System.out.println(user);
        });

        Scene scene = new Scene(gridPane, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
