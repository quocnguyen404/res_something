package mapper;

import dao.Attendance;
import dto.request.AttendanceRequest;

public class AttendanceMapper {
    public Attendance toAttendance(AttendanceRequest attendanceRequest) {
        Attendance attendance = new Attendance();
        attendance.setId(attendanceRequest.getEmployeeID());
        attendance.setTime(attendanceRequest.getTime());
        return attendance;
    }
}
