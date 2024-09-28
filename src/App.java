import product.Dishes;

public class App 
{
    public static void main(String[] args) throws Exception 
    {
        for (String dishName : Dishes.Instance().getDishNames()) 
        {
            System.out.println(dishName);    
        }
    }
}
