package controller;

import dto.request.UserRequest;
import dto.response.UserResponse;
import dto.response.baseResponse.Response;
import services.UserService;

public class UserController {
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    public Response<UserResponse> register(UserRequest request) {
        return userService.register(request);
    }
}
