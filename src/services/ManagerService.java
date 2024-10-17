package services;

import repository.UserRepository;
import utilities.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

import common.AppConstant;
import common.Result;
import dto.request.UserRequest;

public class ManagerService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ManagerService(UserRepository userRepository) {
        this.userRepository = userRepository;
        passwordEncoder = new PasswordEncoder();
    }

    public Map<Object, Object> createUser(UserRequest request) {
        Map<Object, Object> resultExecute = new HashMap<>();
        
        if(userRepository.findUserByUserName(request.getUserName()) == null) {
            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Adready exist user");
        }                
    }
}
