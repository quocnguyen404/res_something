import java.util.List;

import employee.Staff;
import employee.StaffManager;

public class App 
{
    public static void main(String[] args) throws Exception 
    {
        StaffManager staffManager = new StaffManager();
        List<Staff> staffs = staffManager.getEmployee();
        staffs.add(new Staff("Quoc"));

        staffManager.listedEmployee();
    }
}
