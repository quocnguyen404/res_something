package system;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import gui.*;
import listener.*;
import dto.response.UserResponse;
import dto.request.AttendanceRequest;
import dto.request.ChangePasswordRequest;
import dto.response.AttendanceResponse;
import dto.response.CreateOrderResponse;
import dto.response.DishResponse;
import dto.response.FeedbackResponse;
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
    private UpdateDishGUI updateDishGUI;
    private OrderGUI orderGUI;

    private FeedBackGUI feedBackGUI;
    private CreateFeedbackGUI createFeedbackGUI;
    private ChangePasswordGUI changePasswordGUI;
    private AttendanceGUI attendanceGUI;
    
    // private StatisticsManagementGUI statisticsManagementGUI;

    public UI() {
        loginGUI = new LoginGUI();
        registerGUI = new RegisterGUI();

        managementGUI = new ManagerManagementGUI();
        userGUI = new UserGUI();

        userManagerGUI = new UserManagerGUI();
        updateUserGUI = new UpdateUserGUI();

        foodManagementGUI = new FoodManagementGUI();
        addDishGUI = new AddDishGUI();
        updateDishGUI = new UpdateDishGUI();
        orderGUI = new OrderGUI();

        feedBackGUI = new FeedBackGUI();
        createFeedbackGUI = new CreateFeedbackGUI();
        changePasswordGUI = new ChangePasswordGUI();
        attendanceGUI = new AttendanceGUI();

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
        //Handle UI event
        ActionListener handleLogin = new ActionListener(this::handleLogin);
        EventDispatcher.addEvent(Event.HandleLogin, handleLogin);

        ActionListener handleRegister = new ActionListener(this::handleRegister);
        EventDispatcher.addEvent(Event.HandleRegister, handleRegister);
        
        ConsumerListener<ChangePasswordRequest> handleChangPassword = new ConsumerListener<>(this::handleChangePassword);
        EventDispatcher.addEvent(Event.HandleChangePassword, handleChangPassword);
        
        ActionListener handleUpdateUser = new ActionListener(this::handleUpdateUser);
        EventDispatcher.addEvent(Event.HandleUpdateUser, handleUpdateUser);
        
        ActionListener handleDeleteUser = new ActionListener(this::handleDeleteUser);
        EventDispatcher.addEvent(Event.HandleDeletUser, handleDeleteUser);

        ActionListener handleAddDish = new ActionListener(this::handleAddDish);
        EventDispatcher.addEvent(Event.HandleAddDish, handleAddDish);

        ActionListener handleDeleteDish = new ActionListener(this::handleDeleteDish);
        EventDispatcher.addEvent(Event.HandleDeleteDish, handleDeleteDish);

        ActionListener handleUpdateDish = new ActionListener(this::handleUpdateDish);
        EventDispatcher.addEvent(Event.HandleUpdateDish, handleUpdateDish);

        ConsumerListener<ListView<String>> handleViewDishes = new ConsumerListener<>(this::handleViewDishes);
        EventDispatcher.addEvent(Event.HandleViewDishes, handleViewDishes);

        ConsumerListener<ComboBox<String>> handleChooseDishes = new ConsumerListener<>(this::handleChooseDishes);
        EventDispatcher.addEvent(Event.HandleChooseDishes, handleChooseDishes);

        FunctionListener<SystemUser> getUser = new FunctionListener<>(this::getUser);
        EventDispatcher.addEvent(Event.GetSystemUser, getUser);

        ConsumerListener<ListView<String>> handleCreateOrder = new ConsumerListener<>(this::handleCreateOrder);
        EventDispatcher.addEvent(Event.HandleCreateOrder, handleCreateOrder);

        ConsumerListener<ListView<String>> handleGetFeedbacks = new ConsumerListener<>(this::handleGetFeedbacks);
        EventDispatcher.addEvent(Event.HandleGetFeedbacks, handleGetFeedbacks);

        ActionListener handleCreateFeedback = new ActionListener(this::handleCreateFeedback);
        EventDispatcher.addEvent(Event.HandleCreateFeedback, handleCreateFeedback);

        ActionListener handleCheckOut = new ActionListener(this::handleCheckOut);
        EventDispatcher.addEvent(Event.HandleCheckOut, handleCheckOut);

        BiConsumerListener<ListView<String>, LocalDate> handleViewAttendance = new BiConsumerListener<>(this::handleViewAttendance);
        EventDispatcher.addEvent(Event.HandleViewAttendance, handleViewAttendance);

        ////UI EVENT
        //Login UI
        ConsumerListener<Stage> loginUI = new ConsumerListener<>(this.loginGUI::start);
        EventDispatcher.addEvent(Event.LoginUI, loginUI);

        //Register UI
        ConsumerListener<Stage> registerUI = new ConsumerListener<>(this.registerGUI::start);
        EventDispatcher.addEvent(Event.RegisterUI, registerUI);

        //Manager UI
        ConsumerListener<Stage> managerUI = new ConsumerListener<>(this.managementGUI::start);
        EventDispatcher.addEvent(Event.ManagerUI, managerUI);

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

        //Update Dish UI
        ConsumerListener<Stage> updateDishUI = new ConsumerListener<>(this.updateDishGUI::start);
        EventDispatcher.addEvent(Event.UpdateDishUI, updateDishUI);
    
        //Order UI
        ConsumerListener<Stage> createOrderUI = new ConsumerListener<>(this.orderGUI::start);
        EventDispatcher.addEvent(Event.OrderUI, createOrderUI);
    
        //View feedbacks UI
        ConsumerListener<Stage> viewFeedbacksUI = new ConsumerListener<>(this.feedBackGUI::start);
        EventDispatcher.addEvent(Event.FeedbackUI, viewFeedbacksUI);

        //Create feedback UI
        ConsumerListener<Stage> createFeedbackUI = new ConsumerListener<>(this.createFeedbackGUI::start);
        EventDispatcher.addEvent(Event.CreateFeedbackUI, createFeedbackUI);

        //Change password UI
        ConsumerListener<Stage> changePasswordUI = new ConsumerListener<>(this.changePasswordGUI::start);
        EventDispatcher.addEvent(Event.ChangePasswordUI, changePasswordUI);
    
        //View attendances UI
        ConsumerListener<Stage> viewAttendanceUI = new ConsumerListener<>(this.attendanceGUI::start);
        EventDispatcher.addEvent(Event.ViewAttendanceUI, viewAttendanceUI);
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
                    EventDispatcher.invoke(Event.ManagerUI, mainStage);
                } break;
                
                case STAFF: {
                    EventDispatcher.invoke(Event.BindEmployeeEvent, userResponse);
                    listener.clearData();
                    handleCheckIn();
                    EventDispatcher.invoke(Event.UserUI, mainStage);
                } break;
            }
        } 
    }

    private void handleRegister() {
        Listener listener = EventDispatcher.getListener(Event.Register);
        ResponseWrapper response = listener.getData();
        UIUtilities.showAlert("Information", response.getMessage(), AlertType.INFORMATION);
        listener.clearData();
    }

    private void handleChangePassword(ChangePasswordRequest request) {
        request.setUserName(user.getUserName());
        EventDispatcher.invoke(Event.ChangePassword, request);
        Listener listener = EventDispatcher.getListener(Event.ChangePassword);
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

    private void handleUpdateDish() {
        Listener listener = EventDispatcher.getListener(Event.UpdateDish);
        ResponseWrapper response = listener.getData();
        UIUtilities.showAlert("Information", response.getMessage(), AlertType.INFORMATION);
        listener.clearData();
    }

    private void handleViewDishes(ListView<String> listview) {
        Listener listener = EventDispatcher.getListener(Event.GetDishes);
        ResponseWrapper response = listener.getData();
        if(!response.isOK()) {
            UIUtilities.showAlert("Information", response.getMessage(), AlertType.INFORMATION);
            return;
        }
        try {
            @SuppressWarnings("unchecked")
            List<DishResponse> dishes = (List<DishResponse>)response.getData();
            StringBuilder sb = new StringBuilder();
            for (DishResponse dish : dishes) {
                sb.append(UIUtilities.dishResponseToStr(dish));
                sb.append("\n");
            }
            listview.getItems().add(sb.toString());
        } catch (Exception e) {
            listener.clearData();
            e.printStackTrace();
        }
        listener.clearData();
    }

    private void handleChooseDishes(ComboBox<String> comboBox) {
        Listener listener = EventDispatcher.getListener(Event.GetDishes);
        ResponseWrapper response = listener.getData();
        if(!response.isOK()) {
            UIUtilities.showAlert("Information", response.getMessage(), AlertType.INFORMATION);
            return;
        }
        try {
            @SuppressWarnings("unchecked")
            List<DishResponse> dishes = (List<DishResponse>)response.getData();
            for (DishResponse dish : dishes) {
                comboBox.getItems().add(dish.getDishName());
            }
            comboBox.getSelectionModel().selectFirst();
        } catch (Exception e) {
            listener.clearData();
            e.printStackTrace();
        }
        listener.clearData();
    }

    private void handleCreateOrder(ListView<String> listView) {
        Listener listener = EventDispatcher.getListener(Event.CreateOrder);
        ResponseWrapper response = listener.getData();
        UIUtilities.showAlert("Information", response.getMessage(), AlertType.INFORMATION);
        if(!response.isOK()) {
            return;
        }
        CreateOrderResponse createOrderResponse = (CreateOrderResponse)response.getData();
        listView.getItems().addFirst("Order ID: " + createOrderResponse.getOrderID());
        listView.getItems().add("================");
        listView.getItems().add("Total price: " + createOrderResponse.getPrice() + "$");
        listener.clearData();
    }

    private void handleGetFeedbacks(ListView<String> listView) {
        Listener listener = EventDispatcher.getListener(Event.GetFeedbacks);
        ResponseWrapper response = listener.getData();
        UIUtilities.showAlert("Information", response.getMessage(), AlertType.INFORMATION);
        if(!response.isOK()) {
            // Debug.printResponse(response);
            return;
        }
        try {
            @SuppressWarnings("unchecked")
            List<FeedbackResponse> feedbacks = (List<FeedbackResponse>)response.getData();
            for (FeedbackResponse feedbackResponse : feedbacks) {
                listView.getItems().add(UIUtilities.feedbackResponseToStr(feedbackResponse));
            }
        } catch (Exception e) {
            listener.clearData();
            e.printStackTrace();
        }
        listener.clearData();
    }

    private void handleCreateFeedback() {
        Listener listener = EventDispatcher.getListener(Event.CreateFeedback);
        ResponseWrapper response = listener.getData();
        UIUtilities.showAlert("Information", response.getMessage(), AlertType.INFORMATION);
        listener.clearData();
    }

    private void handleCheckIn() {
        AttendanceRequest request = new AttendanceRequest(user.getUserName(), LocalTime.now());
        EventDispatcher.invoke(Event.CheckIn, request);
        Listener listener = EventDispatcher.getListener(Event.CheckIn);
        // ResponseWrapper response = listener.getData();
        // UIUtilities.showAlert("Information", response.getMessage(), AlertType.INFORMATION);
        listener.clearData();
    }

    private void handleCheckOut() {
        AttendanceRequest request = new AttendanceRequest(user.getUserName(), LocalTime.now());
        EventDispatcher.invoke(Event.CheckOut, request);
        Listener listener = EventDispatcher.getListener(Event.CheckOut);
        ResponseWrapper response = listener.getData();
        UIUtilities.showAlert("Information", response.getMessage(), AlertType.INFORMATION);
        listener.clearData();
    }

    private void handleViewAttendance(ListView<String> listView, LocalDate date) {
        EventDispatcher.invoke(Event.GetAttendances, date);
        Listener listener = EventDispatcher.getListener(Event.GetAttendances);
        ResponseWrapper response = listener.getData();
        UIUtilities.showAlert("Information", response.getMessage(), AlertType.INFORMATION);
        if(!response.isOK()) {
            listener.clearData();
            return;
        }

        try {
            @SuppressWarnings("unchecked")
            List<AttendanceResponse> attendances = (List<AttendanceResponse>) response.getData();
            if(attendances == null || attendances.isEmpty()) {
                listener.clearData();
                UIUtilities.showAlert("Information", "Have no attendance in " + date, AlertType.INFORMATION);
                return;
            }

            for (AttendanceResponse attendance : attendances) {
                listView.getItems().add(UIUtilities.attendanceResponseToStr(attendance));
            }
        } catch (Exception e) {
            listener.clearData();
            e.printStackTrace();
        }
        listener.clearData();
    }
}
