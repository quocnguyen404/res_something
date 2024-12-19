package gui;

import java.time.LocalDate;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import listener.Event;
import system.EventDispatcher;


public class FeedBackGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Feedbacks");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label feedbackDateLabel = new Label("Date:");
        DatePicker datePicker = new DatePicker();
        Button viewFeedbackButton = new Button("View feedbacks");
        ListView<String> feedbacks = new ListView<>();

        gridPane.add(feedbackDateLabel, 0, 0);
        gridPane.add(datePicker, 1, 0);
        gridPane.add(viewFeedbackButton, 0, 1);
        gridPane.add(feedbacks, 1, 2);

        // EventDispatcher.invoke(Event.GetFeedbacks);
        viewFeedbackButton.setOnAction(e -> {
            LocalDate date = datePicker.getValue();
            if(date == null) {
                UIUtilities.showAlert("Information", "Please pick a date", AlertType.INFORMATION);
                return;
            }
            feedbacks.getItems().clear();
            EventDispatcher.invoke(Event.GetFeedbacks, date);
            EventDispatcher.invoke(Event.HandleGetFeedbacks, feedbacks);
        });

        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
