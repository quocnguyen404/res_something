import employee.StaffManager;

public class App 
{
    public static void main(String[] args) throws Exception 
    {
        StaffManager sm = new StaffManager();
        sm.listedEmployee();
        sm.finalize();
    }
}