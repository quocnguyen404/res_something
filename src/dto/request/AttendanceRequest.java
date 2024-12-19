package dto.request;

import java.time.LocalTime;

public class AttendanceRequest {
    private String userName;
    private LocalTime time;

    public AttendanceRequest() {
    }

    public AttendanceRequest(String userName, LocalTime time) {
        this.userName = userName;
        this.time = time;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalTime getTime() {
        return time;
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }
}
