package repository;

import dao.Employee;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import common.AppConstant;

public class StatisticsRepository {
    private final String filePath = AppConstant.DATA_PREFIX+"statistics"+AppConstant.DATA_SUFFIX;

    public StatisticsRepository() {
    }

    // Method to save statistics for an employee
    public void saveStatistics(Employee employee) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(employee.getEmployeeID() + "," + employee.getWorkHours() + "," + employee.getProductivity());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to statistics file: " + e.getMessage());
        }
    }

    // Method to load all employee statistics
    public List<String> loadAllStatistics() {
        List<String> statistics = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                statistics.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading from statistics file: " + e.getMessage());
        }
        return statistics;
    }

    // Method to load statistics for a specific employee by ID
    public String loadStatisticsByEmployeeID(int employeeID) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[0]) == employeeID) {
                    return line; // Return the statistics line for the specified employee
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from statistics file: " + e.getMessage());
        }
        return null; // Return null if no statistics found for the employee
    }
}
