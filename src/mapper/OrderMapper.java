package mapper;

import dao.Order;
import dto.request.OrderRequest;

public class OrderMapper {
    public Order toOrder(OrderRequest request) {
        Order order = new Order();
        order.setID(request.getID());
        order.setDishes(request.getDishes());
        order.setPrice(request.getPrice());
        order.setTime(request.getTime());
        return order;
    }
}
