package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import common.AppConstant;

public class DishReposity implements Repository<String, Float>
{
    private static final String DISH_DATA = "dishes";
    private Map<String, Float> relations = new HashMap<>();

    public DishReposity()
    {
        loadData();
    }

    public void loadData()
    {
        try
        {
            FileReader fr = new FileReader(AppConstant.DATA_PREFIX+DISH_DATA+AppConstant.DATA_SUFFIX);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while((line = br.readLine()) != null)
            {
                String[] info = new String[2];
                info = line.split(":");
                relations.put(info[0], Float.parseFloat(info[1]));
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
            FileWriter fw = new FileWriter(AppConstant.DATA_PREFIX+DISH_DATA+AppConstant.DATA_SUFFIX);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pr = new PrintWriter(bw);
            pr.close();
        }
        catch (Exception e)
        {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    @Override
    public Float get(String key)
    {
        return relations.get(key);
    }

    @Override
    public void add(String key, Float value) 
    {
        if(relations.containsKey(key))
        {
            System.out.println("Already exist dish: " + key);
            return;
        }
        relations.put(key, value);
    }

    @Override
    public void delete(String key) 
    {
        if(!relations.containsKey(key))
        {
            System.out.println("Not exist dish to delete");
            return;
        }

        relations.remove(key);
    }
}
