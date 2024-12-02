package system;

import dto.response.ResponseWrapper;
import dto.response.UserResponse;
import listener.Event;
import listener.Listener;

public class UIHandler {
    
    public static void handleLogin() {
        Listener lis = EventDispatcher.getEvent(Event.Login);
        ResponseWrapper loginResponse = lis.getResponse();
        lis.clear();
        try {
            UserResponse response = (UserResponse)loginResponse.getData();
            SystemUser user = new SystemUser(response);

            if(loginResponse.isOK()) {
                //TODO add login success event
                EventDispatcher.invoke(Event.LoginSuccess, user);
            } else {
                //TODO add login fail event
                EventDispatcher.invoke(Event.LoginFail, loginResponse.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
