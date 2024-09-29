package repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter; 
import java.io.FileWriter; 
import java.io.PrintWriter;
import java.util.Map;
import java.util.HashMap;

import dao.Employee;
import dao.Employee.Role;


public class EmployeeRepository implements Repository<Integer, Employee>
{
    private static String staffPath = "\\data\\staffs.dat";
    private Map<Integer, Employee> relations = new HashMap<>();
    
    public EmployeeRepository()
    {
        loadData();
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
                Employee s = new Employee(info[1], Role.Staff,Integer.parseInt(info[2]));
                relations.put(s.getID(), s);
            }

            br.close();
        }
        catch (Exception e) 
        {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    public void saveData()
    {
        try
        {
            FileWriter fw = new FileWriter(System.getProperty("user.dir")+staffPath);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pr = new PrintWriter(bw);
            for (Employee employee : relations.values())
                pr.println(employee.toData());
            pr.close();
        }
        catch (Exception e)
        {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    @Override
    public Employee get(Integer key) 
    {
        return relations.get(key);
    }

    @Override
    public void add(Integer key, Employee value) 
    {
        if(relations.containsKey(key))
        {
            System.out.println("Already exist staff: " + key);
            return;
        }

        relations.put(key, value);
    }

    @Override
    public void delete(Integer key) 
    {
        if(!relations.containsKey(key))
        {
            System.out.println("Not exist staff: " + key + " to delete");
            return;
        }

        relations.remove(key);
    }
}
