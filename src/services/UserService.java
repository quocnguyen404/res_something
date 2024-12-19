package services;

import common.AppConstant;
import common.Result;
import dao.Attendance;
import dao.User;
import debug.Debug;
import dto.request.AttendanceRequest;
import dto.request.ChangePasswordRequest;
import dto.request.UserRequest;
import dto.response.ResponseWrapper;
import mapper.AttendanceMapper;
import mapper.UserMapper;
import repository.AttendanceRepository;
import repository.UserRepository;
import utilities.PasswordEncoder;

import java.util.Map;
import java.time.LocalDate;
import java.util.HashMap;

public class UserService {
    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, AttendanceRepository attendanceRepository) {
        this.userRepository = userRepository;
        this.attendanceRepository = attendanceRepository;
        passwordEncoder = new PasswordEncoder();
    }

    public ResponseWrapper checkIn(AttendanceRequest request) {
        attendanceRepository.updateRepository(LocalDate.now());
        Map<Object, Object> resultExecute = new HashMap<>();
        
        AttendanceMapper mapper = new AttendanceMapper();
        Attendance attendance = attendanceRepository.findObjectByKey(request.getUserName());
        if(attendance != null) {
            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
            resultExecute.put(AppConstant.RESPONSE_KEY.DATA, mapper.toResponse(attendance));
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Already checkin");
            return new ResponseWrapper(resultExecute);
        }
        attendance = mapper.toAttendanceCheckIn(request);
        attendanceRepository.saveObject(attendance);

        resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
        resultExecute.put(AppConstant.RESPONSE_KEY.DATA, null);
        resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Success check in");
        return new ResponseWrapper(resultExecute);
    }

    public ResponseWrapper checkOut(AttendanceRequest request) {
        attendanceRepository.updateRepository(LocalDate.now());
        Map<Object, Object> resultExecute = new HashMap<>();

        Attendance attendance = attendanceRepository.findObjectByKey(request.getUserName());
        if(attendance == null) {
            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
            resultExecute.put(AppConstant.RESPONSE_KEY.DATA, null);
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Have check in yet, please login again");
            return new ResponseWrapper(resultExecute);
        }

        AttendanceMapper mapper = new AttendanceMapper();
        attendance.setCheckoutTime(request.getTime());
        attendanceRepository.updateObject(attendance);

        resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
        resultExecute.put(AppConstant.RESPONSE_KEY.DATA, mapper.toResponse(attendance));
        resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Success check out");
        return new ResponseWrapper(resultExecute);
    }

    public ResponseWrapper register(UserRequest request) {
        Map<Object, Object> resultExecute = new HashMap<>();
        try {
            if(userRepository.findObjectByKey(request.getUserName()) != null) {
                resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
                resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Already exist user");
            } else {
                String encodePassword = passwordEncoder.encode(request.getPassword());
                UserMapper mapper = new UserMapper();

                User user = mapper.toUser(request);
                user.setEncodePassword(encodePassword);
                userRepository.saveObject(user);
                
                resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
                resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Success register");
                resultExecute.put(AppConstant.RESPONSE_KEY.DATA, mapper.toResponse(user));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseWrapper(resultExecute);
    }

    public ResponseWrapper changePassword(ChangePasswordRequest request) {
        Map<Object, Object> resultExecute = new HashMap<>();

        User currentUser = userRepository.findObjectByKey(request.getUserName());
        if(currentUser == null) {
            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
            resultExecute.put(AppConstant.RESPONSE_KEY.DATA, null);
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Not exist user");
            return new ResponseWrapper(resultExecute);
        }
        
        String encodePw = passwordEncoder.encode(request.getCurrentPassword());
        if(currentUser.getPassword() == null) {
            Debug.printObject(currentUser);
            return null;
        }
        boolean rightPw = passwordEncoder.matches(encodePw, currentUser.getPassword()); 
        if(!rightPw) {
            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
            resultExecute.put(AppConstant.RESPONSE_KEY.DATA, null);
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Wrong password");
            return new ResponseWrapper(resultExecute);
        }

        boolean rightCofirmPw = request.getNewPassword().equals(request.getConfirmPassword());
        if(!rightCofirmPw) {
            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT,Result.NotOK());
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Wrong confirm password");
            resultExecute.put(AppConstant.RESPONSE_KEY.DATA, null);
            return new ResponseWrapper(resultExecute);
        }

        currentUser.setPassword(request.getNewPassword());
        currentUser.setEncodePassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.updateObject(currentUser);
        
        resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
        resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Change password success");
        resultExecute.put(AppConstant.RESPONSE_KEY.DATA, new UserMapper().toResponse(currentUser));

        return new ResponseWrapper(resultExecute);
    }
}
