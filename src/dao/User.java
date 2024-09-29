package dao;

public class User 
{
    public enum Role { Staff, Manager }

    private Role role;
    private String userName;
    private String encodePassword;

    public Role getRole()
    {
        return role;
    }
}
