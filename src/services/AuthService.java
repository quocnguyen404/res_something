package services;

import repository.UserRepository;
import utilities.PasswordEncoder;
import dto.request.AuthRequest;
import dto.response.AuthResponse;
import dto.response.baseResponse.Response;

import common.Result;

public class AuthService {
    private final UserRepository userRepository;

    private static final int MAX_LOGIN_COUNT = 3;
    private PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Response<AuthResponse> doLogin(AuthRequest request) {
        Result result = new Result();
        AuthResponse authResponse = new AuthResponse();       
        Response<AuthResponse> response = new Response<>();
        response.setResult(result);
        response.setData(authResponse);

        try {
            dao.User user = userRepository.findUserByUserName(request.getUserName());
            if(user == null) {
                result = Result.NotOK();
                authResponse = null;
                response.setMessage("Not find user");
                response.setOK(false);
                return response;
            }

            if(user.getLoginCount() >= MAX_LOGIN_COUNT) {
                result = Result.NotOK();
                response.setMessage("Wrong login too much");
                response.setOK(false);
                return response;
            }

            passwordEncoder = new PasswordEncoder();
            String encodePassword = passwordEncoder.hashPassword(request.getPassword());
            boolean match = user.getEncodePassword().equals(encodePassword);
            
            if(!match) {
                result = Result.NotOK();
                response.setMessage("Wrong password");
                response.setOK(false);
                return response;
            }
                

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
