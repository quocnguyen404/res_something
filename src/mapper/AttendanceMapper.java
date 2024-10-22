package mapper;

import dao.Attendance;
import dto.request.AttendanceRequest;
import dto.response.AttendanceResponse;

public class AttendanceMapper {

    public Attendance toAttendanceCheckin(AttendanceRequest request) {
        return new Attendance(request.getID(), request.getTime(), null);
    }

    public AttendanceResponse toResponse(Attendance attendance) {
        AttendanceResponse response = new AttendanceResponse();
        response.setID(attendance.getID());
        response.setCheckinTime(attendance.getCheckinTime());
        response.setCheckoutTime(attendance.getCheckoutTime());
        return response;
    }
}
