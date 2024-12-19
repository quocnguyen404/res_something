package mapper;

import dao.Dish;
import dto.request.DishRequest;
import dto.request.UpdateDishRequest;
import dto.response.DishResponse;

public class DishMapper {
    public Dish toDish(UpdateDishRequest request) {
        return new Dish(request.getNewDishName(), request.getPrice());
    }

    public Dish toDish(DishRequest request) {
        return new Dish(request.getDishName(), request.getPrice());
    }

    public DishResponse toResponse(Dish dish) {
        return new DishResponse(dish.getDishName(), dish.getPrice());
    }
}
