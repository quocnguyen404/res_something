package mapper;

import dao.Attendance;
import dto.request.AttendanceRequest;
import dto.response.AttendanceResponse;

public class AttendanceMapper {

    public Attendance toAttendanceCheckIn(AttendanceRequest request) {
        Attendance attendance = new Attendance(request.getUserName(), request.getTime(), null);
        return attendance;
    }

    public Attendance toAttendanceCheckOut(AttendanceRequest request) {
        return new Attendance(request.getUserName(), null, request.getTime());
    }

    public AttendanceResponse toResponse(Attendance attendance) {
        AttendanceResponse response = new AttendanceResponse();
        response.setUserName(attendance.getUserName());
        response.setCheckinTime(attendance.getCheckinTime());
        response.setCheckoutTime(attendance.getCheckoutTime());
        return response;
    }
}
