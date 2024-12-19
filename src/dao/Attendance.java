package dao;

import java.time.LocalTime;

public class Attendance {
    private String userName;
    private LocalTime checkinTime;
    private LocalTime checkoutTime;

    public Attendance() {
    }

    public Attendance(String userName, LocalTime checkinTime, LocalTime checkoutTime) {
        this.userName = userName;
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
