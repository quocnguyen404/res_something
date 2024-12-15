package gui;

import dto.request.OrderRequest;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class OrderGUI {

    // private ManagerService managerService;

    public OrderGUI() {
        // this.managerService = new ManagerService(new UserRepository(), new DishRepository());
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Create Order");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        TextField orderIDField = new TextField();
        TextField dishNamesField = new TextField();
        TextField pricesField = new TextField();

        Button createOrderButton = new Button("Create Order");

        createOrderButton.setOnAction(e -> createOrder(orderIDField, dishNamesField, pricesField));

        gridPane.add(new Label("Order ID:"), 0, 0);
        gridPane.add(orderIDField, 1, 0);

        gridPane.add(new Label("Dish Names (comma separated):"), 0, 1);
        gridPane.add(dishNamesField, 1, 1);

        gridPane.add(new Label("Prices (comma separated):"), 0, 2);
        gridPane.add(pricesField, 1, 2);

        gridPane.add(createOrderButton, 0, 3, 2, 1);

        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createOrder(TextField orderIDField, TextField dishNamesField, TextField pricesField) {
        String orderID = orderIDField.getText();
        String dishNames = dishNamesField.getText();
        String prices = pricesField.getText();

        if (orderID.isEmpty() || dishNames.isEmpty() || prices.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields must be filled.");
            return;
        }

        String[] dishesArray = dishNames.split(",");
        String[] pricesArray = prices.split(",");
        Map<String, Integer> dishes = new HashMap<>();
        double totalPrice = 0.0;

        for (int i = 0; i < dishesArray.length; i++) {
            try {
                String dish = dishesArray[i].trim();
                int price = Integer.parseInt(pricesArray[i].trim());
                dishes.put(dish, price);
                totalPrice += price;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid price format.");
                return;
            }
        }

        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        OrderRequest orderRequest = new OrderRequest(Integer.parseInt(orderID), dishes, totalPrice, currentTime);
        saveOrderToFile(orderRequest);
        JOptionPane.showMessageDialog(null, "Order created successfully!");
    }

    private void saveOrderToFile(OrderRequest orderRequest) {
        String directoryPath = "order.txt";

        try {
            File dir = new File(directoryPath);
            if (!dir.exists()) {
                dir.createNewFile();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(directoryPath, true))) {
                writer.write("Order ID: " + orderRequest.getID());
                writer.newLine();

                String dishesString = String.join(", ", orderRequest.getDishes().keySet());
                writer.write("Dishes: " + dishesString);
                writer.newLine();

                writer.write("Price: " + orderRequest.getPrice());
                writer.newLine();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                String formattedTime = orderRequest.getTime().format(formatter);
                writer.write("Time: " + formattedTime);
                writer.newLine();

                writer.write("-----------------------------------");
                writer.newLine();
            }
        } catch (IOException e) {
            showAlert("Error", "Failed to save order.");
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

}
