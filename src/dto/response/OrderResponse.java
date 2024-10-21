package dto.response;

import java.time.LocalTime;
import java.util.Map;

public class OrderResponse {
    private int id;
    private Map<String,Integer> dishes;
    private LocalTime time;

    public OrderResponse() {
    }
    
    public OrderResponse(int id, Map<String,Integer> dishes, LocalTime time) {
        this.id = id;
        this.dishes = dishes;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<String, Integer> getDishes() {
        return dishes;
    }

    public void setDishes(Map<String, Integer> dishes) {
        this.dishes = dishes;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}