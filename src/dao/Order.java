package dao;

public class Order 
{
    public String dishName;
    public int quantity;
    
    public Order(String dishName, int quantity)
    {
        this.dishName = dishName;
        this.quantity = quantity;
    }

    @Override
    public String toString()
    {
        return String.format("%s: %d", dishName, quantity);    
    }
}