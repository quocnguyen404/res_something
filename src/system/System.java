package system;

import javafx.stage.Stage;
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


        ui.bindEvent(services);
    }

    @Override
    public void start(Stage arg0) throws Exception {
        ui.start(arg0);
    }

    private void preRun() {
        //add manager
        services.getManagerService().createUser(new UserRequest());
    }
}
