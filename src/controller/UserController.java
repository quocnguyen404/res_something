package controller;

import dto.request.UserRequest;
import services.UserService;

public class UserController {
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    public void register(UserRequest request) {
        // return userService.register(request);
    }
}
