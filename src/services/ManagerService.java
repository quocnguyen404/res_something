package services;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import repository.AttendanceRepository;
import repository.DishRepository;
import repository.FeedbackRepository;
import repository.UserRepository;
import common.AppConstant;
import common.Result;
import dao.Attendance;
import dao.Dish;
import dao.Feedback;
import dao.User;
import dto.request.DishRequest;
import dto.request.UpdateDishRequest;
import dto.request.UserRequest;
import dto.response.AttendanceResponse;
import dto.response.FeedbackResponse;
import dto.response.ResponseWrapper;
import mapper.AttendanceMapper;
import mapper.DishMapper;
import mapper.FeedbackMapper;

public class ManagerService {
    private final UserRepository userRepository;
    private final DishRepository dishRepository;
    private final AttendanceRepository attendanceRepository;
    private final FeedbackRepository feedbackRepository;

    public ManagerService(UserRepository userRepository, DishRepository dishRepository, AttendanceRepository attendanceRepository, FeedbackRepository feedbackRepository) {
        this.userRepository = userRepository;
        this.dishRepository = dishRepository;
        this.attendanceRepository = attendanceRepository;
        this.feedbackRepository = feedbackRepository;
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

    //Get feedbacks
    public ResponseWrapper getFeedbacks(LocalDate date) {
        Map<Object, Object> resultExecute = new HashMap<>();
        List<Feedback> feedbacks = feedbackRepository.getAllDataFromFIle(date);

        if(feedbacks == null || feedbacks.isEmpty()) {
            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
            resultExecute.put(AppConstant.RESPONSE_KEY.DATA, null);
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Have no feedback at " + date.toString());
            return new ResponseWrapper(resultExecute);
        }

        FeedbackMapper mapper = new FeedbackMapper();
        List<FeedbackResponse> response = new ArrayList<>(feedbacks.size());
        for (Feedback feedback : feedbacks) {
            response.add(mapper.toResponse(feedback));
        }

        resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
        resultExecute.put(AppConstant.RESPONSE_KEY.DATA, response);
        resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Get feedbacks success");
        return new ResponseWrapper(resultExecute);
    }

    //User management
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

    public ResponseWrapper updateDish(UpdateDishRequest request) {
        Map<Object, Object> resultExecute = new HashMap<>();
        
        Dish oldDish = dishRepository.findObjectByKey(request.getDishName());
        if(oldDish == null) {
            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Not exist dish");
            resultExecute.put(AppConstant.RESPONSE_KEY.DATA, null);
            return new ResponseWrapper(resultExecute);
        }

        DishMapper mapper = new DishMapper();
        Dish newDish = mapper.toDish(request);
        if(!request.getNewDishName().equals(oldDish.getDishName())) {
            dishRepository.deleteObject(oldDish);
            dishRepository.saveObject(newDish);
        } else {
            dishRepository.updateObject(newDish);
        }

        resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
        resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Update dish success");
        resultExecute.put(AppConstant.RESPONSE_KEY.DATA, mapper.toResponse(newDish));

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
