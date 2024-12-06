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

public class RegisterGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Register Application");

        // Set up grid layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Add register form components
        Label userLabel = new Label("Username:");
        TextField userTextField = new TextField();
        Label passLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Label confirmPassLabel = new Label("Confirm Password:");
        PasswordField confirmPasswordField = new PasswordField();
        Label roleLabel = new Label("Role:");

        // Change roleComboBox to only have "EMPLOYEE"
        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().add("EMPLOYEE"); // Only allow "EMPLOYEE"
        roleComboBox.setValue("EMPLOYEE"); // Default role is "EMPLOYEE"

        Button submitButton = new Button("Submit");
        Button backButton = new Button("Back to Login");

        gridPane.add(userLabel, 0, 0);
        gridPane.add(userTextField, 1, 0);
        gridPane.add(passLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(confirmPassLabel, 0, 2);
        gridPane.add(confirmPasswordField, 1, 2);
        gridPane.add(roleLabel, 0, 3);
        gridPane.add(roleComboBox, 1, 3);
        gridPane.add(submitButton, 0, 4);
        gridPane.add(backButton, 1, 4);

        submitButton.setOnAction(event -> {
            String username = userTextField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            String role = roleComboBox.getValue(); // Get selected role (should always be "EMPLOYEE")

            if (password.equals(confirmPassword)) {
                // Save user data including role
                saveUser(username, password, role);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registration Success");
                alert.setHeaderText("User successfully registered!");
                alert.setContentText("You can now log in with your new account.");
                alert.showAndWait();

                // After successful registration, open login screen
                LoginGUI loginGUI = new LoginGUI();
                loginGUI.start(new Stage());
                primaryStage.close(); // Close register window
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Registration Error");
                alert.setHeaderText("Passwords do not match");
                alert.showAndWait();
            }
        });

        backButton.setOnAction(event -> {
            LoginGUI loginGUI = new LoginGUI();
            loginGUI.start(new Stage());
            primaryStage.close(); // Close register window
        });

        Scene scene = new Scene(gridPane, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Save user to file with role
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
