package dao;

import java.util.List;
import java.util.ArrayList;

public class Orders
{
    public enum OrderType { DineIn, TakeOut }
    
    private OrderType type;
    private List<Order> orders;
    private float totalPrice;

    private Orders(OrderType type)
    {
        this.type = type;
        orders = new ArrayList<Order>();
    }

    public void addOrder(Order order)
    {
        orders.add(order);
        //TODO
        //totalPrice += Dishes.Instance().getPrice(order.dishName) * order.quantity;
    }

    public void removeOrder(Order order)
    {
        orders.remove(order);
        //TODO
        //totalPrice -= Dishes.Instance().getPrice(order.dishName) * order.quantity;
    }

    //getter setter
    public float totalPrice()
    {
        return totalPrice;
    }

    public OrderType getType()
    {
        return type;
    }
}
