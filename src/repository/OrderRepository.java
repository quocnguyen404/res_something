package repository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import common.AppConstant;
import dao.Order;

public class OrderRepository {
    private static final int OLFD = 3;
    private static final String ORDER_FOLDER = AppConstant.DATA_PREFIX+"orders\\";
    
    public Order findOrderByID(int id, LocalTime time) {
        
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
            // writer.write();
            writer.newLine();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    //0id,1dishes,2time
    private String orderToData(Order order) {
        return String.format("%s,%s,%s", order.getId(), mapToString(order.getDishes()), order.getTime());
    }



    private String mapToString(Map<?,?> map) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            result.append(entry.getKey()).append("=").append(entry.getValue()).append(".");
        }
        if (result.length() > 0) {
            result.setLength(result.length() - 1); // Remove the last "."
        }
        return result.toString();
    }
}
