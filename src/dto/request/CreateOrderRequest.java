package dto.request;

import java.util.HashMap;
import java.util.Map;

public class CreateOrderRequest {
    private Map<String, Integer> dishes;
    
    public CreateOrderRequest() {
        dishes = new HashMap<>();
    }

    public Map<String, Integer> getDishes() {
        return dishes;
    }

    public void setDishes(Map<String, Integer> dishes) {
        this.dishes = dishes;
    }

    public void addDish(String dishName, int amount) {
        if(dishes.containsKey(dishName)) {
            int oldAmount = dishes.get(dishName);
            dishes.put(dishName, oldAmount + amount);
            return;
        }
        dishes.put(dishName, amount);
    }

    public void removeDish(String dishName, int amount) {
        if(!dishes.containsKey(dishName)) {
            return;
        }
        int newAmount = dishes.get(dishName) - amount;
        if (newAmount <= 0) {
            dishes.remove(dishName);
            return;
        }
        dishes.put(dishName, newAmount);
    }
}
