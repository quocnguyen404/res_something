package gui;

import dao.Attendance;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CheckAttendanceGUI extends Application {

    private TextField txtID;
    private Button btnCheckIn, btnCheckOut;

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        Label lblID = new Label("Employee ID:");
        txtID = new TextField();
        txtID.setPromptText("Enter employee ID");

        btnCheckIn = new Button("Check In");
        btnCheckOut = new Button("Check Out");

        HBox buttonBox = new HBox(10, btnCheckIn, btnCheckOut);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        root.getChildren().addAll(lblID, txtID, buttonBox);

        btnCheckIn.setOnAction(e -> handleCheckIn());

        btnCheckOut.setOnAction(e -> handleCheckOut());

        Scene scene = new Scene(root, 300, 200);
        primaryStage.setTitle("Attendance System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Xử lý logic Check-In
     */
    private void handleCheckIn() {
        try {
            int id = Integer.parseInt(txtID.getText().trim());
            LocalTime checkinTime = LocalTime.now();

            Attendance attendance = new Attendance(id, checkinTime, null);
            saveAttendanceToFile(attendance);
            showAlert("Check-In Successful", "Check-In time: " + checkinTime);
        } catch (NumberFormatException ex) {
            showAlert("Invalid ID", "Please enter a valid numeric ID.");
        }
    }

    /**
     * Xử lý logic Check-Out
     */
    private void handleCheckOut() {
        try {
            int id = Integer.parseInt(txtID.getText().trim());
            LocalTime checkoutTime = LocalTime.now();

            Attendance attendance = new Attendance(id, null, checkoutTime);
            saveAttendanceToFile(attendance);
            showAlert("Check-Out Successful", "Check-Out time: " + checkoutTime);
        } catch (NumberFormatException ex) {
            showAlert("Invalid ID", "Please enter a valid numeric ID.");
        }
    }

    /**
     * Lưu Attendance vào file Attendance.txt
     */
    private void saveAttendanceToFile(Attendance attendance) {
        String directoryPath = "Attendance.txt";

        try {
            File dir = new File(directoryPath);
            if (!dir.exists()) {
                dir.createNewFile();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(directoryPath, true))) {
                String checkinTime = formatTime(attendance.getCheckinTime());
                String checkoutTime = formatTime(attendance.getCheckoutTime());

                writer.write(String.format("ID: %d | Check-in: %s | Check-out: %s", attendance.getID(), checkinTime, checkoutTime));
                writer.newLine();
                writer.write("-----------------------------------");
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save attendance.");
        }
    }

    private String formatTime(LocalTime time) {
        if (time == null) {
            return "N/A";
        }
        return time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}