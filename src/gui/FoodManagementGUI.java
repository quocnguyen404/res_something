
package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FoodManagementGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Food Management");

        // Set up grid layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Create UI components
        Label foodLabel = new Label("Food Item:");
        TextField foodTextField = new TextField();
        Label priceLabel = new Label("Price:");
        TextField priceTextField = new TextField();
        Button addFoodButton = new Button("Add Food Item");
        ListView<String> foodListView = new ListView<>();

        addFoodButton.setOnAction(e -> {
            String foodName = foodTextField.getText();
            String price = priceTextField.getText();
            if (!foodName.isEmpty() && !price.isEmpty()) {
                String foodEntry = foodName + " - $" + price;
                foodListView.getItems().add(foodEntry);
                foodTextField.clear();
                priceTextField.clear();
                saveFoodItem(foodEntry); // Save food item to file
            }
        });

        // Add components to grid
        gridPane.add(foodLabel, 0, 0);
        gridPane.add(foodTextField, 1, 0);
        gridPane.add(priceLabel, 0, 1);
        gridPane.add(priceTextField, 1, 1);
        gridPane.add(addFoodButton, 1, 2);
        gridPane.add(foodListView, 0, 3, 2, 1);

        // Show scene
        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void saveFoodItem(String foodItem) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("food_data.txt", true))) {
            writer.write(foodItem);
            writer.newLine();
        } catch (IOException e) {
            showAlert("Error", "Failed to save food item.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}