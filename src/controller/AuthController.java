package controller;

import dto.request.AuthRequest;
import repository.UserRepository;
import services.AuthService;

public class AuthController {
    private AuthService authService;

    public AuthController(UserRepository userRepository) {
        authService = new AuthService(userRepository);
    }

    public void doLogin(String userName, String password) {
        AuthRequest authRequest = new AuthRequest(userName, password);
        
    }
}
