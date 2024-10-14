package dao;

public class Employee extends User {
    private int employeeID;
    private WorkShift workShift; // Field to represent the work shift of the employee
    private double salary;
    private double workHours;
    private double productivity;

    // Constructor to initialize the Employee object
    public Employee(String userName, String firstName, String lastName, dao.Role role, int employeeID, WorkShift workShift, double salary) {
        super(userName, firstName, lastName, role); // Call User class constructor
        this.employeeID = employeeID;
        this.workShift = workShift; // Set the work shift
        this.salary = salary;
        this.workHours = 0.0; // Initialize work hours to 0
        this.productivity = 0.0; // Initialize productivity to 0
    }

    // Getters and Setters
    public int getEmployeeID() {
        return employeeID;
    }

    public WorkShift getWorkShift() {
        return workShift;
    }

    public void setWorkShift(WorkShift workShift) {
        this.workShift = workShift;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getWorkHours() {
        return workHours;
    }

    // Add work hours for the employee
    public void addWorkHours(double hours) {
        this.workHours += hours;
    }

    public double getProductivity() {
        return productivity;
    }

    // Set the productivity metric for the employee
    public void setProductivity(double productivity) {
        this.productivity = productivity;
    }

    // Override toString method for better output formatting
    @Override
    public String toString() {
        return "EmployeeID: " + employeeID + 
               ", Name: " + getFullName() + 
               ", Work Shift: " + workShift + 
               ", Salary: $" + salary + 
               ", Work Hours: " + workHours + 
               ", Productivity: " + productivity;
    }
}
