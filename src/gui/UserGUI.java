package gui;

import javafx.scene.control.ButtonType;
import repository.DishRepository;
import repository.UserRepository;
import services.ManagerService;
import dto.request.DishRequest;
import dto.response.DishResponse;
import dto.response.ResponseWrapper;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class UserGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("User Management");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Button manageDishesButton = new Button("Manage Dishes");
        Button changePasswordButton = new Button("Change Password");
        Button createOrderButton = new Button("Create Order");
        Button btnCheckAttendance = new Button("Check Attendance");
        Button logOutButton = new Button("Log Out");

        manageDishesButton.setOnAction(e -> showDishManagementScreen(primaryStage));
        changePasswordButton.setOnAction(e -> openChangePasswordScreen());
        logOutButton.setOnAction(e -> handleLogout(primaryStage));
        btnCheckAttendance.setOnAction(e -> openCheckAttendanceGUI());
        createOrderButton.setOnAction(e -> openOrderScreen());

        gridPane.add(new Label("Manage Users and Dishes"), 0, 0);
        gridPane.add(manageDishesButton, 0, 1);
        gridPane.add(changePasswordButton, 0, 2);
        gridPane.add(logOutButton, 0, 3);
        gridPane.add(createOrderButton, 1, 1);
        gridPane.add(btnCheckAttendance, 1, 2);

        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openCheckAttendanceGUI() {
        Stage attendanceStage = new Stage();
        CheckAttendanceGUI attendanceGUI = new CheckAttendanceGUI();
        attendanceGUI.start(attendanceStage);
    }

    private void openOrderScreen() {
        OrderGUI orderGUI = new OrderGUI();
        Stage orderStage = new Stage();
        try {
            orderGUI.start(orderStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleLogout(Stage primaryStage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                primaryStage.close();

                LoginGUI loginGUI = new LoginGUI();
                Stage loginStage = new Stage();
                try {
                    loginGUI.start(loginStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void openChangePasswordScreen() {
        ChangePasswordGUI changePasswordGUI = new ChangePasswordGUI();
        Stage stage = new Stage();
        try {
            changePasswordGUI.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDishManagementScreen(Stage primaryStage) {
        Stage dishStage = new Stage();
        dishStage.setTitle("Dish Management");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Button addDishButton = new Button("Add Dish");
        Button updateDishButton = new Button("Update Dish");
        Button deleteDishButton = new Button("Delete Dish");

        addDishButton.setOnAction(e -> handleAddDish());
        updateDishButton.setOnAction(e -> handleUpdateDish());
        deleteDishButton.setOnAction(e -> handleDeleteDish());

        gridPane.add(addDishButton, 0, 0);
        gridPane.add(updateDishButton, 0, 1);
        gridPane.add(deleteDishButton, 0, 2);

        Scene dishScene = new Scene(gridPane, 400, 300);
        dishStage.setScene(dishScene);
        dishStage.show();
    }


    private void handleAddDish() {
        String dishName = JOptionPane.showInputDialog("Enter Dish Name");
        String priceStr = JOptionPane.showInputDialog("Enter Price");
        double price = 0.0;

        try {
            price = Double.parseDouble(priceStr);  // Chuyển đổi giá trị chuỗi thành số
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid price format.");
            return;
        }

        DishRequest request = new DishRequest(dishName, price);
        //TODO
        ResponseWrapper response = new ResponseWrapper();


        DishResponse dishResponse = (DishResponse) response.getData();

        Map<String, Object> result = new HashMap<>();
        result.put("message", response.getMessage());
        result.put("dishName", dishResponse.getDishName());
        result.put("price", dishResponse.getPrice());

        JOptionPane.showMessageDialog(null, result.get("message"));
        saveFoodItem(dishName, price);  // Lưu món ăn vào file
    }

    private void handleUpdateDish() {
        String dishName = JOptionPane.showInputDialog("Enter Dish Name to update");
        String priceStr = JOptionPane.showInputDialog("Enter New Price");

        if (dishName == null || dishName.isEmpty() || priceStr == null || priceStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Dish name and price cannot be empty.");
            return;
        }

        double price = 0.0;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid price format.");
            return;
        }

        String directoryPath = "D:\\demo (4)\\res_something\\food_data.txt";
        File file = new File(directoryPath);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "Food data file not found.");
            return;
        }

        try {
            List<String> lines = Files.readAllLines(file.toPath());
            boolean dishFound = false;

            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                String[] tokens = line.split(" ,");
                if (tokens.length >= 2 && tokens[0].equalsIgnoreCase(dishName)) {
                    // Món ăn đã tồn tại, cập nhật giá trị của nó
                    updatedLines.add(dishName + " ," + price + " $");
                    dishFound = true;
                } else {
                    updatedLines.add(line);
                }
            }

            if (!dishFound) {
                updatedLines.add(dishName + " ," + price + " $");
            }

            Files.write(file.toPath(), updatedLines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

            JOptionPane.showMessageDialog(null, "Dish " + dishName + " updated successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to update food item.");
            e.printStackTrace();
        }
    }

    private void handleDeleteDish() {
        String dishName = JOptionPane.showInputDialog("Enter Dish Name to delete");

        if (dishName == null || dishName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Dish name cannot be empty.");
            return;
        }

        String directoryPath = "D:\\demo (4)\\res_something\\food_data.txt";
        File file = new File(directoryPath);

        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "Food data file not found.");
            return;
        }

        try {
            List<String> lines = Files.readAllLines(file.toPath());
            List<String> updatedLines = new ArrayList<>();
            boolean dishFound = false;

            for (String line : lines) {
                String[] tokens = line.split(" ,");
                if (tokens.length >= 2 && tokens[0].equalsIgnoreCase(dishName)) {
                    dishFound = true;
                } else {
                    updatedLines.add(line);
                }
            }

            if (!dishFound) {
                JOptionPane.showMessageDialog(null, "Dish " + dishName + " not found.");
                return;
            }

            Files.write(file.toPath(), updatedLines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

            JOptionPane.showMessageDialog(null, "Dish " + dishName + " removed successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to remove food item.");
            e.printStackTrace();
        }
    }


    private void saveFoodItem(String dishName, double price) {
        String directoryPath = "D:\\demo (4)\\res_something\\food_data.txt";

        try {
            File dir = new File("D:\\demo (4)\\res_something");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(directoryPath, true))) {
                writer.write(dishName + " ," + price + " $");
                writer.newLine();
            }
        } catch (IOException e) {
            // showAlert("Error", "Failed to save food item. Error: " + e.getMessage());
            e.printStackTrace();  // In ra chi tiết lỗi
        }
    }
}