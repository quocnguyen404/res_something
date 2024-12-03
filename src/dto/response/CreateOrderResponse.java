package dto.response;

import java.util.List;

import dao.Dish;
import dto.request.OrderRequest;

public class CreateOrderResponse {
    private List<Dish> dishes;
    private OrderRequest orderRequest;

    public CreateOrderResponse() {
    }

    public CreateOrderResponse(List<Dish> dishes, OrderRequest orderRequest) {
        this.dishes = dishes;
        this.orderRequest = orderRequest;
    }

    public final List<Dish> getDishes() {
        return this.dishes;
    }

    public OrderRequest getOrderRequest() {
        return orderRequest;
    }
}
