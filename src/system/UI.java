package system;

import dto.response.ResponseWrapper;
import dto.response.UserResponse;
import gui.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import listener.*;

public class UI {
    private Stage arg0;
    private SystemUser user;

    private LoginGUI loginGUI;
    private ManagerManagementGUI managementGUI;


    public UI() {
        loginGUI = new LoginGUI();
        bindEvent();
    }

    public void start(Stage arg0) {
        this.arg0 = arg0;
        loginGUI.start(arg0);
    }

    private void bindEvent() {
        ActionListener handleLogin = new ActionListener(this::handleLogin);
        EventDispatcher.addEvent(Event.HandleLogin, handleLogin);
    }
    
    private void handleLogin() {
        Listener listener = EventDispatcher.getListener(Event.Authenticate);
        ResponseWrapper loginResponse = listener.getResponse();
        try {
            if(loginResponse.isOK()) {
                handleLoginSuccess(loginResponse);
            } else {
                UIUtilities.showAlert("Alert", loginResponse.getMessage(), AlertType.INFORMATION);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        listener.clear();
    }

    private void handleLoginSuccess(ResponseWrapper response) {
        UserResponse userResponse = (UserResponse)response.getData();
        user = new SystemUser(userResponse);

        try {
            loginGUI.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch (user.getRole()) {
            case MANAGER: {
                managementGUI = new ManagerManagementGUI();
                EventDispatcher.invoke(Event.BindManagerEvent, userResponse);
                managementGUI.start(arg0);
            } break;
            
            case STAFF: {
                EventDispatcher.invoke(Event.BindEmployeeEvent, userResponse);
            } break;
        }
    }
}
