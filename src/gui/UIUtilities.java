package gui;

import dao.Role;
import dto.response.AttendanceResponse;
import dto.response.ResponseWrapper;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import listener.Event;
import listener.Listener;
import system.EventDispatcher;
import system.SystemUser;

public class UIUtilities {
    public static String attendanceResponseToStr(AttendanceResponse response) {
        return String.format("%s, %s, %s", response.getID(), response.getCheckinTime(), response.getCheckoutTime());
    }

    public static Boolean validateInput(String input) {
        return input != null && !input.isEmpty() && !input.isBlank();
    }

    public static Boolean validateUserName(String userName) {
        return validateInput(userName);
    }

    public static Boolean validatePassword(String password) {
        return password != null && password.length() >= 4;
    }
    
    //Maybe add some alert pool
    public static void showAlert(String title, String response, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(response);
        alert.showAndWait();
    }

    public static void handleRole(ComboBox<String> comboBox) {
        EventDispatcher.invoke(Event.GetSystemUser);
        Listener listener = EventDispatcher.getListener(Event.GetSystemUser);
        try {
            SystemUser user = (SystemUser)listener.getData();
            dao.Role role = Role.STAFF;
            if(user != null) {
                role = user.getRole();
            }
            switch (role) {
                case MANAGER: {
                    for (dao.Role r : dao.Role.values()) {
                        comboBox.getItems().add(r.toString());
                    }
                    comboBox.getSelectionModel().select(dao.Role.STAFF.toString());
                    break;
                }
                case STAFF: 
                default: {
                    //default role
                    comboBox.getItems().add(dao.Role.STAFF.toString());
                    comboBox.getSelectionModel().select(dao.Role.STAFF.toString());
                    break;
                }
            }            
        } catch (Exception e) {
            listener.clearData();
            e.printStackTrace();
        }
        listener.clearData();
    }
}
