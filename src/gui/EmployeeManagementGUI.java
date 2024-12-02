
package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EmployeeManagementGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Employee Management");

        // Set up the employee-specific interface
        VBox layout = new VBox();
        layout.getChildren().add(new Label("Welcome, Employee!"));
        layout.getChildren().add(new Label("This is your limited-access management interface."));

        Scene scene = new Scene(layout, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}