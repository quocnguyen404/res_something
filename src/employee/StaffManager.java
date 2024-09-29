package employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter; 
import java.io.FileWriter; 
import java.io.PrintWriter;

import java.util.List;
import java.util.ArrayList;

public class StaffManager
{
    private static String staffPath = "\\data\\staffs.dat";
    private List<Staff> staffs;
    
    public StaffManager()
    {
        staffs = new ArrayList<Staff>();
        //open database if not exist init with no data
        //db exist init with db
        loadData();
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
        {
            staffs.add(staff);
        }
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

    public void saveData()
    {
        try
            {
                FileWriter fw = new FileWriter(System.getProperty("user.dir")+staffPath);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pr = new PrintWriter(bw);
                for (Staff staff : staffs)
                    pr.println(staff.toData());
                pr.close();
            }
            catch (Exception e)
            {
                System.out.println("Exception: " + e.getMessage());
            }
    }

    public void loadData()
    {
        try
        {
            FileReader fr = new FileReader(System.getProperty("user.dir")+staffPath);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while((line = br.readLine()) != null)
            {
                String[] info = new String[3];
                info = line.split(" ");
                Staff s = new Staff(info[1], Integer.parseInt(info[2]));
                staffs.add(s);
            }

            br.close();
        }
        catch (Exception e) 
        {
            System.out.println("Exception: " + e.getMessage());
        }
    }
    
    public void finalize()
    {
        saveData();
    }
}
