package repository;

import dao.Employee;
import dao.Role;
import dao.User;
import dao.WorkShift;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.AppConstant;

public class EmployeeRepository {
    private static final int ELFD = 6;
    private static final String EM_DATA_PATH = AppConstant.DATA_PREFIX+"staffs"+AppConstant.DATA_SUFFIX;
    private static final String EM_TMP_DATA_PATH = AppConstant.DATA_PREFIX+"staffs"+AppConstant.DATA_TEMP_SUFFIX;
    private Map<String, Employee> employees;

    public EmployeeRepository() {
        loadAllEmployeeFromFile();
    }

    public List<Employee> getEmployeeList() {
        return new ArrayList<Employee>(employees.values());
    }

    private void loadAllEmployeeFromFile() {
        employees = new HashMap<String, Employee>();
        try (BufferedReader reader = new BufferedReader(new FileReader(EM_DATA_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if(tokens.length != ELFD)
                    throw new Exception("Employee data format wrong");
                Employee em = dataToEmployee(tokens);
                employees.put(em.getUserName(), em);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveEmployee(Employee em) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EM_DATA_PATH, true))) {
            employees.put(em.getUserName(), em);
            writer.write(employeeToData(em));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Employee findEmployeeByUserName(String userName) {
        if(employees.containsKey(userName)) {
            return employees.get(userName);
        }
        return null;
    }

    public void updateEmployee(Employee updateEm) {
        File tempFile = new File(EM_TMP_DATA_PATH);
        try (BufferedReader reader = new BufferedReader(new FileReader(EM_DATA_PATH));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens[0].equals(updateEm.getUserName())) {
                    writer.write(employeeToData(updateEm));
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }

            if (!new File(EM_DATA_PATH).delete()) {
                throw new IOException("Could not delete original file");
            }
            if (!tempFile.renameTo(new File(EM_DATA_PATH))) {
                throw new IOException("Could not rename temp file");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String emName) {
        File tempFile = new File(EM_TMP_DATA_PATH);
        try (BufferedReader reader = new BufferedReader(new FileReader(EM_DATA_PATH));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (!tokens[0].equals(emName)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
            if (!new File(EM_DATA_PATH).delete()) {
                throw new IOException("Could not delete original file");
            }
            if (!tempFile.renameTo(new File(EM_DATA_PATH))) {
                throw new IOException("Could not rename temp file");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //employee data: 0userName, 1id, 2shift, 3salary, 4workHours, 5productivity
    private Employee dataToEmployee(String[] tokens) {
        Employee em = new Employee(Integer.parseInt(tokens[1]), WorkShift.valueOf(tokens[2]),
                                   Double.parseDouble(tokens[3]), Double.parseDouble(tokens[4]),
                                   Double.parseDouble(tokens[5]));
        em.setUserName(tokens[0]);
        return em;
    }

    private String employeeToData(Employee em) {
        return String.format("%s, %s, %s, %s, %s, %s",
                             em.getUserName(), em.getID(),
                             em.getWorkShift(), em.getSalary(),
                             em.getWorkHours(), em.getProductivity());
    }
}
