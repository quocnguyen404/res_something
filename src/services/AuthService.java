package services;

import repository.UserRepository;
import utilities.PasswordEncoder;
import dto.request.AuthRequest;
import mapper.UserMapper;
import common.AppConstant;
import common.Result;
import dao.User;

import java.util.Map;
import java.util.HashMap;

public class AuthService {
    private final UserRepository userRepository;

    private static final int MAX_LOGIN_COUNT = 3;
    private PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        passwordEncoder = new PasswordEncoder();
    }

    public Map<Object, Object> doLogin(AuthRequest request) {
        Map<Object, Object> resultExecute = new HashMap<>();
        User user = userRepository.findObjectByKey(request.getUserName());
        
        if(user == null) {
            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Not exist user name");  
        } else {
            boolean match = passwordEncoder.matches(user.getEncodePassword(), request.getPassword());
            if(!match) {
                if(user.getLoginCount() == 0) {
                    //handle user login
                    user.setLoginCount(MAX_LOGIN_COUNT);
                    userRepository.updateObject(user);
                }
                user.setLoginCount(user.getLoginCount() - 1);
                resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
                resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Wrong password");
            } else {
                UserMapper mapper = new UserMapper();
                resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
                resultExecute.put(AppConstant.RESPONSE_KEY.DATA, mapper.toResponse(user));
            }
        }
        return resultExecute;
    }
}
