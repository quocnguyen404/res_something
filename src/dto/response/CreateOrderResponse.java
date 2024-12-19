package dto.response;

import java.util.Map;

public class CreateOrderResponse {
    private Map<String, Integer> dishes;
    private double price;
    private int orderID;

    public CreateOrderResponse() {
    }

    public CreateOrderResponse(Map<String, Integer> dishes, double price, int orderID) {
        this.dishes = dishes;
        this.price = price;
        this.orderID = orderID;
    }

    public Map<String, Integer> getDishes() {
        return dishes;
    }

    public void setDishes(Map<String, Integer> dishes) {
        this.dishes = dishes;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getOrderID() {
        return orderID;
    }
    
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
}
