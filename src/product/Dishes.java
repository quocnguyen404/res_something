package product;

import java.io.BufferedReader; 
import java.io.FileReader; 
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Dishes 
{
    private static String dishPath = "\\data\\dishes.dat";
    private Map<String, Float> dishes;
    
    //Singleton
    private static Dishes instance = new Dishes();
    public static Dishes Instance()
    {
        return instance;
    }

    private Dishes()
    {
        dishes = new HashMap<String, Float>();
        loadData();
    }

    private void loadData()
    {
        try
        {
            FileReader fr = new FileReader(System.getProperty("user.dir")+dishPath);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while((line = br.readLine()) != null)
            {
                String[] info = new String[2];
                info = line.split(":");
                dishes.put(info[0], Float.parseFloat(info[1]));
            }

            br.close();
        } 
        catch (Exception e) 
        {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    public Set<String> getDishNames()
    {
        return dishes.keySet();
    }

    public float getPrice(String name)
    {
        return dishes.get(name);
    }

    /*
    public class Dish 
    {
        private String name;
        private float price;

        public Dish(String name, float price)
        {
            this.name = name;
            this.price = price;
        }

        @Override
        public String toString() 
        {
            return String.format("Name: [%s] Price:[%.2f]", name, price);    
        }

        //getter setter
        public String getName() 
        {
            return name;
        }
        public float getPrice() 
        {
            return price;
        }
    }
    */
}
