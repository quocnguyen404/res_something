package system;

import dto.response.ResponseWrapper;
import dto.response.UserResponse;
import gui.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import listener.*;

public class UI {
    private SystemUser user;

    //auth
    private LoginGUI loginGUI;
    private RegisterGUI registerGUI;
    
    //management services
    private ManagerManagementGUI managementGUI;
    private FoodManagementGUI foodManagementGUI;
    private StatisticsManagementGUI statisticsManagementGUI;
    private UserManagerGUI userManagerGUI;
    
    //user services
    private OrderGUI orderGUI;
    private FeedBackGUI feedBackGUI;
    private ChangePasswordGUI changePasswordGUI;
    private CheckAttendanceGUI checkAttendanceGUI;

    public UI() {
        loginGUI = new LoginGUI();
        registerGUI = new RegisterGUI();

        managementGUI = new ManagerManagementGUI();
        bindEvent();
    }

    public void start(Stage arg0) {
        loginGUI.start(arg0);
    }

    private void bindEvent() {
        ActionListener handleLogin = new ActionListener(this::handleLogin);
        EventDispatcher.addEvent(Event.HandleLogin, handleLogin);

        ActionListener handleRegister = new ActionListener(this::handleRegister);
        EventDispatcher.addEvent(Event.HandleRegister, handleRegister);
        
        ConsumerListener<Stage> loginUI = new ConsumerListener<>(this.loginGUI::start);
        EventDispatcher.addEvent(Event.LoginUI, loginUI);

        ConsumerListener<Stage> registerUI = new ConsumerListener<>(this.registerGUI::start);
        EventDispatcher.addEvent(Event.RegisterUI, registerUI);
    }
    
    private void handleLogin() {
        Listener listener = EventDispatcher.getListener(Event.Authenticate);
        ResponseWrapper loginResponse = listener.getResponse();
        try {
            if(loginResponse.isOK()) {
                handleLoginSuccess(loginResponse);
            } else {
                UIUtilities.showAlert("Alert", loginResponse.getMessage(), AlertType.ERROR);
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
                EventDispatcher.invoke(Event.BindManagerEvent, userResponse);
                managementGUI = new ManagerManagementGUI();
                //TODO open management ui
                // managementGUI.start(arg0);
            } break;
            
            case STAFF: {
                EventDispatcher.invoke(Event.BindEmployeeEvent, userResponse);
            } break;
        }
    }

    private void handleRegister() {
        Listener listener = EventDispatcher.getListener(Event.Register);
        ResponseWrapper registerResponse = listener.getResponse();
        try {
            if(registerResponse.isOK()) {
                UIUtilities.showAlert("Alert", registerResponse.getMessage(), AlertType.INFORMATION);
                
                try {
                    //TODO open login ui
                    // loginGUI.start(arg0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                UIUtilities.showAlert("Alert", registerResponse.getMessage(), AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        listener.clear();
    }
}
