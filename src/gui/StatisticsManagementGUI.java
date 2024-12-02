
package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StatisticsManagementGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Statistics Management");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        Label label = new Label("Statistics functionalities will be implemented here.");

        vbox.getChildren().add(label);

        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}