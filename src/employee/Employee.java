package employee;

public abstract class Employee 
{
    public enum Role { Staff, Manager }

    private Role role;
    private String name;
    private int id;    

    public Employee(String name)
    {
        this.name = name;
        id = hashCode();        
    }

    //visible in package
    Employee(String name, int id)
    {
        this.name = name;
        this.id = id;
    }

    public String toData()
    {
        return String.format("%s %s %s", role, name, id);
    }

    @Override
    public String toString() 
    {
        return String.format("Employee: [Role: %s] [Name: %s] [ID: %d]", role, name, id);
    }
    
    //getter setter
    public String getName() 
    {
        return name;
    }

    public Role getRole()
    {
        return role;
    }

    public int getID()
    {
        return id;
    }

    protected void setRole(Role role)
    {
        this.role = role;
    }
}
