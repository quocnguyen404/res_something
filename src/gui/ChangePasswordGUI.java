package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ChangePasswordGUI extends Application {

    private String loggedInUsername;
    private final String userDataFile = "user_data.txt";

    public ChangePasswordGUI(String username) {
        this.loggedInUsername = username;
    }

    @Override
    public void start(Stage stage) {
        if (loggedInUsername == null || loggedInUsername.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "No username provided! Please log in first.");
            stage.close();
            return;
        }

        stage.setTitle("Change Password");

        // Grid layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Components
        Label currentPassLabel = new Label("Current Password:");
        PasswordField currentPasswordField = new PasswordField();
        Label newPassLabel = new Label("New Password:");
        PasswordField newPasswordField = new PasswordField();
        Label confirmPassLabel = new Label("Confirm New Password:");
        PasswordField confirmPasswordField = new PasswordField();

        Button submitButton = new Button("Submit");

        gridPane.add(currentPassLabel, 0, 0);
        gridPane.add(currentPasswordField, 1, 0);
        gridPane.add(newPassLabel, 0, 1);
        gridPane.add(newPasswordField, 1, 1);
        gridPane.add(confirmPassLabel, 0, 2);
        gridPane.add(confirmPasswordField, 1, 2);
        gridPane.add(submitButton, 1, 3);


        submitButton.setOnAction(event -> {
            String currentPassword = currentPasswordField.getText();
            String newPassword = newPasswordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            Map<String, String[]> userData = loadUserData();

            if (!userData.containsKey(loggedInUsername) || !userData.get(loggedInUsername)[0].equals(currentPassword)) {
                showAlert(Alert.AlertType.ERROR, "Error", "Current password is incorrect.");
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                showAlert(Alert.AlertType.ERROR, "Error", "New password and confirmation do not match.");
                return;
            }

            String role = userData.get(loggedInUsername)[1];
            userData.put(loggedInUsername, new String[]{newPassword, role});
            saveUserData(userData);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Password updated successfully.");
        });

        Scene scene = new Scene(gridPane, 400, 200);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Load user data from file.
     * Each entry in the file should be in the format: username:password:role
     */
    private Map<String, String[]> loadUserData() {
        Map<String, String[]> userData = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(userDataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 3); // Tách thành 3 phần: username, password, role
                if (parts.length == 3) {
                    userData.put(parts[0], new String[]{parts[1], parts[2]}); // {password, role}
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userData;
    }

    /**
     * Save user data back to file.
     * Writes each entry in the format: username:password:role
     */
    private void saveUserData(Map<String, String[]> userData) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userDataFile))) {
            for (Map.Entry<String, String[]> entry : userData.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue()[0] + ":" + entry.getValue()[1]);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Show alert dialog.
     */
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // public static void main(String[] args) {
    //     launch(args);
    // }
}
