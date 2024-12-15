package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import listener.Event;
import system.EventDispatcher;
import dto.request.AuthRequest;


public class LoginGUI extends Application {
    private Stage childStage;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login Application");

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
        Button registerButton = new Button("Register");

        gridPane.add(userLabel, 0, 0);
        gridPane.add(userTextField, 1, 0);
        gridPane.add(passLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginButton, 1, 2);
        gridPane.add(registerButton, 0, 2);

        loginButton.setOnAction(event -> {
            String username = userTextField.getText();
            String password = passwordField.getText();

            AuthRequest authRequest = new AuthRequest(username, password);
            EventDispatcher.invoke(Event.Authenticate, authRequest);
            EventDispatcher.invoke(Event.HandleLogin);
        });

        childStage = new Stage();
        registerButton.setOnAction(event -> {
            EventDispatcher.invoke(Event.RegisterUI, childStage);
        });

        Scene scene = new Scene(gridPane, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
