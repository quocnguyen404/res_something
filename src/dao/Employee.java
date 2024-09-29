package dao;

public class Employee extends User
{
    private String name;
    private int id;    

    public Employee(String name, Role role)
    {
        this.name = name;
        this.role = role;
        id = hashCode();        
    }

    //only visible in package
    public Employee(String name, Role role, int id)
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



    public int getID()
    {
        return id;
    }

    protected void setRole(Role role)
    {
        this.role = role;
    }
}
