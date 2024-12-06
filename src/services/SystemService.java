package services;

import dao.Attendance;
import dto.request.AttendanceRequest;
import dto.request.AuthRequest;
import dto.request.OrderRequest;
import dto.response.CreateOrderResponse;
import dto.response.ResponseWrapper;
import common.AppConstant;
import common.Result;
import dao.User;
import mapper.AttendanceMapper;
import mapper.OrderMapper;
import repository.AttendanceRepository;
import repository.DishRepository;
import repository.OrderRepository;
import repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

public class SystemService {
    private final AttendanceRepository attendanceRepository;
    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;
    private final UserRepository userRepository; // Thêm UserRepository

    public SystemService(AttendanceRepository attendanceRepository, OrderRepository orderRepository, DishRepository dishRepository, UserRepository userRepository) {
        this.attendanceRepository = attendanceRepository;
        this.orderRepository = orderRepository;
        this.dishRepository = dishRepository;
        this.userRepository = userRepository; // Khởi tạo UserRepository
    }

    // Phương thức kiểm tra xác thực người dùng
    public ResponseWrapper checkAuthentication(AuthRequest authRequest) {
        Map<Object, Object> resultExecute = new HashMap<>();
        // Tìm người dùng từ UserRepository
        User user = userRepository.findObjectByKey(authRequest.getUserName());

        if (user != null && user.getPassword().equals(authRequest.getPassword())) {
            // Nếu tìm thấy người dùng và mật khẩu khớp
            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Login successful");
            resultExecute.put(AppConstant.RESPONSE_KEY.DATA, user);  // Trả về đối tượng User
        } else {
            // Nếu không tìm thấy hoặc mật khẩu không khớp
            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Invalid username or password");
        }

        return new ResponseWrapper(resultExecute);
    }

    // Các phương thức khác như checkAttendance, createOrder, submitOrder không thay đổi
    public ResponseWrapper checkAttendance(AttendanceRequest request) {
        Map<Object, Object> resultExecute = new HashMap<>();
        AttendanceMapper mapper = new AttendanceMapper();
        Attendance attendance = attendanceRepository.findObjectByKey(request.getID());

        if (attendance != null) {
            if (attendance.getCheckinTime() != null && attendance.getCheckoutTime() == null) {
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

    public ResponseWrapper createOrder() {
        Map<Object, Object> resultExecute = new HashMap<>();
        OrderRequest orderRequest = new OrderRequest();
        CreateOrderResponse response = new CreateOrderResponse(dishRepository.getDataList(), orderRequest);
        resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
        resultExecute.put(AppConstant.RESPONSE_KEY.DATA, response);
        resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Get dishes success");
        return new ResponseWrapper(resultExecute);
    }

    public ResponseWrapper submitOrder(OrderRequest request) {
        Map<Object, Object> resultExecute = new HashMap<>();

        if (orderRepository.findObjectByKey(request.getID()) != null) {
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
