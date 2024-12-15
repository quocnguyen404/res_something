package gui;

import dto.response.UserResponse;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import system.EventDispatcher;
import dto.request.UserRequest;
import dto.request.DishRequest;
import dto.response.AttendanceResponse;
import dto.response.DishResponse;
import dto.response.ResponseWrapper;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import listener.Event;

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

public class ManagerManagementGUI extends Application {
    // private String loggedInUsername;
    // private ManagerService managerService;

    // public ManagerManagementGUI(String username) {
    //     this.loggedInUsername = username;
    //     this.managerService = new ManagerService(new UserRepository(), new DishRepository()); // Initialize here
    // }

    // public ManagerManagementGUI() {
    //     this.managerService = new ManagerService(new UserRepository(), new DishRepository()); // Initialize here as well
    // }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Manager Management");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Button manageUsersButton = new Button("Manage Users");
        Button manageDishesButton = new Button("Manage Dishes");
        Button viewFeedbackButton = new Button("View Feedback");
        Button createOrderButton = new Button("Create Order");
        Button viewAttendanceButton = new Button("View Attendance");
        Button changePasswordButton = new Button("Change Password");
        Button logOutButton = new Button("Log Out");

        manageUsersButton.setOnAction(e -> showUserManagementScreen(primaryStage));
        manageDishesButton.setOnAction(e -> showDishManagementScreen(primaryStage));
        viewFeedbackButton.setOnAction(e -> showFeedbackScreen(primaryStage));
        changePasswordButton.setOnAction(e -> openChangePasswordScreen());
        logOutButton.setOnAction(e -> handleLogout(primaryStage));
        createOrderButton.setOnAction(e -> openOrderScreen());
        viewAttendanceButton.setOnAction(e -> viewAttendance());

        gridPane.add(new Label("Manage Users and Dishes"), 0, 0);
        gridPane.add(manageUsersButton, 0, 1);
        gridPane.add(manageDishesButton, 1, 1);
        gridPane.add(viewFeedbackButton, 0, 2);
        gridPane.add(changePasswordButton, 0, 3);
        gridPane.add(logOutButton, 0, 4);
        gridPane.add(createOrderButton, 1, 2);
        gridPane.add(viewAttendanceButton, 1, 3);

        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void viewAttendance() {
        //TODO add GetAttendances event
        EventDispatcher.invoke(Event.GetAttendances);
        String attendanceData = readAttendanceFromFile();

        Stage attendanceStage = new Stage();
        attendanceStage.setTitle("Attendance List");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        TextArea attendanceArea = new TextArea();
        attendanceArea.setEditable(false);
        attendanceArea.setText(attendanceData);

        gridPane.add(new Label("Attendance List"), 0, 0);
        gridPane.add(attendanceArea, 0, 1);

        Scene scene = new Scene(gridPane, 400, 300);
        attendanceStage.setScene(scene);
        attendanceStage.show();
    }

