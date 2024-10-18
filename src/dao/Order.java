package dao;

import java.util.HashMap;
import java.util.Map;

import java.time.LocalTime;

public class Order {
    private int id;
    private Map<String,Integer> dishes;
    private LocalTime time;

    public Order() {
        id = hashCode();
        dishes = new HashMap<>();
    }

    public Order(int id, Map<String,Integer> dishes, LocalTime time) {
        this.id = id;
        this.time = time;
        this.dishes = dishes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<String,Integer> getDishes() {
        return dishes;
    }

    public void setDishes(Map<String,Integer> dishes) {
        this.dishes = dishes;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
