package repository;

import java.time.LocalDate;

import common.AppConstant;
import dao.Attendance;
import utilities.AppUtilities;

public class AttendanceRepository extends DynamicRepository<String,Attendance> {

    public AttendanceRepository() {
        DLF = 3;
        DATA_FOLDER = AppConstant.DATA_PREFIX+"attendance\\";
        updateRepository(LocalDate.now());
    }

    @Override
    protected boolean comparePrimaryKey(String token, Attendance obj) {
        return token.equals(obj.getUserName());
    }
    
    @Override
    protected void removeObjectFromMap(Attendance obj) {
        if(dataMap.containsKey(obj.getUserName())) {
            dataMap.remove(obj.getUserName());
        }
    }

    @Override
    protected void putObjectToMap(Attendance obj) {
        dataMap.put(obj.getUserName(), obj);
    }

    @Override
    protected Attendance dataToObject(String[] tokens) {
        return new Attendance(tokens[0], AppUtilities.localTimeParse(tokens[1]), AppUtilities.localTimeParse(tokens[2]));
    }

    @Override
    protected String objectToData(Attendance obj) {
        return String.format("%s,%s,%s", obj.getUserName(), obj.getCheckinTime(), obj.getCheckoutTime());
    }
}
