package gui;

import dto.request.DishRequest;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import listener.Event;
import system.EventDispatcher;

public class AddDishGUI extends Application{

    @Override
    public void start(Stage arg0) {
        arg0.setTitle("Add Dish");

        // Set up grid layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label dishNameLabel = new Label("Dish name:");
        TextField dishNameTextField = new TextField();
        Label priceLabel = new Label("Price:");
        TextField priceTextField = new TextField();

        Button submitButton = new Button("Submit");
        Button backButton = new Button("Back");

        gridPane.add(dishNameLabel, 0, 0);
        gridPane.add(dishNameTextField, 1, 0);
        gridPane.add(priceLabel, 0, 1);
        gridPane.add(priceTextField, 1, 1);
        gridPane.add(submitButton, 0, 4);
        gridPane.add(backButton, 1, 4);

        //valid double value for price
        priceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^-?\\d*(\\.\\d*)?$")) {
                priceTextField.setText(oldValue);
            }
        });

        submitButton.setOnAction(e -> {
            String dishName = dishNameTextField.getText();
            String price = priceTextField.getText();

            if(!UIUtilities.validateInput(dishName) || !UIUtilities.validateInput(price)) {
                UIUtilities.showAlert("Information", "Invalid dish name or price", AlertType.INFORMATION);
                return;
            }
            DishRequest request = new DishRequest(dishName, Double.parseDouble(price));
            EventDispatcher.invoke(Event.AddDish, request);
            EventDispatcher.invoke(Event.HandleAddDish);
        });

        backButton.setOnAction(event -> {
            arg0.close();
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
