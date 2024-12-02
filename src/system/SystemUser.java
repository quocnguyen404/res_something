package system;

import dao.Role;
import dto.response.UserResponse;

public class SystemUser {
    private UserResponse userData;

    public SystemUser(UserResponse userResponse) {
        userData = userResponse;
    }

    public String getFirstName() {
        return userData.getFirstName();
    }

    public String getLastName() {
        return userData.getLastName();
    }

    public String getFullName() {
        return userData.getFirstName() + " " + userData.getLastName();
    }

    public Role getRole() {
        return userData.getRole();
    }
}
