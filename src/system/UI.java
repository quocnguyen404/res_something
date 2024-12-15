package system;

import debug.Debug;
import dto.response.ResponseWrapper;
import dto.response.UserResponse;
import gui.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import listener.*;

public class UI {
    private SystemUser user;
    private Stage mainStage;

    //auth
    private LoginGUI loginGUI;
    private RegisterGUI registerGUI;
    
    //management services
    private ManagerManagementGUI managementGUI;
    private FoodManagementGUI foodManagementGUI;
    private StatisticsManagementGUI statisticsManagementGUI;
    private UserGUI userGUI;
    
    //user services
    private OrderGUI orderGUI;
    private FeedBackGUI feedBackGUI;
    private ChangePasswordGUI changePasswordGUI;
    private CheckAttendanceGUI checkAttendanceGUI;

    public UI() {
        loginGUI = new LoginGUI();
        registerGUI = new RegisterGUI();

        managementGUI = new ManagerManagementGUI();
        userGUI = new UserGUI();
        bindEvent();
    }

    public void start(Stage arg0) {
        mainStage = arg0;
        loginGUI.start(mainStage);
    }

    private SystemUser getUser() {
        return this.user;
    }

    private void bindEvent() {
        ActionListener handleLogin = new ActionListener(this::handleLogin);
        EventDispatcher.addEvent(Event.HandleLogin, handleLogin);

        ActionListener handleRegister = new ActionListener(this::handleRegister);
        EventDispatcher.addEvent(Event.HandleRegister, handleRegister);
        
        ActionListener handleChangPassword = new ActionListener(this::handleChangePassword);
        EventDispatcher.addEvent(Event.HandleChangePassword, handleChangPassword);

        ConsumerListener<Stage> loginUI = new ConsumerListener<>(this.loginGUI::start);
        EventDispatcher.addEvent(Event.LoginUI, loginUI);

        ConsumerListener<Stage> registerUI = new ConsumerListener<>(this.registerGUI::start);
        EventDispatcher.addEvent(Event.RegisterUI, registerUI);

        ConsumerListener<Stage> managerUI = new ConsumerListener<>(this.managementGUI::start);
        EventDispatcher.addEvent(Event.ManagerManagementUI, managerUI);

        ConsumerListener<Stage> userUI = new ConsumerListener<>(this.userGUI::start);
        EventDispatcher.addEvent(Event.UserUI, userUI);

        FunctionListener<SystemUser> getUser = new FunctionListener<>(this::getUser);
        EventDispatcher.addEvent(Event.GetSystemUser, getUser);
    }
    
    private void handleLogin() {
        Listener listener = EventDispatcher.getListener(Event.Authenticate);
        ResponseWrapper loginResponse = listener.getData();
        UIUtilities.showAlert("Information", loginResponse.getMessage(), AlertType.INFORMATION);

        if(loginResponse.isOK()) {
            UserResponse userResponse = (UserResponse)loginResponse.getData();
            user = new SystemUser(userResponse);
            
            //close main stage (login ui)
            mainStage.close();
            try {
                loginGUI.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }

            switch (user.getRole()) {
                case MANAGER: {
                    EventDispatcher.invoke(Event.BindManagerEvent, userResponse);
                    listener.clearData();
                    EventDispatcher.invoke(Event.ManagerManagementUI, mainStage);
                } break;
                
                case STAFF: {
                    EventDispatcher.invoke(Event.BindEmployeeEvent, userResponse);
                    listener.clearData();
                    EventDispatcher.invoke(Event.UserUI, mainStage);
                } break;
            }
        } 
        listener.clearData();
    }

    private void handleRegister() {
        Listener listener = EventDispatcher.getListener(Event.Register);
        ResponseWrapper response = listener.getData();
        UIUtilities.showAlert("Information", response.getMessage(), AlertType.INFORMATION);
        listener.clearData();
    }

    private void handleChangePassword() {
        Listener listener = EventDispatcher.getListener(Event.HandleChangePassword);
        ResponseWrapper response = listener.getData();
        UIUtilities.showAlert("Information", response.getMessage(), AlertType.INFORMATION);
        listener.clearData();
    }
}
