package system;

import listener.ConsumerListener;
import listener.Event;
import listener.FunctionListener;
import listener.ServiceListener;

import java.time.LocalDate;

import dao.Role;
import dto.request.*;
import dto.response.ResponseWrapper;
import dto.response.UserResponse;
import javafx.stage.Stage;
import javafx.application.Application;

public class System extends Application{
    private UI ui;
    private Repositories repositories;
    private Services services;
    
    public System() {
        
        ui = new UI();
        repositories = new Repositories();
        services = new Services(repositories);
        
        preRun();
        bindSystemEvent();
    }

    @Override
    public void start(Stage arg0) throws Exception {
        ui.start(arg0);
    }

    private void preRun() {
        //add manager
        UserRequest admin = new UserRequest();
        admin.setUserName("admin");
        admin.setPassword("admin123");
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setRole(Role.MANAGER);
        services.getDevService().addAdministrator(admin);
    }

    private void bindSystemEvent() {
        //system service
        ServiceListener<AuthRequest> authenticate = new ServiceListener<>(services.getAuthService()::doLogin);
        EventDispatcher.addEvent(Event.Authenticate, authenticate);
        
        ServiceListener<UserRequest> register = new ServiceListener<>(services.getUserService()::register);
        EventDispatcher.addEvent(Event.Register, register);

        FunctionListener<ResponseWrapper> getDishes = new FunctionListener<>(services.getSystemService()::getDishes);
        EventDispatcher.addEvent(Event.GetDishes, getDishes);

        ServiceListener<CreateOrderRequest> createOrder = new ServiceListener<>(services.getSystemService()::createOrder);
        EventDispatcher.addEvent(Event.CreateOrder, createOrder);

        ServiceListener<OrderRequest> submitOrder = new ServiceListener<>(services.getSystemService()::submitOrder);
        EventDispatcher.addEvent(Event.SubmitOrder, submitOrder);

        ServiceListener<FeedbackRequest> createFeedback = new ServiceListener<>(services.getSystemService()::createFeedback);
        EventDispatcher.addEvent(Event.CreateFeedback, createFeedback);

        ConsumerListener<UserResponse> bindManagerListener = new ConsumerListener<>(this::bindManagerEvent);
        EventDispatcher.addEvent(Event.BindManagerEvent, bindManagerListener);
    
        ConsumerListener<UserResponse> bindEmployeeListener = new ConsumerListener<>(this::bindEmployeeEvent);
        EventDispatcher.addEvent(Event.BindEmployeeEvent, bindEmployeeListener);
    }

    private void bindManagerEvent(UserResponse user) {
        try {
            if(user.getRole() != Role.MANAGER) {
                throw new Exception("Do not permission to access services");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        ServiceListener<LocalDate> getAttendanceList = new ServiceListener<>(services.getManagerService()::getAttendances);
        EventDispatcher.addEvent(Event.GetAttendances, getAttendanceList);

        ServiceListener<UserRequest> updateUser = new ServiceListener<>(services.getManagerService()::updateUser);
        EventDispatcher.addEvent(Event.UpdateUser, updateUser);
        
        ServiceListener<UserRequest> deleteUser = new ServiceListener<>(services.getManagerService()::deleteUser);
        EventDispatcher.addEvent(Event.DeleteUser, deleteUser);
    
        ServiceListener<DishRequest> addDish = new ServiceListener<>(services.getManagerService()::addDish);
        EventDispatcher.addEvent(Event.AddDish, addDish);

        ServiceListener<DishRequest> deleteDish = new ServiceListener<>(services.getManagerService()::deleteDish);
        EventDispatcher.addEvent(Event.DeleteDish, deleteDish);
        
        ServiceListener<UpdateDishRequest> updateDish = new ServiceListener<>(services.getManagerService()::updateDish);
        EventDispatcher.addEvent(Event.UpdateDish, updateDish);
        
        ServiceListener<LocalDate> getFeedbacks = new ServiceListener<>(services.getManagerService()::getFeedbacks);
        EventDispatcher.addEvent(Event.GetFeedbacks, getFeedbacks);        
    
        ServiceListener<ChangePasswordRequest> changePassword = new ServiceListener<>(services.getUserService()::changePassword);
        EventDispatcher.addEvent(Event.ChangePassword, changePassword);
    }

    private void bindEmployeeEvent(UserResponse user) {
        try {
            if(user.getRole() != Role.STAFF) {
                throw new Exception("Do not permission to access services");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        ServiceListener<ChangePasswordRequest> changePassword = new ServiceListener<>(services.getUserService()::changePassword);
        EventDispatcher.addEvent(Event.ChangePassword, changePassword);

        ServiceListener<AttendanceRequest> checkIn = new ServiceListener<>(services.getUserService()::checkIn);
        EventDispatcher.addEvent(Event.CheckIn, checkIn);

        ServiceListener<AttendanceRequest> checkOut = new ServiceListener<>(services.getUserService()::checkOut);
        EventDispatcher.addEvent(Event.CheckOut, checkOut);
    }
}
