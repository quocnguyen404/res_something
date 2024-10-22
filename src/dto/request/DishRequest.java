package dto.request;

public class DishRequest {
    private String dishName;
    private double price;
    
    public DishRequest() {
    }

    public DishRequest(String dishName, double price) {
        this.dishName = dishName;
        this.price = price;
    }

    public String getDishName() {
        return dishName;
    }

    public void setName(String dishName) {
        this.dishName = dishName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
