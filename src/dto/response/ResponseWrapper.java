package dto.response;

import java.util.HashMap;
import java.util.Map;

import common.AppConstant;
import common.Result;

public class ResponseWrapper {
    private Map<Object,Object> response;

    public ResponseWrapper() {
    }

    public ResponseWrapper(Result result, Object data, String message) {
        response = new HashMap<Object, Object>();
        response.put(AppConstant.RESPONSE_KEY.RESULT, result);
        response.put(AppConstant.RESPONSE_KEY.DATA, data);
        response.put(AppConstant.RESPONSE_KEY.MESSAGE, message);
    }

    public ResponseWrapper(Map<Object,Object> response) {
        this.response = response;
    }

    public Boolean isOK() {
        return response.get(AppConstant.RESPONSE_KEY.RESULT).equals(Result.OK());
    }
    
    public final Object getData() {
        return response.get(AppConstant.RESPONSE_KEY.DATA);
    }

    public String getMessage() {
        return (String)response.get(AppConstant.RESPONSE_KEY.MESSAGE);
    }
}
