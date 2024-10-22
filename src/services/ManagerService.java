package services;

import repository.DishRepository;
import repository.UserRepository;
import utilities.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

import common.AppConstant;
import common.Result;
import dao.Dish;
import dao.User;
import dto.request.DishRequest;
import dto.request.UserRequest;
import mapper.DishMapper;
import mapper.UserMapper;

public class ManagerService {
    private final UserRepository userRepository;
    private final DishRepository dishRepository;
    private final PasswordEncoder passwordEncoder;

    public ManagerService(UserRepository userRepository, DishRepository dishRepository) {
        this.userRepository = userRepository;
        this.dishRepository = dishRepository;
        passwordEncoder = new PasswordEncoder();
    }

    //User management
    public Map<Object, Object> createUser(UserRequest request) {
        Map<Object, Object> resultExecute = new HashMap<>();
        
        if(userRepository.findObjectByKey(request.getUserName()) != null) {
            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Adready exist user");
            return resultExecute;
        }
        String encodePassword = passwordEncoder.encode(request.getPassword());
        UserMapper mapper = new UserMapper();

        User user = mapper.toUser(request);
        user.setEncodePassword(encodePassword);
        userRepository.saveObject(user);
        
        resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
        resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Create user success");
        resultExecute.put(AppConstant.RESPONSE_KEY.DATA, mapper.toResponse(user));
        return resultExecute;
    }

    public Map<Object, Object> updateUser(UserRequest request) {
        Map<Object, Object> resultExecute = new HashMap<>();

        if(userRepository.findObjectByKey(request.getUserName()) == null) {
            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Not exist user");
            return resultExecute;
        }

        UserMapper mapper = new UserMapper();
        User user = mapper.toUser(request);
        userRepository.updateObject(user);
        resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
        resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Update user success");
        resultExecute.put(AppConstant.RESPONSE_KEY.DATA, mapper.toResponse(user));
        return resultExecute;
    }

    public Map<Object, Object> deleteUser(UserRequest request) {
        Map<Object, Object> resultExecute = new HashMap<>();

        if(userRepository.findObjectByKey(request.getUserName()) == null) {
            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Not exist user");
            return resultExecute;
        }

        UserMapper mapper = new UserMapper();
        User user = mapper.toUser(request);
        userRepository.deleteObject(user);
        resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
        resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Delete user success");
        resultExecute.put(AppConstant.RESPONSE_KEY.DATA, mapper.toResponse(user));
        return resultExecute;
    }

    //Dish management
    public Map<Object, Object> addDish(DishRequest request) {
        Map<Object, Object> resultExecute = new HashMap<>();

        if(dishRepository.findObjectByKey(request.getDishName()) != null) {
            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Already exist dish");
            return resultExecute;
        }

        DishMapper mapper = new DishMapper();
        Dish dish = mapper.toDish(request);
        dishRepository.saveObject(dish);

        resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
        resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Add dish success");
        resultExecute.put(AppConstant.RESPONSE_KEY.DATA, mapper.toResponse(dish));
        return resultExecute;
    }

    public Map<Object, Object> updateDish(DishRequest request) {
        Map<Object, Object> resultExecute = new HashMap<>();
        
        if(dishRepository.findObjectByKey(request.getDishName()) == null) {
            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Not exist dish");
            return resultExecute;
        }

        DishMapper mapper = new DishMapper();
        Dish dish = mapper.toDish(request);
        dishRepository.updateObject(dish);

        resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
        resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Update dish success");
        resultExecute.put(AppConstant.RESPONSE_KEY.DATA, mapper.toResponse(dish));

        return resultExecute;
    }

    public Map<Object,Object> deleteDish(DishRequest request) {
        Map<Object, Object> resultExecute = new HashMap<>();

        if(dishRepository.findObjectByKey(request.getDishName()) == null) {
            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Not exist dish");
            return resultExecute;
        }

        DishMapper mapper = new DishMapper();
        Dish dish = mapper.toDish(request);
        dishRepository.deleteObject(dish);

        resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
        resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Delete dish success");
        resultExecute.put(AppConstant.RESPONSE_KEY.DATA, mapper.toResponse(dish));

        return resultExecute;
    }
}
