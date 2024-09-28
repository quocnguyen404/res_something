package employee;

public class Staff extends Employee
{
    public Staff(String name)
    {
        super(name);
        setRole(Role.Staff);
    }
}
