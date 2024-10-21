package repository;

import dao.Dish;
import common.AppConstant;

public class DishRepository extends Repository<String, Dish> {

    public DishRepository() {
        DLF = 2;
        DATA_PATH = AppConstant.DATA_PREFIX+"dish"+AppConstant.DATA_SUFFIX;
        TEMP_DATA_PATH = AppConstant.DATA_PREFIX+"dish"+AppConstant.DATA_TEMP_SUFFIX;
    }

    @Override
    protected boolean comparePrimaryKey(String token, Dish obj) {
        return token.equals(obj.getDishName());
    }

    @Override
    protected void removeObjectFromMap(Dish obj) {
        if(dataMap.containsKey(obj.getDishName())) {
            dataMap.remove(obj.getDishName());
        }
    }

    @Override
    protected void putObjectToMap(Dish obj) {
        dataMap.put(obj.getDishName(), obj);
    }

    //0name,1price
    @Override
    protected Dish dataToObject(String[] tokens) {
        return new Dish(tokens[0], Double.parseDouble(tokens[1]));
    }

    @Override
    protected String objectToData(Dish obj) {
        return String.format("%s,%s", obj.getDishName(), obj.getPrice());
    }
}
