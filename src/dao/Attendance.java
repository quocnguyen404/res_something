package dao;

import java.time.LocalTime;

public class Attendance {
    private int id;
    private LocalTime time;

    public Attendance() {

    }

    public Attendance(int id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
