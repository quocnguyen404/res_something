package dto.response;

import java.time.LocalTime;

public class AttendanceResponse {
    private String userName;
    private LocalTime checkinTime;
    private LocalTime checkoutTime;
    
    public AttendanceResponse() {
    }
    
    public AttendanceResponse(String userName, LocalTime checkinTime, LocalTime checkoutTime) {
        this.checkinTime = checkinTime;
        this.checkoutTime = checkoutTime;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
