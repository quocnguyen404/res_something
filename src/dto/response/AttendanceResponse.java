package dto.response;

import java.time.LocalTime;

public class AttendanceResponse {
    private int id;
    private LocalTime checkinTime;
    private LocalTime checkoutTime;
    
    public AttendanceResponse() {
    }
    
    public AttendanceResponse(int id, LocalTime checkinTime, LocalTime checkoutTime) {
        this.id = id;
        this.checkinTime = checkinTime;
        this.checkoutTime = checkoutTime;
    }
    
    public int getID() {
        return id;
    }
    public void setID(int id) {
        this.id = id;
    }
    public LocalTime getCheckinTime() {
        return checkinTime;
    }
    public void setCheckinTime(LocalTime checkinTime) {
        this.checkinTime = checkinTime;
    }
    public LocalTime getCheckoutTime() {
        return checkoutTime;
    }
    public void setCheckoutTime(LocalTime checkoutTime) {
        this.checkoutTime = checkoutTime;
    }
}
