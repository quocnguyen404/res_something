package repository;

import dao.Employee;
import dao.Role;
import dao.WorkShift;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import common.AppConstant;

public class EmployeeRepository {
    private final String employeeFile = AppConstant.DATA_PREFIX+"staffs"+AppConstant.DATA_SUFFIX;
    private Map<Integer, Employee> employeeMap;

    public EmployeeRepository() {
        this.employeeMap = loadEmployees();
    }

    // Add a new employee
    public void addEmployee(Employee employee) {
        employeeMap.put(employee.getEmployeeID(), employee);
        saveEmployees();
    }

    // Update an existing employee
    public void updateEmployee(Employee employee) {
        if (employeeMap.containsKey(employee.getEmployeeID())) {
            employeeMap.put(employee.getEmployeeID(), employee);
            saveEmployees();
        } else {
            System.out.println("Employee not found.");
        }
    }

    // Get employee by ID
    public Employee getEmployee(int employeeID) {
        return employeeMap.get(employeeID);
    }

    // Save all employees to the file
    private void saveEmployees() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(employeeFile))) {
            for (Employee employee : employeeMap.values()) {
                writer.write(employee.getEmployeeID() + "," +
                             employee.getUserName() + "," +
                             employee.getFirstName() + "," +
                             employee.getLastName() + "," +
                             employee.getRole() + "," +
                             employee.getWorkShift() + "," +
                             employee.getSalary() + "," +
                             employee.getWorkHours() + "," +
                             employee.getProductivity());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load employees from the file
    private Map<Integer, Employee> loadEmployees() {
        Map<Integer, Employee> employeeMap = new HashMap<>();
        File file = new File(employeeFile);

        if (!file.exists()) {
            return employeeMap; // Return empty map if file does not exist
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 9) { // Expecting 9 fields
                    int employeeID = Integer.parseInt(parts[0]);
                    String userName = parts[1];
                    String firstName = parts[2];
                    String lastName = parts[3];
                    Role role = Role.valueOf(parts[4]); // Assuming Role enum exists
                    WorkShift workShift = WorkShift.valueOf(parts[5]); // Parse work shift
                    double salary = Double.parseDouble(parts[6]);
                    double workHours = Double.parseDouble(parts[7]);
                    double productivity = Double.parseDouble(parts[8]);

                    Employee employee = new Employee(userName, firstName, lastName, role, employeeID, workShift, salary);
                    employee.addWorkHours(workHours); // Set initial work hours
                    employee.setProductivity(productivity); // Set initial productivity
                    employeeMap.put(employeeID, employee);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return employeeMap;
    }
}
