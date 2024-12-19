package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import system.EventDispatcher;
import listener.Event;

public class UserGUI extends Application {
    private Stage childStage;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("User Management");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Button createOrderButton = new Button("Create Order");
        Button createFeedbackButton = new Button("Create feedback");
        Button changePasswordButton = new Button("Change Password");
        Button checkOutButton = new Button("Checkout");

        gridPane.add(createOrderButton, 0, 0);
        gridPane.add(createFeedbackButton, 0, 1);
        gridPane.add(changePasswordButton, 0, 2);
        gridPane.add(checkOutButton, 0, 3);
        
        childStage = new Stage();
        createOrderButton.setOnAction(e -> {
           EventDispatcher.invoke(Event.OrderUI, childStage);
        });

        createFeedbackButton.setOnAction(e -> {
            EventDispatcher.invoke(Event.CreateFeedbackUI, childStage);
        });

        changePasswordButton.setOnAction(e -> {
            EventDispatcher.invoke(Event.ChangePasswordUI, childStage);
        });

        checkOutButton.setOnAction(e -> {
            EventDispatcher.invoke(Event.HandleCheckOut);
        });

        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
