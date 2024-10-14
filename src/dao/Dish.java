package dao;

public class Dish {
    private int id;
    private String name;
    private double price;
    private String category; // Example: "Appetizer", "Main Course", "Dessert"

    public Dish(int id, String name, double price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Dish [id=" + id + ", name=" + name + ", price=" + price + ", category=" + category + "]";
    }
}
