package mapper;

import dao.User;
import dto.request.UserRequest;
import dto.response.UserResponse;

public class UserMapper {
    
    public User toUser(UserRequest userRequest) {
        User user = new User();
        user.setUserName(userRequest.getUserName());
        user.setEncodePassword(userRequest.getEncodePassword());
        user.setPassword(userRequest.getPassword());
        user.setFirstName(userRequest.getPassword());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setRole(userRequest.getRole());
        return user;
    }

    public User toUser(UserResponse userResponse) {
        User user = new User();
        user.setUserName(userResponse.getUserName());
        user.setEncodePassword(userResponse.getEncodePassword());
        user.setPassword(userResponse.getPassword());
        user.setFirstName(userResponse.getPassword());
        user.setFirstName(userResponse.getFirstName());
        user.setLastName(userResponse.getLastName());
        user.setRole(userResponse.getRole());
        return user;
    }

    public UserResponse toResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserName(user.getUserName());
        userResponse.setEncodePassword(user.getEncodePassword());
        userResponse.setPassword(user.getPassword());
        userResponse.setFirstName(user.getPassword());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setRole(user.getRole());
        return userResponse;
    }
}
