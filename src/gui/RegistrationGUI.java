
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

public class RegistrationGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Registration");

        // Set up grid layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Create UI components
        Label userLabel = new Label("Username:");
        TextField userTextField = new TextField();
        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();
        Button registerButton = new Button("Register");

        registerButton.setOnAction(e -> {
            String username = userTextField.getText();
            String password = passField.getText();
            if (isValidPassword(password)) {
                saveUserToFile(username, password);
                showAlert("Success", "Registration successful!");
                primaryStage.close(); // Close registration window
            } else {
                showAlert("Error", "Password cannot contain spaces. Please try again.");
            }
        });

        // Add components to grid
        gridPane.add(userLabel, 0, 0);
        gridPane.add(userTextField, 1, 0);
        gridPane.add(passLabel, 0, 1);
        gridPane.add(passField, 1, 1);
        gridPane.add(registerButton, 1, 2);

        // Show scene
        Scene scene = new Scene(gridPane, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean isValidPassword(String password) {
        return !password.contains(" ");
    }

    private void saveUserToFile(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("user_data.txt", true))) {
            writer.write(username + "," + password);
            writer.newLine();
        } catch (IOException e) {
            showAlert("Error", "Failed to save user data.");
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