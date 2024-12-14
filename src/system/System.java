package system;

import listener.ConsumerListener;
import listener.Event;
import listener.NonRequestListener;
import listener.ServiceListener;


import dao.Role;
import dto.request.*;
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
        ServiceListener<AuthRequest> authenticateListener = new ServiceListener<>(services.getAuthService()::doLogin);
        EventDispatcher.addEvent(Event.Authenticate, authenticateListener);
        
        ServiceListener<UserRequest> registerListener = new ServiceListener<>(services.getUserService()::register);
        EventDispatcher.addEvent(Event.Register, registerListener);

        ServiceListener<AttendanceRequest> attendanceListener = new ServiceListener<>(services.getSystemService()::checkAttendance);
        EventDispatcher.addEvent(Event.Attendance, attendanceListener);
        
        NonRequestListener createOrderListener = new NonRequestListener(services.getSystemService()::createOrder);
        EventDispatcher.addEvent(Event.CreateOrder, createOrderListener);

        ServiceListener<OrderRequest> submitOrderListener = new ServiceListener<>(services.getSystemService()::submitOrder);
        EventDispatcher.addEvent(Event.SubmitOrder, submitOrderListener);

        NonRequestListener logoutListener = new NonRequestListener(services.getSystemService()::doLogout);
        EventDispatcher.addEvent(Event.Logout, logoutListener);

        ConsumerListener<UserResponse> bindManagerListener = new ConsumerListener<>(this::bindManagerEvent);
        EventDispatcher.addEvent(Event.BindManagerEvent, bindManagerListener);
    
        ConsumerListener<UserResponse> bindEmployeeListener = new ConsumerListener<>(this::BindEmployeeEvent);
        EventDispatcher.addEvent(Event.BindEmployeeEvent, bindEmployeeListener);
    }

    private void bindManagerEvent(UserResponse user) {
        try {
            if(user.getRole() != Role.MANAGER) {
                throw new Exception("Do not permission to access services");
            }
        } catch (Exception e) {
            //TODO show alert do login again
            e.printStackTrace();
            return;
        }
        //TODO bind manager services
        
    }

    private void BindEmployeeEvent(UserResponse user) {
        try {
            if(user.getRole() != Role.STAFF) {
                throw new Exception("Do not permission to access services");
            }
        } catch (Exception e) {
            // TODO show alert do login again
            e.printStackTrace();
            return;
        }

        //TODO bind manager services
    }
}
