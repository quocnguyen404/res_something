package dao;

public class Employee extends User {
    private int id;
    private WorkShift workShift; // Field to represent the work shift of the employee
    private double salary;
    private double workHours;
    private double productivity;

    // Constructor to initialize the Employee object
    public Employee() {
        id = hashCode();
    }

    public Employee(int id, WorkShift workShift, double salary, double workHours, double productivity) {
        this.id = id;
        this.workShift = workShift;
        this.salary = salary;
        this.workHours = workHours;
        this.productivity = productivity;
    }

    // Getter and setter
    public int getID() {
        return id;
    }

    public void setEmployeeID(int id) {
        this.id = id;
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

    public void setWorkHours(double workHours) {
        this.workHours = workHours;
    }

    public double getProductivity() {
        return productivity;
    }

    public void setProductivity(double productivity) {
        this.productivity = productivity;
    }
}
