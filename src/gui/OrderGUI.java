package gui;

import java.util.Map.Entry;

import dto.request.CreateOrderRequest;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import listener.Event;
import system.EventDispatcher;

public class OrderGUI {

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Create Order");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label dishLabel = new Label("Dishes:");
        ComboBox<String> dishes = new ComboBox<>();
        Label amountLabel = new Label("Amount:");
        TextField amounTextField = new TextField();
        ListView<String> dishesView = new ListView<>();

        Button addDishButton = new Button("Add dish");
        Button removeDishButton = new Button("Remove dish");
        Button createOrderButton = new Button("Create Order");

        amounTextField.setTextFormatter(new javafx.scene.control.TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?\\d*")) { 
                return change;
            }
            return null; // Reject change
        }));

        EventDispatcher.invoke(Event.GetDishes);
        EventDispatcher.invoke(Event.HandleChooseDishes, dishes);

        gridPane.add(dishLabel, 0, 0);
        gridPane.add(dishes, 1, 0);
        gridPane.add(amountLabel, 0, 1);
        gridPane.add(amounTextField, 1, 1);
        
        gridPane.add(addDishButton, 2, 0);
        gridPane.add(removeDishButton, 2, 1);
        gridPane.add(createOrderButton, 2, 3);

        gridPane.add(dishesView, 0, 4);

        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        addDishButton.setOnAction(e -> {
            String dishName = dishes.getSelectionModel().getSelectedItem().toString();
            String amountStr = amounTextField.getText();
            if(!UIUtilities.validateInput(dishName)) {
                UIUtilities.showAlert("Information", "Pick dish", AlertType.INFORMATION);
                return;
            }
            if(!UIUtilities.validateInput(amountStr) || Integer.parseInt(amountStr) == 0) {
                UIUtilities.showAlert("Information", "Enter amount", AlertType.INFORMATION);
                return;
            }
            createOrderRequest.addDish(dishName, Integer.parseInt(amountStr));
            updateListView(createOrderRequest, dishesView);
        });

        removeDishButton.setOnAction(e -> {
            String dishName = dishes.getSelectionModel().getSelectedItem().toString();
            String amountStr = amounTextField.getText();
            if(!UIUtilities.validateInput(dishName)) {
                UIUtilities.showAlert("Information", "Pick dish", AlertType.INFORMATION);
                return;
            }
            if(!UIUtilities.validateInput(amountStr) || Integer.parseInt(amountStr) == 0) {
                UIUtilities.showAlert("Information", "Enter amount", AlertType.INFORMATION);
                return;
            }

            createOrderRequest.removeDish(dishName, Integer.parseInt(amountStr));
            updateListView(createOrderRequest, dishesView);
        });

        createOrderButton.setOnAction(e -> {
            EventDispatcher.invoke(Event.CreateOrder, createOrderRequest);
            EventDispatcher.invoke(Event.HandleCreateOrder, dishesView);
        });

        Scene scene = new Scene(gridPane, 550, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateListView(CreateOrderRequest order, ListView<String> listView) {
        listView.getItems().clear();
        for (Entry<String, Integer> entry : order.getDishes().entrySet()) {
            listView.getItems().add(String.format("%s: %s", entry.getKey(), entry.getValue()));
        }
    }
}
