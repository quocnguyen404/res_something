package gui;


import javax.swing.JOptionPane;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import dto.request.UserRequest;
import listener.Event;
import system.EventDispatcher;

public class UserManagerGUI extends Application{

    private Stage childStage;
    @Override
    public void start(Stage arg0) {
        Stage userStage = new Stage();
        userStage.setTitle("User Management");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Button createUserButton = new Button("Create User");
        Button updateUserButton = new Button("Update User");
        Button deleteUserButton = new Button("Delete User");

        childStage = new Stage();
        createUserButton.setOnAction(e -> {
            childStage.close();
            EventDispatcher.invoke(Event.RegisterUI, childStage);
        });
        
        updateUserButton.setOnAction(e -> {
            childStage.close();
            EventDispatcher.invoke(Event.UpdateUserUI, childStage);
        });

        deleteUserButton.setOnAction(e -> {
            String username = JOptionPane.showInputDialog("Enter Username to delete");
            if(!UIUtilities.validateUserName(username)) {
                UIUtilities.showAlert("Information", "Not valid user name", AlertType.INFORMATION);
                return;
            }
            UserRequest user = new UserRequest();
            user.setUserName(username);
            EventDispatcher.invoke(Event.DeleteUser, user);
            EventDispatcher.invoke(Event.HandleDeletUser);
        });

        gridPane.add(createUserButton, 0, 0);
        gridPane.add(updateUserButton, 0, 1);
        gridPane.add(deleteUserButton, 0, 2);

        Scene userScene = new Scene(gridPane, 400, 300);
        userStage.setScene(userScene);
        userStage.show();
    }
}
