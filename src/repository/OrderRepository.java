package repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import common.AppConstant;
import dao.Order;
import utilities.AppUtilities;

public class OrderRepository extends DynamicRepository<Integer,Order> {

    public OrderRepository() {
        DLF = 4;
        DATA_FOLDER = AppConstant.DATA_PREFIX+"order\\";
        updateRepository(LocalDate.now());
    }

    @Override
    protected boolean comparePrimaryKey(String token, Order obj) {
        return Integer.parseInt(token) == obj.getID();
    }

    @Override
    protected void removeObjectFromMap(Order obj) {
        if(dataMap.containsKey(obj.getID())) {
            dataMap.remove(obj.getID());
        }
    }

    @Override
    protected void putObjectToMap(Order obj) {
        dataMap.put(obj.getID(), obj);
    }

    @Override
    protected Order dataToObject(String[] tokens) {
        return new Order(Integer.parseInt(tokens[0]), stringToMap(tokens[1]), Double.parseDouble(tokens[2]), AppUtilities.localTimeParse(tokens[3]));
    }

    @Override
    protected String objectToData(Order obj) {
        return String.format("%s,%s,%s,%s", obj.getID(), mapToString(obj.getDishes()), obj.getPrice(), obj.getTime());
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

    private Map<String, Integer> stringToMap(String input) {
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
