package system;

import java.awt.event.ActionListener;

import gui.LoginGUI;

public class GUI {
    private LoginGUI loginGUI;
    
    GUI() {
        loginGUI = new LoginGUI();
    }

    void addLoginButtonListener(ActionListener listener) {
        loginGUI.onClickLoginButton = listener;
    }
}
