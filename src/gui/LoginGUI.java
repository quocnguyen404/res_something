package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public class LoginGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login Application");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Add login form components
        Label userLabel = new Label("Username:");
        TextField userTextField = new TextField();
        Label passLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");

        gridPane.add(userLabel, 0, 0);
        gridPane.add(userTextField, 1, 0);
        gridPane.add(passLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginButton, 1, 2);
        gridPane.add(registerButton, 0, 2);

        loginButton.setOnAction(event -> {
            String username = userTextField.getText();
            String password = passwordField.getText();

            String role = validateUser(username, password);

            if (role != null) {
                if (role.equals("MANAGER")) {
                    ManagerManagementGUI managerManagementGUI = new ManagerManagementGUI(username);
                    managerManagementGUI.start(new Stage());
                    primaryStage.close();
                } else {
                    UserManagerGUI userManagerGUI = new UserManagerGUI(username);
                    userManagerGUI.start(new Stage());
                    primaryStage.close();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText("Invalid username or password");
                alert.showAndWait();
            }
        });

        registerButton.setOnAction(event -> {
            RegisterGUI registerGUI = new RegisterGUI();
            registerGUI.start(new Stage());
            primaryStage.close();
        });

        Scene scene = new Scene(gridPane, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String validateUser(String username, String password) {
        String filePath = "D:\\demo (4)\\res_something\\user_data.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                String[] credentials = line.split(":");

                if (credentials.length == 3) {
                    String fileUsername = credentials[0].trim();
                    String filePassword = credentials[1].trim();
                    String fileRole = credentials[2].trim();

                    if (fileUsername.equals(username) && filePassword.equals(password)) {
                        return fileRole;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void saveUser(String username, String password, String role) {
        String directoryPath = "D:\\demo (4)\\res_something\\user_data.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(directoryPath, true))) {
            writer.write(username + ":" + password + ":" + role);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