    private String readAttendanceFromFile() {
        StringBuilder sb = new StringBuilder();

        listener.Listener listener = EventDispatcher.getListener(Event.GetAttendances);
        ResponseWrapper response = listener.getData();
        if(!response.isOK()) {
            UIUtilities.showAlert("Alert", response.getMessage(), AlertType.ERROR);
            return null;
        }
        try {
            @SuppressWarnings("unchecked")
            List<AttendanceResponse> list = (List<AttendanceResponse>)response.getData();
            for (AttendanceResponse attendanceResponse : list) {
                // sb.append(attendanceResponse.getID())
                sb.append(utilities.AppUtilities.attendanceResponseToStr(attendanceResponse));
                sb.append('\n');
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        listener.clearData();
        return sb.toString();
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

    private void showFeedbackScreen(Stage primaryStage) {
        FeedBackGUI feedbackGUI = new FeedBackGUI();  // Khởi tạo FeedBackGUI
        Stage feedbackStage = new Stage();
        try {
            feedbackGUI.start(feedbackStage);
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
            if (response == ButtonType.OK) { // Kiểm tra nút xác nhận
                primaryStage.close();

                // Quay lại màn hình đăng nhập
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
        //TODO
        // ChangePasswordGUI changePasswordGUI = new ChangePasswordGUI(loggedInUsername);
        // Stage stage = new Stage();
        // try {
        //     changePasswordGUI.start(stage);
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
    }

    private void showUserManagementScreen(Stage primaryStage) {
        Stage userStage = new Stage();
        userStage.setTitle("User Management");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Button createUserButton = new Button("Create User");
        Button updateUserButton = new Button("Update User");
        Button deleteUserButton = new Button("Delete User");

        createUserButton.setOnAction(e -> handleCreateUser());
        updateUserButton.setOnAction(e -> handleUpdateUser());
        deleteUserButton.setOnAction(e -> handleDeleteUser());

        gridPane.add(createUserButton, 0, 0);
        gridPane.add(updateUserButton, 0, 1);
        gridPane.add(deleteUserButton, 0, 2);

        Scene userScene = new Scene(gridPane, 400, 300);
        userStage.setScene(userScene);
        userStage.show();
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

    private void handleCreateUser() {
        String username = JOptionPane.showInputDialog("Enter Username");
        if (username == null || username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username cannot be empty.");
            return;
        }

        String password = JOptionPane.showInputDialog("Enter Password");
        if (password == null || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Password cannot be empty.");
            return;
        }

        UserRequest request = new UserRequest();
        request.setUserName(username);
        request.setPassword(password);

        // ResponseWrapper response = managerService.createUser(request);
        //TODO
        ResponseWrapper response = new ResponseWrapper();

        UserResponse userResponse = (UserResponse) response.getData();

        Map<String, Object> result = new HashMap<>();
        result.put("message", response.getMessage());
        result.put("username", userResponse.getUserName());
        result.put("status", "Created");

        JOptionPane.showMessageDialog(null, result.get("message"));
        saveUserEdit(username, password);
    }

    private void handleUpdateUser() {
        String username = JOptionPane.showInputDialog("Enter Username to update");
        String newPassword = JOptionPane.showInputDialog("Enter New Password");

        if (username == null || username.trim().isEmpty() || newPassword == null || newPassword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username and password cannot be empty.");
            return;
        }

        String filePath = "D:\\demo (4)\\res_something\\user_data_edit.txt";
        File file = new File(filePath);

        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "User data file not found.");
            return;
        }

        try {
            List<String> lines = Files.readAllLines(file.toPath());
            List<String> updatedLines = new ArrayList<>();
            boolean userFound = false;

            for (String line : lines) {
                String[] tokens = line.split(" ,");  // Đảm bảo dấu phân cách chính xác
                if (tokens.length >= 2 && tokens[0].trim().equalsIgnoreCase(username)) {  // Dùng equalsIgnoreCase để so sánh không phân biệt hoa thường
                    updatedLines.add(username + " ," + newPassword);
                    userFound = true;
                } else {
                    updatedLines.add(line);
                }
            }

            if (!userFound) {
                updatedLines.add(username + " ," + newPassword);
            }

            Files.write(file.toPath(), updatedLines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

            JOptionPane.showMessageDialog(null, "User " + username + " updated successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to update user.");
            e.printStackTrace();
        }
    }


    private void handleDeleteUser() {
        String username = JOptionPane.showInputDialog("Enter Username to delete");

        if (username == null || username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username cannot be empty.");
            return;
        }

        String filePath = "D:\\demo (4)\\res_something\\user_data_edit.txt";
        File file = new File(filePath);

        // Kiểm tra file tồn tại
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "User data file not found.");
            return;
        }

        try {
            List<String> lines = Files.readAllLines(file.toPath());
            List<String> updatedLines = new ArrayList<>();
            boolean userFound = false;
            for (String line : lines) {
                String[] tokens = line.split(" ,");
                if (tokens.length >= 2 && tokens[0].equalsIgnoreCase(username)) {
                    userFound = true;
                } else {
                    updatedLines.add(line);
                }
            }

            if (!userFound) {
                JOptionPane.showMessageDialog(null, "User " + username + " not found.");
                return;
            }
            Files.write(file.toPath(), updatedLines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

            JOptionPane.showMessageDialog(null, "User " + username + " removed successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to remove user.");
            e.printStackTrace();
        }
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
        // ResponseWrapper response = managerService.addDish(request);
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
    private void saveUserEdit(String username, String password) {
        String directoryPath = "D:\\demo (4)\\res_something\\user_data_edit.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(directoryPath, true))) {
            writer.write(username + "," + password);
            writer.newLine();
        } catch (IOException e) {
            showAlert("Error", "Failed to save user data.");
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
            showAlert("Error", "Failed to save food item. Error: " + e.getMessage());
            e.printStackTrace();  // In ra chi tiết lỗi
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // public static void main(String[] args) {
    //     launch(args);
    // }
}
