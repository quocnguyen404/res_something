package gui;

import dto.request.UserRequest;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import listener.Event;
import system.EventDispatcher;

public class UpdateUserGUI extends Application {

    @Override
    public void start(Stage arg0) {
        arg0.setTitle("Update User");

        // Set up grid layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Add update form components
        Label userLabel = new Label("Username:");
        TextField userTextField = new TextField();
        Label firstNameLabel = new Label("First name");
        TextField firstNameTextField = new TextField();
        Label lastNameLabel = new Label("Last name");
        TextField lastNamTextField = new TextField();

        Label roleLabel = new Label("Role");
        ComboBox<String> roleComboBox = new ComboBox<>();
        UIUtilities.handleRole(roleComboBox);

        Button updateButton = new Button("Update");
        Button backButton = new Button("Back");

        gridPane.add(userLabel, 0, 0);
        gridPane.add(userTextField, 1, 0);
        gridPane.add(firstNameLabel, 0, 1);
        gridPane.add(firstNameTextField, 1, 1);
        
        gridPane.add(lastNameLabel, 0, 2);
        gridPane.add(lastNamTextField, 1, 2);
        
        gridPane.add(roleLabel, 0, 3);
        gridPane.add(roleComboBox, 1, 3);

        gridPane.add(updateButton, 0, 4);
        gridPane.add(backButton, 1, 4);

        updateButton.setOnAction(event -> {
            String userName = userTextField.getText();
            String firstName = firstNameTextField.getText();
            String lastName = lastNamTextField.getText();
            String role = roleComboBox.getSelectionModel().getSelectedItem();
            
            if(!UIUtilities.validateUserName(userName)) {
                UIUtilities.showAlert("Information", "Not valid user name", AlertType.INFORMATION);
                return;
            }

            UserRequest request = new UserRequest();
            request.setUserName(userName);
            request.setFirstName(firstName);
            request.setLastName(lastName);
            request.setRole(dao.Role.valueOf(role));


            EventDispatcher.invoke(Event.UpdateUser, request);
            EventDispatcher.invoke(Event.HandleUpdateUser);
        });

        backButton.setOnAction(event -> {
            arg0.close(); // Close register window
            try {
                this.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Scene scene = new Scene(gridPane, 400, 250);
        arg0.setScene(scene);
        arg0.show();
    }
    
}
