package dao;

public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private WorkShift workShift;
    private double salary;
    private double workHours;
    private double productivity;

    public Employee() {
    }

    public Employee(int id, String firstName, String lastName, WorkShift workShift, double salary, double workHours, double productivity) {
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

    public void setID(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
