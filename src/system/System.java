package system;

import java.util.Map;

import common.AppConstant;
import dao.User;
import dto.request.AuthRequest;
import dto.response.UserResponse;
import mapper.UserMapper;

public class System {
    private UI ui;
    private User user;
    private Repositories repositories;
    private Services services;
    
    public System() {
        ui = new UI();
        repositories = new Repositories();
        services = new Services(repositories);

        ui.loginGUI.loginListener.biConsumer = (userName, password) -> login(userName, password); 
    }

    public void login(String userName, String password) {
        Map<Object, Object> result = services.getAuthService().doLogin(new AuthRequest(userName, password));
        if(result.get(AppConstant.RESPONSE_KEY.RESULT).equals(AppConstant.NOT_OK_RESULT)) {
            //do like print the message to the window
            String message = result.get(AppConstant.RESPONSE_KEY.MESSAGE).toString();
            return;
        }

        UserMapper mapper = new UserMapper();
        user = mapper.toUser((UserResponse)result.get(AppConstant.RESPONSE_KEY.DATA));
        ui.changeToUser(user.getRole());
    }    
}
