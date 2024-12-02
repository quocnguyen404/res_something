package listener;

import java.util.function.Function;

import dto.request.AuthRequest;
import dto.response.ResponseWrapper;

public class LoginListener extends Listener{
    private Function<AuthRequest, ResponseWrapper> callback;

    public LoginListener() {
    }

    public LoginListener(Function<AuthRequest, ResponseWrapper> callback) {
        this.callback = callback;
    }

    public void bindCallback(Function<AuthRequest, ResponseWrapper> callback) {
        this.callback = callback;
    }

    @Override
    public <T, T1> void invoke(T d, T1 d1) {
        try {
            String userName = (String) d;
            String password = (String) d1;
            AuthRequest authRequest = new AuthRequest(userName, password);
            response = callback.apply(authRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // public void invoke(String userName, String password) {
    //     try {
    //         AuthRequest authRequest = new AuthRequest(userName, password);
    //         response = callback.apply(authRequest);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }
}
