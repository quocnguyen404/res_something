package system;

import javafx.stage.Stage;
import dao.Role;
import dto.request.UserRequest;
import javafx.application.Application;

public class System extends Application{
    private UI ui;
    private SystemUser user;
    private Repositories repositories;
    private Services services;
    
    public System() {
        ui = new UI();
        repositories = new Repositories();
        services = new Services(repositories);
        
        preRun();
        ui.bindEvent(services);
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
}
