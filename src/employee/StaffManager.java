package employee;

import java.util.List;
import java.util.ArrayList;

public class StaffManager
{
    private List<Staff> staffs;
    
    public StaffManager()
    {
        //open database if not exist init with no data
        //db exist init with db
        staffs = new ArrayList<Staff>();
    }

    public List<Staff> getEmployee()
    {
        return staffs;
    }

    public Staff findStaff(String name)
    {
        return staffs
               .stream()
               .filter(fEm -> name.equals(fEm.getName()))
               .findFirst()
               .get();
    }

    public Staff findStaff(int id)
    {
        return staffs
                .stream()
                .filter(fEm -> id == fEm.getID())
                .findFirst()
                .get();
 
    }

    public boolean existEmployee(String name)
    {
        return staffs
               .stream()
               .anyMatch(fEm -> name.equals(fEm.getName()));
    }

    public boolean existEmployee(int id)
    {
        return staffs
            .stream()
            .anyMatch(fEm -> id == fEm.getID());
    }

    public void addEmployee(Staff staff)
    {
        if(existEmployee(staff.getID()))
            System.out.printf("Already exist staff ID: %d in system\n", staff.getID());
        else
            staffs.add(staff);
    }

    public void removeEmployee(String name)
    {
        Employee em = findStaff(name);
        if(em != null)
            staffs.remove(em);
        else
            System.out.printf("Not exist staff name [%s] to remove\n", name);
    }

    public void removeEmployee(int id)
    {
        Employee em = findStaff(id);
        if(em != null)
            staffs.remove(em);
        else
            System.out.printf("Not exist staff id [%d] to remove\n", id);
    }

    public void listedEmployee()
    {
        for (Staff staff : staffs) 
            System.out.println(staff);    
    }
}
