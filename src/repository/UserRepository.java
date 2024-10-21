package repository;

import common.AppConstant;
import dao.Role;
import dao.User;

public class UserRepository extends Repository<String, User> {

    public UserRepository() {
        DLF = 6;
        DATA_PATH = AppConstant.DATA_PREFIX+"user"+AppConstant.DATA_SUFFIX;
        TEMP_DATA_PATH = AppConstant.DATA_PREFIX+"user"+AppConstant.DATA_TEMP_SUFFIX;
        loadAllDataFromFile();
    }

    @Override
    protected boolean comparePrimaryKey(String token, User obj) {
        return token.equals(obj.getUserName());
    }
    
    @Override
    protected void removeObjectFromMap(User obj) {
        if(dataMap.containsKey(obj.getUserName())) {
            dataMap.remove(obj.getUserName());
        }
    }
        
    @Override
    protected void putObjectToMap(User obj) {
        dataMap.put(obj.getUserName(), obj);
    }

    //0username,1encodePassword,2password,3firstName,4lastName,5loginCount,6role
    @Override
    protected User dataToObject(String[] tokens) {
        return new User(tokens[0], tokens[1],
                             tokens[2], tokens[3],
                             tokens[4], Integer.parseInt(tokens[5]),
                             Role.valueOf(tokens[6]));
    }

    @Override
    protected String objectToData(User obj) {
        return String.format("%s,%s,%s,%s,%s,%s", obj.getUserName(), obj.getEncodePassword(),
                                                         obj.getPassword(), obj.getFirstName(),
                                                         obj.getLastName(), obj.getLoginCount(),
                                                         obj.getRole());
    }
}
