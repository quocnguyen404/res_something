package customer;

import product.Orders;

public class Customer 
{
    private String name;
    private Orders orders;
    private String phoneNum;

    public Customer(String name, String phoneNum, Orders orders)
    {
        this.name = name;
        this.phoneNum = phoneNum;
        this.orders = orders;
    }
}
