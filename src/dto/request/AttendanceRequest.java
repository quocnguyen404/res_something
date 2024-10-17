package dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceRequest {
    private int employeeID;
    private LocalDate date;
    private LocalTime time;

    public AttendanceRequest() {
    }

    public AttendanceRequest(int employeeID, LocalDate date, LocalTime time) {
        this.employeeID = employeeID;
        this.date = date;
        this.time = time;
    }

    public int getEmployeeID() {
        return employeeID;
    }
    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public LocalTime getTime() {
        return time;
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }
}
