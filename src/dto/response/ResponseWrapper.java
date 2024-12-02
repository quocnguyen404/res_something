package dto.response;

import java.util.Map;

import common.AppConstant;

public class ResponseWrapper {
    private Map<Object,Object> response;

    public ResponseWrapper() {
    }

    public ResponseWrapper(Map<Object,Object> response) {
        this.response = response;
    }

    public Boolean isOK() {
        return response.get(AppConstant.RESPONSE_KEY.RESULT).equals(AppConstant.OK_RESULT);
    }
    
    public Object getData() {
        return response.get(AppConstant.RESPONSE_KEY.DATA);
    }

    public String getMessage() {
        return (String)response.get(AppConstant.RESPONSE_KEY.MESSAGE);
    }
}