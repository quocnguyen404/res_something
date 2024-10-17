package services;

import dto.request.AttendanceRequest;
import repository.AttendanceRepository;

import java.util.HashMap;
import java.util.Map;

public class SystemService {
    private final AttendanceRepository attendanceRepository;

    public SystemService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public Map<Object, Object> checkAttendance(AttendanceRequest request) {
        Map<Object, Object> resultExecute = new HashMap<>();


        return resultExecute;
    }
}
