package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import listener.Event;
import dto.request.UpdateDishRequest;
import system.EventDispatcher;

public class UpdateDishGUI extends Application{

    @Override
    public void start(Stage arg0) {
        arg0.setTitle("Update Dish");

        // Set up grid layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Add update form components
        Label dishNameLabel = new Label("Dish name:");
        TextField dishNameTextField = new TextField();
        Label newDishNameLabel = new Label("New dish name:");
        TextField newDishNameTextField = new TextField();
        Label priceLabel = new Label("Price:");
        TextField priceTextField = new TextField();

        Button submitButton = new Button("Submit");
        Button backButton = new Button("Back");

        gridPane.add(dishNameLabel, 0, 0);
        gridPane.add(dishNameTextField, 1, 0);
        gridPane.add(newDishNameLabel, 0, 1);
        gridPane.add(newDishNameTextField, 1, 1);
        
        gridPane.add(priceLabel, 0, 2);
        gridPane.add(priceTextField, 1, 2);
        
        gridPane.add(submitButton, 0, 4);
        gridPane.add(backButton, 1, 4);

        //valid price text field is a double
        priceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^-?\\d*(\\.\\d*)?$")) {
                priceTextField.setText(oldValue);
            }
        });

        submitButton.setOnAction(event -> {
            String dishName = dishNameTextField.getText();
            String newDishName = newDishNameTextField.getText();
            String price = priceTextField.getText();
            
            if(!UIUtilities.validateInput(dishName)) {
                UIUtilities.showAlert("Information", "Invalid dish name or new dish name", AlertType.INFORMATION);
                return;
            }
            if(!UIUtilities.validateInput(newDishName)) {
                newDishName = dishName;
            }
            
            UpdateDishRequest request = new UpdateDishRequest(dishName, newDishName, Double.parseDouble(price));

            EventDispatcher.invoke(Event.UpdateDish, request);
            EventDispatcher.invoke(Event.HandleUpdateDish);
        });

        backButton.setOnAction(event -> {
            arg0.close(); // Close register window
            try {
                this.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Scene scene = new Scene(gridPane, 400, 250);
        arg0.setScene(scene);
        arg0.show();
    }
    
}
