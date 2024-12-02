package services;

import dto.request.AttendanceRequest;
import dto.request.OrderRequest;
import dto.response.ResponseWrapper;
import mapper.AttendanceMapper;
import mapper.OrderMapper;
import repository.AttendanceRepository;
import repository.OrderRepository;

import java.util.HashMap;
import java.util.Map;

import common.AppConstant;
import common.Result;
import dao.Attendance;

public class SystemService {
    private final AttendanceRepository attendanceRepository;
    private final OrderRepository orderRepository;

    public SystemService(AttendanceRepository attendanceRepository, OrderRepository orderRepository) {
        this.attendanceRepository = attendanceRepository;
        this.orderRepository = orderRepository;
    }

    public ResponseWrapper checkAttendance(AttendanceRequest request) {
        Map<Object, Object> resultExecute = new HashMap<>();
        AttendanceMapper mapper = new AttendanceMapper();
        Attendance attendance = attendanceRepository.findObjectByKey(request.getID());

        if(attendance != null) {
            if(attendance.getCheckinTime() != null && attendance.getCheckoutTime() == null) {
                attendance.setCheckoutTime(request.getTime());
                attendanceRepository.updateObject(attendance);
                resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
                resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Check out success");
                resultExecute.put(AppConstant.RESPONSE_KEY.DATA, mapper.toResponse(attendance));
            }
            return new ResponseWrapper(resultExecute);
        }

        attendance = mapper.toAttendanceCheckin(request);
        attendanceRepository.saveObject(attendance);
        resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
        resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Check in success");
        resultExecute.put(AppConstant.RESPONSE_KEY.DATA, mapper.toResponse(attendance));
        return new ResponseWrapper(resultExecute);
    }

    public ResponseWrapper createOrder(OrderRequest request) {
        Map<Object, Object> resultExecute = new HashMap<>();

        if(orderRepository.findObjectByKey(request.getID()) != null) {
            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Already exist order id");
            return new ResponseWrapper(resultExecute);
        }

        OrderMapper mapper = new OrderMapper();
        orderRepository.saveObject(mapper.toOrder(request));
        resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
        resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Create order success");

        return new ResponseWrapper(resultExecute);
    }

    public ResponseWrapper doLogout() {
        Map<Object, Object> resultExecute = new HashMap<>();
        //TODO do checkout or something like that
        return new ResponseWrapper(resultExecute);
    }
}
