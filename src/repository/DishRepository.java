package repository;

import dao.Dish;
import dao.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.AppConstant;

public class DishRepository {
    private static final int DLFD = 3;
    private static final String DISH_DATA_PATH = AppConstant.DATA_PREFIX+"dishes"+AppConstant.DATA_SUFFIX;
    private static final String DISH_TMP_DATA_PATH = AppConstant.DATA_PREFIX+"dishes"+AppConstant.DATA_TEMP_SUFFIX;
    private Map<Integer, Dish> dishes;

    public DishRepository() {
        loadAllDishFromFile();
    }

    public List<Dish> getDishList() {
        return new ArrayList<Dish>(dishes.values());
    }

    private void loadAllDishFromFile() {
        dishes = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DISH_DATA_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if(tokens.length != DLFD)
                    throw new Exception("User data format wrong");    
                Dish dish = dataToDish(tokens)
                dishes.put(dish.getId(), dish);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveDish(Dish dish) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DISH_DATA_PATH, true))) {
            dishes.put(dish.getId(), dish);
            writer.write(dishToData(dish));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Dish findDishByDishID(int id) {
        if(dishes.containsKey(id)) {
            return dishes.get(id);
        }
        return null;
    }

    public void updateDish(Dish updateDish) {
        File tempFile = new File(DISH_TMP_DATA_PATH);
        try (BufferedReader reader = new BufferedReader(new FileReader(DISH_DATA_PATH));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");

                if (Integer.parseInt(tokens[0]) == updateDish.getId()) {
                    writer.write(dishToData(updateDish));
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }

            if (!new File(DISH_DATA_PATH).delete()) {
                throw new IOException("Could not delete original file");
            }
            if (!tempFile.renameTo(new File(DISH_DATA_PATH))) {
                throw new IOException("Could not rename temp file");
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteDish(int dishID) {
        File tempFile = new File(DISH_TMP_DATA_PATH);
        try (BufferedReader reader = new BufferedReader(new FileReader(DISH_DATA_PATH));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (Integer.parseInt(tokens[0]) != dishID) {
                    writer.write(line);
                    writer.newLine();
                }
            }
            if (!new File(DISH_DATA_PATH).delete()) {
                throw new IOException("Could not delete original file");
            }
            if (!tempFile.renameTo(new File(DISH_DATA_PATH))) {
                throw new IOException("Could not rename temp file");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Dish dataToDish(String[] tokens) {
        return new Dish(Integer.parseInt(tokens[0]), tokens[1], Double.parseDouble(tokens[2]));
    }

    private String dishToData(Dish dish) {
        return String.format("%s,%s,%s", dish.getId(), dish.getName(), dish.getPrice());
    }
}
