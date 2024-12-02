
package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ManagerManagementGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Manager Management");

        // Set up the manager-specific interface
        VBox layout = new VBox();
        layout.getChildren().add(new Label("Welcome, Manager!"));
        layout.getChildren().add(new Label("This is your full-access management interface."));

        Scene scene = new Scene(layout, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}