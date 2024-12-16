package system;

import javax.swing.Action;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import debug.Debug;
import gui.*;
import listener.*;
import dto.response.UserResponse;
import dto.response.ResponseWrapper;

public class UI {
    private SystemUser user;
    private Stage mainStage;

    //auth
    private LoginGUI loginGUI;
    private RegisterGUI registerGUI;
    
    //management services
    private ManagerManagementGUI managementGUI;
    private UserManagerGUI userManagerGUI;
    private UserGUI userGUI;
    private UpdateUserGUI updateUserGUI;

    private FoodManagementGUI foodManagementGUI;
    private AddDishGUI addDishGUI;

    private StatisticsManagementGUI statisticsManagementGUI;
    
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

        userManagerGUI = new UserManagerGUI();
        updateUserGUI = new UpdateUserGUI();

        foodManagementGUI = new FoodManagementGUI();
        addDishGUI = new AddDishGUI();

        bindEvent();
    }

    public void start(Stage arg0) {
        mainStage = arg0;
        loginGUI.start(mainStage);
        // foodManagementGUI.start(arg0);
        // addDishGUI.start(arg0);
    }

    private SystemUser getUser() {
        return this.user;
    }

    private void bindEvent() {
        //Handle UI event
        ActionListener handleLogin = new ActionListener(this::handleLogin);
        EventDispatcher.addEvent(Event.HandleLogin, handleLogin);

        ActionListener handleRegister = new ActionListener(this::handleRegister);
        EventDispatcher.addEvent(Event.HandleRegister, handleRegister);
        
        ActionListener handleChangPassword = new ActionListener(this::handleChangePassword);
        EventDispatcher.addEvent(Event.HandleChangePassword, handleChangPassword);
        
        ActionListener handleUpdateUser = new ActionListener(this::handleUpdateUser);
        EventDispatcher.addEvent(Event.HandleUpdateUser, handleUpdateUser);
        
        ActionListener handleDeleteUser = new ActionListener(this::handleDeleteUser);
        EventDispatcher.addEvent(Event.HandleDeletUser, handleDeleteUser);

        ActionListener handleAddDish = new ActionListener(this::handleAddDish);
        EventDispatcher.addEvent(Event.HandleAddDish, handleAddDish);

        ActionListener handleDeleteDish = new ActionListener(this::handleDeleteDish);
        EventDispatcher.addEvent(Event.HandleDeleteDish, handleDeleteDish);

        FunctionListener<SystemUser> getUser = new FunctionListener<>(this::getUser);
        EventDispatcher.addEvent(Event.GetSystemUser, getUser);

        ////UI EVENT
        //Login UI
        ConsumerListener<Stage> loginUI = new ConsumerListener<>(this.loginGUI::start);
        EventDispatcher.addEvent(Event.LoginUI, loginUI);

        //Register UI
        ConsumerListener<Stage> registerUI = new ConsumerListener<>(this.registerGUI::start);
        EventDispatcher.addEvent(Event.RegisterUI, registerUI);

        //Manager UI
        ConsumerListener<Stage> managerUI = new ConsumerListener<>(this.managementGUI::start);
        EventDispatcher.addEvent(Event.ManagerManagementUI, managerUI);

        //User UI
        ConsumerListener<Stage> userUI = new ConsumerListener<>(this.userGUI::start);
        EventDispatcher.addEvent(Event.UserUI, userUI);

        //User Management UI
        ConsumerListener<Stage> userManagementUI = new ConsumerListener<>(this.userManagerGUI::start);
        EventDispatcher.addEvent(Event.UserManagementUI, userManagementUI);

        //Update User UI
        ConsumerListener<Stage> updateUserUI = new ConsumerListener<>(this.updateUserGUI::start);
        EventDispatcher.addEvent(Event.UpdateUserUI, updateUserUI);

        //Dish Management UI
        ConsumerListener<Stage> dishManagementUI = new ConsumerListener<>(this.foodManagementGUI::start);
        EventDispatcher.addEvent(Event.FoodManagementUI, dishManagementUI);

        //Add Dish UI
        ConsumerListener<Stage> addDishUI = new ConsumerListener<>(this.addDishGUI::start);
        EventDispatcher.addEvent(Event.AddDishUI, addDishUI);

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

    private void handleUpdateUser() {
        Listener listener = EventDispatcher.getListener(Event.UpdateUser);
        ResponseWrapper response = listener.getData();
        UIUtilities.showAlert("Information", response.getMessage(), AlertType.INFORMATION);
        listener.clearData();
    }

    private void handleDeleteUser() {
        Listener listener = EventDispatcher.getListener(Event.DeleteUser);
        ResponseWrapper response = listener.getData();
        UIUtilities.showAlert("Information", response.getMessage(), AlertType.INFORMATION);
        listener.clearData();
    }

    private void handleAddDish() {
        Listener listener = EventDispatcher.getListener(Event.AddDish);
        ResponseWrapper response = listener.getData();
        UIUtilities.showAlert("Information", response.getMessage(), AlertType.INFORMATION);
        listener.clearData();
    }

    private void handleDeleteDish() {
        Listener listener = EventDispatcher.getListener(Event.DeleteDish);
        ResponseWrapper response = listener.getData();
        UIUtilities.showAlert("Information", response.getMessage(), AlertType.INFORMATION);
        listener.clearData();
    }
}
