package services;

import common.AppConstant;
import common.Result;
import dao.User;
import dto.request.ChangePasswordRequest;
import dto.request.UserRequest;
import dto.response.ResponseWrapper;
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

    public ResponseWrapper register(UserRequest request) {
        Map<Object, Object> resultExecute = new HashMap<>();
        try {
            if(userRepository.findObjectByKey(request.getUserName()) != null) {
                resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
                resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Already exist user");
            } else {
                String encodePassword = passwordEncoder.encode(request.getPassword());
                UserMapper mapper = new UserMapper();

                User user = mapper.toUser(request);
                user.setEncodePassword(encodePassword);
                userRepository.saveObject(user);
                
                resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
                resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Success register");
                resultExecute.put(AppConstant.RESPONSE_KEY.DATA, mapper.toResponse(user));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseWrapper(resultExecute);
    }

    public ResponseWrapper changePassword(ChangePasswordRequest request) {
        Map<Object, Object> resultExecute = new HashMap<>();

        User currentUser = userRepository.findObjectByKey(request.getUserName());

        if(currentUser == null) {
            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
            resultExecute.put(AppConstant.RESPONSE_KEY.DATA, null);
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Not exist user");
            return new ResponseWrapper(resultExecute);
        }
        
        String encodePw = passwordEncoder.encode(request.getCurrentPassword());
        
        boolean rightPw = passwordEncoder.matches(encodePw, currentUser.getPassword()); 
        if(!rightPw) {
            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
            resultExecute.put(AppConstant.RESPONSE_KEY.DATA, null);
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Wrong password");
            return new ResponseWrapper(resultExecute);
        }

        boolean rightCofirmPw = request.getNewPassword().equals(request.getConfirmPassword());
        if(!rightCofirmPw) {
            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT,Result.NotOK());
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Wrong confirm password");
            resultExecute.put(AppConstant.RESPONSE_KEY.DATA, null);
            return new ResponseWrapper(resultExecute);
        }

        currentUser.setPassword(request.getNewPassword());
        currentUser.setEncodePassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.updateObject(currentUser);
        
        resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
        resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Change password success");
        resultExecute.put(AppConstant.RESPONSE_KEY.DATA, new UserMapper().toResponse(currentUser));

        return new ResponseWrapper(resultExecute);
    }
}
