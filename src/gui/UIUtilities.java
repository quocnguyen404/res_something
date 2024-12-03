package gui;

import javafx.scene.control.Alert;

public class UIUtilities {
    //Maybe add some alert pool
    public static void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
