package gui;

import dao.Feedback;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FeedBackGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("View Feedback");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Button loadFeedbackButton = new Button("Load Feedback");
        Button closeButton = new Button("Back");

        loadFeedbackButton.setOnAction(e -> loadFeedback());
        closeButton.setOnAction(e -> primaryStage.close());

        gridPane.add(new Label("Feedback Viewer"), 0, 0);
        gridPane.add(loadFeedbackButton, 0, 1);
        gridPane.add(closeButton, 0, 2);

        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadFeedback() {
        StringBuilder feedbackText = new StringBuilder();

        try {
            File feedbackFile = new File("D:\\demo (4)\\res_something\\feedback.txt");
            if (!feedbackFile.exists()) {
                showAlert("Error", "Feedback file not found.");
                return;
            }

            List<String> lines = Files.readAllLines(feedbackFile.toPath());
            for (String line : lines) {
                String[] tokens = line.split(",");
                if (tokens.length == 4) {
                    Feedback feedback = new Feedback(
                            Integer.parseInt(tokens[0]),
                            Integer.parseInt(tokens[1]),
                            tokens[2],
                            tokens[3]
                    );

                    // Thêm thông tin phản hồi vào chuỗi feedbackText
                    feedbackText.append("ID: ").append(feedback.getID()).append("\n")
                            .append("Order ID: ").append(feedback.getOrderID()).append("\n")
                            .append("Customer: ").append(feedback.getCustomerName()).append("\n")
                            .append("Feedback: ").append(feedback.getFeedback()).append("\n\n");
                }
            }

            if (feedbackText.length() == 0) {
                showAlert("No Feedback", "There are no feedback available.");
            } else {
                showAlert("Feedback", feedbackText.toString());
            }
        } catch (IOException e) {
            showAlert("Error", "Failed to read feedback data from file.");
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
