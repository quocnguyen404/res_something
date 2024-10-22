package dto.request;

import java.time.LocalTime;

public class AttendanceRequest {
    private int id;
    private LocalTime time;

    public AttendanceRequest() {
    }

    public AttendanceRequest(int id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public int getID() {
        return id;
    }
    public void setID(int id) {
        this.id = id;
    }
    public LocalTime getTime() {
        return time;
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }
}
