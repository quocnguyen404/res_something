package repository;

import java.time.LocalDate;

import common.AppConstant;
import dao.Feedback;

public class FeedbackRepository extends DynamicRepository<Integer,Feedback> {

    public FeedbackRepository() {
        DLF = 4;
        DATA_FOLDER = AppConstant.DATA_PREFIX+"feedback\\";
        updateRepository(LocalDate.now());
    }

    @Override
    protected boolean comparePrimaryKey(String token, Feedback obj) {
        return Integer.parseInt(token) == obj.getID();
    }

    @Override
    protected void removeObjectFromMap(Feedback obj) {
        if(dataMap.containsKey(obj.getID())) {
            dataMap.remove(obj.getID());
        }
    }

    @Override
    protected void putObjectToMap(Feedback obj) {
        dataMap.put(obj.getID(), obj);
    }

    //0id,1orderid,2customerName,3feedback
    @Override
    protected Feedback dataToObject(String[] tokens) {
        return new Feedback(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), tokens[2], tokens[3]);
    }

    @Override
    protected String objectToData(Feedback obj) {
        return String.format("%s,%s,%s,%s", obj.getID(), obj.getOrderID(), obj.getCustomerName(), obj.getFeedback());
    }
}
