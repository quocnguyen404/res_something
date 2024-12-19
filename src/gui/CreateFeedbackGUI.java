package gui;

import java.time.LocalDate;

import dto.request.FeedbackRequest;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import listener.Event;
import system.EventDispatcher;

public class CreateFeedbackGUI extends Application{

    @Override
    public void start(Stage arg0) {
        arg0.setTitle("Create Feedback");

        // Set up grid layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label orderIDLabel = new Label("Order ID:");
        TextField orderIDTextField = new TextField();
        Label customerNameLabel = new Label("Customer name:");
        TextField customerNameTextField = new TextField();
        TextArea feedbackArea = new TextArea();
        Button submiButton = new Button("Submit");

        feedbackArea.setPromptText("Enter your feedback here...");
        feedbackArea.setWrapText(true); // Enable text wrapping
        feedbackArea.setPrefSize(200, 200); // Set preferred size

        orderIDTextField.setTextFormatter(new javafx.scene.control.TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?\\d*")) { 
                return change;
            }
            return null; // Reject change
        }));

        gridPane.add(orderIDLabel, 0, 0);
        gridPane.add(orderIDTextField, 1, 0);
        gridPane.add(customerNameLabel, 0, 1);
        gridPane.add(customerNameTextField, 0, 2);
        gridPane.add(feedbackArea, 0, 3);
        gridPane.add(submiButton, 1, 3);


        submiButton.setOnAction(e -> {
            String orderID = orderIDTextField.getText();
            String customerName = customerNameTextField.getText();
            String feedback = feedbackArea.getText();
            if(!UIUtilities.validateInput(orderID) || !UIUtilities.validateInput(customerName)) {
                UIUtilities.showAlert("Information", "Invalid order ID or customer name", AlertType.INFORMATION);
                return;
            }

            if(!UIUtilities.validateInput(feedback)) {
                UIUtilities.showAlert("Information", "Please enter feedback", AlertType.INFORMATION);
                return;
            }

            FeedbackRequest request = new FeedbackRequest(Integer.parseInt(orderID), customerName, feedback, LocalDate.now());
            EventDispatcher.invoke(Event.CreateFeedback, request);
            EventDispatcher.invoke(Event.HandleCreateFeedback);
        });
        
        Scene scene = new Scene(gridPane, 400, 300);
        arg0.setScene(scene);
        arg0.show();
    }
    
}
