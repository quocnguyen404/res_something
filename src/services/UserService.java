package services;

import common.Result;
import dao.User;
import dto.request.UserRequest;
import dto.response.UserResponse;
import dto.response.baseResponse.Response;
import mapper.UserMapper;
import repository.UserRepository;
import utilities.PasswordEncoder;

import java.io.IOException;

public class UserService {
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Response<UserResponse> register(UserRequest request) {
        Result result = new Result();
        UserResponse userResponse = new UserResponse();
        Response<UserResponse> response = new Response<>();
        response.setResult(result);
        response.setData(userResponse);

        try {
            if(userRepository.findUserByUserName(request.getUserName()) != null) {
                result = Result.NotOK();
                userResponse = null;
                response.setMessage("Already exist user name");
                response.setOK(false);
            }
            else {
                result = Result.OK();
                passwordEncoder = new PasswordEncoder();
                String encodePassword = passwordEncoder.hashPassword(request.getPassword());
                UserMapper mapper = new UserMapper();

                User user = mapper.toUser(request);
                userResponse = mapper.toResponse(user);
                user.setEncodePassword(encodePassword);
                userRepository.saveUser(user);

                response.setMessage("Register success");
                response.setOK(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
