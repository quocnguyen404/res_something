package gui;

import javafx.scene.control.*;
import system.EventDispatcher;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import listener.Event;


public class ManagerManagementGUI extends Application {
    private Stage childStage;
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Manager Management");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Button manageUsersButton = new Button("Manage Users");
        Button manageDishesButton = new Button("Manage Dishes");
        Button viewFeedbackButton = new Button("View Feedback");
        Button createOrderButton = new Button("Create Order");
        Button viewAttendanceButton = new Button("View Attendance");
        Button changePasswordButton = new Button("Change Password");

        gridPane.add(new Label("Manage Users and Dishes"), 0, 0);
        gridPane.add(manageUsersButton, 0, 1);
        gridPane.add(manageDishesButton, 1, 1);
        gridPane.add(viewFeedbackButton, 0, 2);
        gridPane.add(changePasswordButton, 0, 3);
        gridPane.add(createOrderButton, 1, 2);
        gridPane.add(viewAttendanceButton, 1, 3);

        childStage = new Stage();

        manageUsersButton.setOnAction(e -> {
            EventDispatcher.invoke(Event.UserManagementUI, childStage);
        });

        manageDishesButton.setOnAction(e -> {
            EventDispatcher.invoke(Event.FoodManagementUI, childStage);
        });
        
        createOrderButton.setOnAction(e -> {
            EventDispatcher.invoke(Event.OrderUI, childStage);
        });
        
        viewFeedbackButton.setOnAction(e -> {
            EventDispatcher.invoke(Event.FeedbackUI, childStage);
        });

        changePasswordButton.setOnAction(e -> {
            EventDispatcher.invoke(Event.ChangePasswordUI, childStage);
        });

        viewAttendanceButton.setOnAction(e -> {
            EventDispatcher.invoke(Event.ViewAttendanceUI, childStage);
        });

        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
