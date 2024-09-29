package employee;

public class Manager extends Employee
{
    public Manager(String name)
    {
        super(name);
        setRole(Role.Manager);
    }

    Manager(String name, int id)
    {
        super(name, id);
        setRole(Role.Manager);
    }
}
