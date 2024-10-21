package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.AppConstant;
import dao.Order;

public class OrderRepository {
    private static final int OLFD = 3;
    private static final String ORDER_FOLDER = AppConstant.DATA_PREFIX+"orders\\";
    private Map<Integer,Order> orders;

    public OrderRepository() {
        loadAllOrderFromFile();
    }

    public List<Order> getOrderList() {
        return new ArrayList<Order>(orders.values());
    }

    private void loadAllOrderFromFile() {
        String file = ORDER_FOLDER+LocalDate.now()+AppConstant.DATA_SUFFIX;
        Path path = Paths.get(file);
        orders = new HashMap<>();
        try {
            if(Files.exists(path)) {
                try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while((line = reader.readLine()) != null) {
                        String[] tokens = line.split(",");
                        if(tokens.length != OLFD)
                            throw new Exception("Order format wrong");
                        Order order = dataToOrder(tokens);
                        orders.put(order.getId(), order);
                    }
                }
            } else {
                Files.createFile(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveOrder(Order order) {
        String fileDir = ORDER_FOLDER+LocalDate.now()+AppConstant.DATA_SUFFIX;
        File file = new File(fileDir);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            orders.put(order.getId(), order);
            writer.write(orderToData(order));
            writer.newLine();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public Order findOrderByID(int id) {
        if(orders.containsKey(id)) {
            return orders.get(id);
        }
        return null;
    }

    private Order dataToOrder(String tokens[]) {
        return new Order(Integer.parseInt(tokens[0]), stringToMap(tokens[1]), LocalTime.parse(tokens[2]));
    }

    //0id,1dishes,2time
    private String orderToData(Order order) {
        return String.format("%s,%s,%s", order.getId(), mapToString(order.getDishes()), order.getTime());
    }

    private String mapToString(Map<String, Integer> map) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            result.append(entry.getKey()).append("=").append(entry.getValue()).append(".");
        }
        if (result.length() > 0) {
            result.setLength(result.length() - 1); // Remove the last "."
        }
        return result.toString();
    }

    public Map<String, Integer> stringToMap(String input) {
        Map<String, Integer> resultMap = new HashMap<>();
        String[] pairs = input.split(".");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            if (keyValue.length == 2) {
                resultMap.put(keyValue[0], Integer.parseInt(keyValue[1]));
            }
        }
        return resultMap;
    }
}
