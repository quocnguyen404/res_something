package dao;

import java.util.List;
public class User {
    private String userName;
    private String encodePassword;
    private String password;
    private String firstName;
    private String lastName;
    private int loginCount;
    private Role role;

    // Constructor
    public User() {
    }

    public User(String userName, String encodePassword, String password, String firstName, String lastName, int loginCount, Role role) {
        this.userName = userName;
        this.encodePassword = encodePassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.loginCount = loginCount;
        this.role = role;
    }

    // Getters and Setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEncodePassword() {
        return encodePassword;
    }

    public void setEncodePassword(String encodePassword) {
        this.encodePassword = encodePassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }
    
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }

    // Returns full name
    public String getFullName() {
        return firstName + " " + lastName;
    }

    // Returns a list of permissions based on the user's role
    public List<String> getPermissions() {
        return role.getPermission();
    }
}
