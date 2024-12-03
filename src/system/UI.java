package system;

import common.Result;
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

    public UI() {
        loginGUI = new LoginGUI();
    }

    public void start(Stage arg0) {
        this.arg0 = arg0;
        loginGUI.start(arg0);
    }

    private void login(String userName, String password) {

    }

    private void managerLogin(ResponseWrapper response) {
        try {
            user = (SystemUser)response.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void employeeLogin(ResponseWrapper response) {

    }

    private void handleLogin() {
        Listener listener = EventDispatcher.getListener(Event.Login);
        ResponseWrapper loginResponse = listener.getResponse();
        try {
            if(loginResponse.isOK()) {
                handleLoginSuccess(loginResponse);
            } else {
                UIUtilities.showAlert(null, loginResponse.getMessage(), AlertType.INFORMATION);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        listener.clear();
    }

    private void handleLoginSuccess(ResponseWrapper response) {
        UserResponse user = (UserResponse)response.getData();
        // Debug.printObject(userResponse);
        ResponseWrapper newResponse = new ResponseWrapper(Result.OK(), new SystemUser(user), response.getMessage());
        switch (user.getRole()) {
            case MANAGER: {
            } break;

            case STAFF: {
            } break;
        }
    }
}
