
package gui;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import dto.request.DishRequest;
import listener.Event;
import system.EventDispatcher;

public class FoodManagementGUI extends Application {

    private Stage childStage;
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Food Management");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Button addDishButton = new Button("Add Dish");
        Button deleteDishButton = new Button("Delete Dish");
        Button updateDishButton = new Button("Update Dish");
        Button viewDishes = new Button("View Dishes");
        Button backButton = new Button("Back");
        ListView<String> dishes = new ListView<>();

        gridPane.add(addDishButton, 0, 1);
        gridPane.add(deleteDishButton, 1, 1);
        gridPane.add(updateDishButton, 0, 2);
        gridPane.add(viewDishes, 1, 2);
        gridPane.add(backButton, 0, 3);
        gridPane.add(dishes, 0, 5);

        childStage = new Stage();
        addDishButton.setOnAction(e -> {
            EventDispatcher.invoke(Event.AddDishUI, childStage);
        });

        deleteDishButton.setOnAction(e -> {
            String dishName = JOptionPane.showInputDialog("Enter dish name to delete");
            if(!UIUtilities.validateInput(dishName)) {
                UIUtilities.showAlert("Information", "Invalid dish name", AlertType.INFORMATION);
                return;
            }
            DishRequest request = new DishRequest(dishName, 0);
            EventDispatcher.invoke(Event.DeleteDish, request);
            EventDispatcher.invoke(Event.HandleDeleteDish);
        });

        updateDishButton.setOnAction(e -> {
            EventDispatcher.invoke(Event.UpdateDishUI, childStage);
        });

        viewDishes.setOnAction(e -> {
            dishes.getItems().clear();
            EventDispatcher.invoke(Event.GetDishes);
            EventDispatcher.invoke(Event.HandleViewDishes, dishes);
        });

        backButton.setOnAction(event -> {
            primaryStage.close();
            try {
                this.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}