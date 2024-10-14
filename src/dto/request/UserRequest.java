package dto.request;


public class UserRequest {
    private String userName;
    private String encodePassword;
    private String password;
    private String firstName;
    private String lastName;
    private dao.Role role;

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
    public dao.Role getRole() {
        return role;
    }
    public void setRole(dao.Role role) {
        this.role = role;
    }
}
