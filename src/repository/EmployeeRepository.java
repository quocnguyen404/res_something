package repository;

import common.AppConstant;
import dao.Employee;
import dao.WorkShift;

public class EmployeeRepository extends Repository<Integer,Employee> {

    public EmployeeRepository() {
        DLF = 6;
        DATA_PATH = AppConstant.DATA_PREFIX+"staff"+AppConstant.DATA_SUFFIX;
        TEMP_DATA_PATH = AppConstant.DATA_PREFIX+"staff"+AppConstant.DATA_TEMP_SUFFIX;
        loadAllDataFromFile();
    }

    @Override
    protected boolean comparePrimaryKey(String token, Employee obj) {
        return Integer.parseInt(token) == obj.getID();
    }

    @Override
    protected void removeObjectFromMap(Employee obj) {
        if(dataMap.containsKey(obj.getID())) {
            dataMap.remove(obj.getID());
        }
    }

    @Override
    protected void putObjectToMap(Employee obj) {
        dataMap.put(obj.getID(), obj);
    }

    //0id,1firstName,2lastName,3workShift,4salary,5workHours,6productivity
    @Override
    protected Employee dataToObject(String[] tokens) {
        return new Employee(Integer.parseInt(tokens[0]), tokens[1],
                                   tokens[2], WorkShift.valueOf(tokens[3]),
                                   Double.parseDouble(tokens[4]), Double.parseDouble(tokens[5]),
                                   Double.parseDouble(tokens[6]));
    }

    @Override
    protected String objectToData(Employee obj) {
        return String.format("%s,%s,%s,%s,%s,%s,%s", obj.getID(), obj.getFirstName(),
                                                            obj.getLastName(), obj.getWorkShift(),
                                                            obj.getSalary(), obj.getWorkHours(),
                                                            obj.getProductivity());
    }
}
