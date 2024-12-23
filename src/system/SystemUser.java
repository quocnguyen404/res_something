package system;

import dao.Role;
import dto.response.UserResponse;

public class SystemUser {
    private UserResponse userData;

    SystemUser(UserResponse userResponse) {
        userData = userResponse;
    }

    public String getUserName() {
        return userData.getUserName();
    }

    public String getFirstName() {
        return userData.getFirstName();
    }

    public void setFirstName(String firstName) {
        userData.setFirstName(firstName);
    }

    public String getLastName() {
        return userData.getLastName();
    }

    public void setLastName(String lastName) {
        userData.setLastName(lastName);
    }

    public String getFullName() {
        return userData.getFirstName() + " " + userData.getLastName();
    }

    public Role getRole() {
        return userData.getRole();
    }
}
