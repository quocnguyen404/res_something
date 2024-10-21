package repository;

import java.time.LocalDate;
import java.time.LocalTime;

import common.AppConstant;
import dao.Attendance;

public class AttendanceRepository extends DynamicRepository<Integer,Attendance> {

    public AttendanceRepository() {
        DLF = 3;
        DATA_FOLDER = AppConstant.DATA_PREFIX+"attendance\\";
        updateRepository(LocalDate.now());
    }

    @Override
    protected boolean comparePrimaryKey(String token, Attendance obj) {
        return Integer.parseInt(token) == obj.getID();
    }
    
    @Override
    protected void removeObjectFromMap(Attendance obj) {
        if(dataMap.containsKey(obj.getID())) {
            dataMap.remove(obj.getID());
        }
    }

    @Override
    protected void putObjectToMap(Attendance obj) {
        dataMap.put(obj.getID(), obj);
    }

    @Override
    protected Attendance dataToObject(String[] tokens) {
        return new Attendance(Integer.parseInt(tokens[0]), LocalTime.parse(tokens[1]), LocalTime.parse(tokens[2]))
    }

    @Override
    protected String objectToData(Attendance obj) {
        return String.format("%s,%s,%s", obj.getID(), obj.getCheckinTime(), obj.getCheckoutTime());
    }
}
