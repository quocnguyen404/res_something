package services;

import dao.User;
import dto.request.UserRequest;
import mapper.UserMapper;
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

    public void addAdministrator(UserRequest request) {
        if(userRepository.findObjectByKey(request.getUserName()) != null) {
            // System.out.println("Already exist userName");
            return;
        }

        String encodePassword = passwordEncoder.encode(request.getPassword());
        UserMapper mapper = new UserMapper();

        User user = mapper.toUser(request);
        user.setEncodePassword(encodePassword);
        userRepository.saveObject(user);
    }

    public void removeAdministrator(UserRequest request) {
    }
}
