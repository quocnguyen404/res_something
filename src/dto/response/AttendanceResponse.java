package dto.response;

import java.time.LocalTime;

public class AttendanceResponse {
    private int employeeID;
    private LocalTime time;
    
    public AttendanceResponse() {
        
    }
    
    public AttendanceResponse(int employeeID, LocalTime time) {
        this.employeeID = employeeID;
        this.time = time;
    }
    
    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
