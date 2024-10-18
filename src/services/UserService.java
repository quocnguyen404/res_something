package services;

import common.AppConstant;
import common.Result;
import dao.User;
import dto.request.UserChangePasswordRequest;
import dto.request.UserRequest;
import dto.response.UserResponse;
import dto.response.baseResponse.Response;
import mapper.UserMapper;
import repository.UserRepository;
import utilities.PasswordEncoder;

import java.util.Map;
import java.util.HashMap;

public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        passwordEncoder = new PasswordEncoder();
    }

    public Map<Object, Object> register(UserRequest request) {
        Map<Object, Object> resultExecute = new HashMap<>();
        try {
            if(userRepository.findUserByUserName(request.getUserName()) != null) {
                resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
                resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Already exist user");
            } else {
                String encodePassword = passwordEncoder.encode(request.getPassword());
                UserMapper mapper = new UserMapper();

                User user = mapper.toUser(request);
                user.setEncodePassword(encodePassword);
                userRepository.saveUser(user);
                
                resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
                resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Success register");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultExecute;
    }

    public Map<Object, Object> changePassword(UserChangePasswordRequest request, User currentUser) {
        Map<Object, Object> resultExecute = new HashMap<>();

        try {
            boolean rightPw = passwordEncoder.matches(currentUser.getEncodePassword(), request.getCurrentPassword(); 
            boolean rightCofirmPw = request.getNewPassword().equals(request.getConfirmPassword());
            if(!rightPw) {
                resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
                resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Wrong password");
            }
            if(!rightCofirmPw) {
                resultExecute.put(AppConstant.RESPONSE_KEY.RESULT,Result.NotOK());
                resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Wrong confirm password");
            }
            if(rightPw && rightCofirmPw) {
                currentUser.setPassword(request.getNewPassword());
                currentUser.setEncodePassword(passwordEncoder.encode(request.getNewPassword()));
                userRepository.updateUser(currentUser);
                resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
                resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Change password success");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return resultExecute;
    }
}
