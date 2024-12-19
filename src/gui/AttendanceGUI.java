package gui;

import java.time.LocalDate;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import listener.Event;
import system.EventDispatcher;

public class AttendanceGUI extends Application {

    @Override
    public void start(Stage arg0) {
        arg0.setTitle("Attendances");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);


        DatePicker datePicker = new DatePicker();
        Button viewAttendaceButton = new Button("View");
        ListView<String> attendanceView = new ListView<>();

        gridPane.add(datePicker, 0, 0);
        gridPane.add(viewAttendaceButton, 1, 0);
        gridPane.add(attendanceView, 0, 1);

        viewAttendaceButton.setOnAction(e -> {
            LocalDate date = datePicker.getValue();
            // EventDispatcher
            if(date == null) {
                UIUtilities.showAlert("Information", "Please pick a date", AlertType.INFORMATION);
                return;
            }

            attendanceView.getItems().clear();
            EventDispatcher.invoke(Event.HandleViewAttendance, attendanceView, date);
        });

        Scene scene = new Scene(gridPane, 400, 300);
        arg0.setScene(scene);
        arg0.show();
    }
    

}
