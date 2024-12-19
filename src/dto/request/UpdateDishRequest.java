package dto.request;

public class UpdateDishRequest {
    private String dishName;
    private String newDishName;
    private double price;
    
    public UpdateDishRequest() {
    }

    public UpdateDishRequest(String dishName, String newDishName, double price) {
        this.dishName = dishName;
        this.newDishName = newDishName;
        this.price = price;
    }

    public String getDishName() {
        return dishName;
    }

    public void setName(String dishName) {
        this.dishName = dishName;
    }

    public String getNewDishName() {
        return newDishName;
    }

    public void setNewDishName(String newDishName) {
        this.newDishName = newDishName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
