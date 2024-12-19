package services;

import dao.Dish;
import dao.Feedback;
import dto.request.CreateOrderRequest;
import dto.request.FeedbackRequest;
import dto.request.OrderRequest;
import dto.response.CreateOrderResponse;
import dto.response.DishResponse;
import dto.response.ResponseWrapper;
import common.AppConstant;
import common.Result;
import mapper.DishMapper;
import mapper.FeedbackMapper;
import mapper.OrderMapper;
import repository.DishRepository;
import repository.FeedbackRepository;
import repository.OrderRepository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemService {
    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;
    private final FeedbackRepository feedbackRepository;

    public SystemService(OrderRepository orderRepository, DishRepository dishRepository, FeedbackRepository feedbackRepository) {
        this.orderRepository = orderRepository;
        this.dishRepository = dishRepository;
        this.feedbackRepository = feedbackRepository;
    }

    public ResponseWrapper createOrder(CreateOrderRequest request) {
        Map<Object, Object> resultExecute = new HashMap<>();
        
        if(request.getDishes().isEmpty()) {
            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
            resultExecute.put(AppConstant.RESPONSE_KEY.DATA, null);
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Order have no dish");
            return new ResponseWrapper(resultExecute);
        }

        double price = request.getDishes().entrySet().stream()
        .mapToDouble(entry -> dishRepository.findObjectByKey(entry.getKey()).getPrice() * entry.getValue())
        .sum();

        OrderRequest orderRequest = new OrderRequest(request.getDishes(), price, LocalTime.now());
        CreateOrderResponse response = new CreateOrderResponse(request.getDishes(), price, orderRequest.getID());
        
        submitOrder(orderRequest);
        resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
        resultExecute.put(AppConstant.RESPONSE_KEY.DATA, response);
        resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Create order dishes success");
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
        resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Submit order success");

        return new ResponseWrapper(resultExecute);
    }

    public ResponseWrapper getDishes() {
        Map<Object, Object> resultExecute = new HashMap<>();

        List<Dish> dishes = dishRepository.getDataList();
        List<DishResponse> response = new ArrayList<>(dishes.size());
        DishMapper mapper = new DishMapper();
        for (Dish dish : dishes) {
            response.add(mapper.toResponse(dish));
        }

        resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
        resultExecute.put(AppConstant.RESPONSE_KEY.DATA, response);
        resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Get dishes success");
        return new ResponseWrapper(resultExecute);
    }

    public ResponseWrapper createFeedback(FeedbackRequest request) {
        Map<Object, Object> resultExecute = new HashMap<>();
        feedbackRepository.updateRepository(request.getDate());
        
        if(orderRepository.findObjectByKey(request.getOrderID()) == null) {
            resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.NotOK());
            resultExecute.put(AppConstant.RESPONSE_KEY.DATA, null);
            resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Not exist order ID");
            return new ResponseWrapper(resultExecute);
        }

        FeedbackMapper mapper = new FeedbackMapper();
        Feedback feedback = mapper.toFeedback(request);
        feedbackRepository.saveObject(feedback);

        resultExecute.put(AppConstant.RESPONSE_KEY.RESULT, Result.OK());
        resultExecute.put(AppConstant.RESPONSE_KEY.DATA, null);
        resultExecute.put(AppConstant.RESPONSE_KEY.MESSAGE, "Create feedback succes");

        return new ResponseWrapper(resultExecute);
    }
}
