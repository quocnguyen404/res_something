package services;

import dao.User;
import dto.request.UserRequest;
import repository.DishRepository;
import repository.UserRepository;
import utilities.PasswordEncoder;

public class DevService {
    private final UserRepository userRepository;
    private final DishRepository dishRepository;
    private final PasswordEncoder passwordEncoder;

    public DevService(UserRepository userRepository, DishRepository dishRepository) {
        this.userRepository = userRepository;
        this.dishRepository = dishRepository;
        passwordEncoder = new PasswordEncoder();
    }

    public void addAdministrator(UserRequest userRequest) {
        if(userRepository.findObjectByKey(userRequest.getUserName()) != null) {
            System.out.println("Already exist userName");
            return;
        }

        
    }

    public void removeAdministrator(UserRequest userRequest) {

    }
}
