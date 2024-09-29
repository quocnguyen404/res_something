package employee;

public class Staff extends Employee
{
    public Staff(String name)
    {
        super(name);
        setRole(Role.Staff);
    }

    Staff(String name, int id)
    {
        super(name, id);
        setRole(Role.Staff);
    }
}
