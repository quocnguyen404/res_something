package services;

import repository.AttendanceRepository;
import repository.DishRepository;
import repository.UserRepository;
import utilities.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.AppConstant;
import common.Result;
import dao.Attendance;
import dao.Dish;
import dao.User;
import dto.request.DishRequest;
import dto.request.UserRequest;
import dto.response.AttendanceResponse;
import dto.response.ResponseWrapper;
import mapper.AttendanceMapper;
import mapper.DishMapper;
import mapper.UserMapper;

public class ManagerService {
    private final UserRepository userRepository;
    private final DishRepository dishRepository;
    private final AttendanceRepository attendanceRepository;
    private final PasswordEncoder passwordEncoder;

    public ManagerService(UserRepository userRepository, DishRepository dishRepository, AttendanceRepository attendanceRepository) {
        this.userRepository = userRepository;
        this.dishRepository = dishRepository;
        this.attendanceRepository = attendanceRepository;
        passwordEncoder = new PasswordEncoder();
    }

    //Check attendance
    public ResponseWrapper getAttendances(LocalDate date) {
        Map<Object, Object> resultExecute = new HashMap<>();
        List<Attendance> attendances = attendanceRepository.getAllDataFromFIle(date);
        
        if(attendances == null) {
            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
            resultExecute.put(AppConstant.RESPONSE_KEY.DATA, null);
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Not exist attendace data at " + date.toString());
            return new ResponseWrapper(resultExecute);
        }
        
        AttendanceMapper mapper = new AttendanceMapper();
        List<AttendanceResponse> attendanceResponse = new ArrayList<>(attendances.size());
        for (Attendance attendance : attendances) {
            attendanceResponse.add(mapper.toResponse(attendance));
        }

        resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
        resultExecute.put(AppConstant.RESPONSE_KEY.DATA, attendanceResponse);
        resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Get attendance list success");
        return new ResponseWrapper(resultExecute);
    }

    //User management
    public ResponseWrapper createUser(UserRequest request) {
        Map<Object, Object> resultExecute = new HashMap<>();
        
        if(userRepository.findObjectByKey(request.getUserName()) != null) {
            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Adready exist user");
            return new ResponseWrapper(resultExecute);
        }
        String encodePassword = passwordEncoder.encode(request.getPassword());
        UserMapper mapper = new UserMapper();

        User user = mapper.toUser(request);
        user.setEncodePassword(encodePassword);
        userRepository.saveObject(user);
        
        resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
        resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Create user success");
        resultExecute.put(AppConstant.RESPONSE_KEY.DATA, mapper.toResponse(user));
        return new ResponseWrapper(resultExecute);
    }

    public ResponseWrapper updateUser(UserRequest request) {
        Map<Object, Object> resultExecute = new HashMap<>();
        User user = userRepository.findObjectByKey(request.getUserName());
        if(user == null) {
            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Not exist user");
            resultExecute.put(AppConstant.RESPONSE_KEY.DATA, null);
            return new ResponseWrapper(resultExecute);
        }

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(request.getRole());
        
        userRepository.updateObject(user);

        resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
        resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Update user success");
        resultExecute.put(AppConstant.RESPONSE_KEY.DATA, null);
        return new ResponseWrapper(resultExecute);
    }

    public ResponseWrapper deleteUser(UserRequest request) {
        Map<Object, Object> resultExecute = new HashMap<>();
        User user = userRepository.findObjectByKey(request.getUserName());
        if(user == null) {
            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Not exist user");
            resultExecute.put(AppConstant.RESPONSE_KEY.DATA, null);
            return new ResponseWrapper(resultExecute);
        }
        userRepository.deleteObject(user);
        resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
        resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Delete user success");
        resultExecute.put(AppConstant.RESPONSE_KEY.DATA, null);
        return new ResponseWrapper(resultExecute);
    }
    
    //Dish management
    public ResponseWrapper addDish(DishRequest request) {
        Map<Object, Object> resultExecute = new HashMap<>();

        if(dishRepository.findObjectByKey(request.getDishName()) != null) {
            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Already exist dish");
            return new ResponseWrapper(resultExecute);
        }

        DishMapper mapper = new DishMapper();
        Dish dish = mapper.toDish(request);
        dishRepository.saveObject(dish);

        resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
        resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Add dish success");
        resultExecute.put(AppConstant.RESPONSE_KEY.DATA, mapper.toResponse(dish));
        return new ResponseWrapper(resultExecute);
    }

    public ResponseWrapper updateDish(DishRequest request) {
        Map<Object, Object> resultExecute = new HashMap<>();
        
        if(dishRepository.findObjectByKey(request.getDishName()) == null) {
            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Not exist dish");
            return new ResponseWrapper(resultExecute);
        }

        DishMapper mapper = new DishMapper();
        Dish dish = mapper.toDish(request);
        dishRepository.updateObject(dish);

        resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
        resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Update dish success");
        resultExecute.put(AppConstant.RESPONSE_KEY.DATA, mapper.toResponse(dish));

        return new ResponseWrapper(resultExecute);
    }

    public ResponseWrapper deleteDish(DishRequest request) {
        Map<Object, Object> resultExecute = new HashMap<>();

        Dish dish = dishRepository.findObjectByKey(request.getDishName());
        if(dish == null) {
            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Not exist dish");
            return new ResponseWrapper(resultExecute);
        }

        dishRepository.deleteObject(dish);
        resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
        resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Delete dish:" + dish.getDishName() +" success");
        resultExecute.put(AppConstant.RESPONSE_KEY.DATA, null);
        return new ResponseWrapper(resultExecute);
    }
}
