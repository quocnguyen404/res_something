package repository;

import dao.Dish;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import common.AppConstant;

public class DishRepository {
    private List<Dish> dishes;
    private final String filePath = AppConstant.DATA_PREFIX+"dishes"+AppConstant.DATA_SUFFIX; // Path to the text file

    public DishRepository() {
        this.dishes = loadFromFile(); // Load dishes from the text file when the repository is initialized
    }

    // Add a new dish to the repository
    public void addDish(Dish dish) {
        dishes.add(dish);
        saveToFile();
    }

    // Remove a dish by ID
    public void removeDish(int dishId) {
        dishes.removeIf(dish -> dish.getId() == dishId);
        saveToFile();
    }

    // Get a list of all dishes
    public List<Dish> getAllDishes() {
        return new ArrayList<>(dishes);
    }

    // Get a dish by its ID
    public Optional<Dish> getDishById(int id) {
        return dishes.stream().filter(dish -> dish.getId() == id).findFirst();
    }

    // Save dishes to text file
    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Dish dish : dishes) {
                // Write each dish as a line in the format: id,name,price,category
                writer.write(dish.getId() + "," + dish.getName() + "," + dish.getPrice() + "," + dish.getCategory());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load dishes from text file
    private List<Dish> loadFromFile() {
        List<Dish> loadedDishes = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return loadedDishes; // Return an empty list if the file doesn't exist
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into parts (id,name,price,category)
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    String category = parts[3];
                    loadedDishes.add(new Dish(id, name, price, category));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return loadedDishes;
    }
}
