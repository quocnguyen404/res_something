package services;

import dto.request.AttendanceRequest;
import mapper.AttendanceMapper;
import repository.AttendanceRepository;

import java.util.HashMap;
import java.util.Map;

import common.AppConstant;
import common.Result;

public class SystemService {
    private final AttendanceRepository attendanceRepository;

    public SystemService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public Map<Object, Object> checkAttendance(AttendanceRequest request) {
        Map<Object, Object> resultExecute = new HashMap<>();
        
        if(attendanceRepository.findAttendanceByEmployeeID(request.getEmployeeID()) == null) {
            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Already check attendance");
        } else {
            AttendanceMapper mapper = new AttendanceMapper();
            attendanceRepository.saveAttendance(mapper.toAttendance(request));

            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Check attendance success");
        }
        return resultExecute;
    }

    public Map<Object, Object> createOrder(int orderID, Map<String,Integer> dishes) {
        
    }


    public Map<Object, Object> doLogout() {
        Map<Object, Object> resultExecute = new HashMap<>();

        return resultExecute;
    }
}
