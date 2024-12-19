package dto.request;

import java.time.LocalTime;
import java.util.Map;

public class OrderRequest {
    private static int idCount;
    private int id;
    private Map<String,Integer> dishes;
    private double price;
    private LocalTime time;
    
    public OrderRequest() {
    }

    public OrderRequest(Map<String,Integer> dishes, double price, LocalTime time) {
        this.id = idCount;
        idCount++;
        this.dishes = dishes;
        this.price = price;
        this.time = time;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public Map<String,Integer> getDishes() {
        return dishes;
    }

    public void setDishes(Map<String,Integer> dishes) {
        this.dishes = dishes;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
