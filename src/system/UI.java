package system;

import gui.*;
import javafx.stage.Stage;
import listener.Event;
import listener.LoginListener;

public class UI {
    private LoginGUI loginGUI;

    public UI() {
        loginGUI = new LoginGUI();
    }

    public void start(Stage arg0) {
        loginGUI.start(arg0);
    }

    public void bindEvent(Services services) {
        
        LoginListener loginListener = new LoginListener(services.getAuthService()::doLogin);
        EventDispatcher.addEvent(Event.Login, loginListener);
    }
}
