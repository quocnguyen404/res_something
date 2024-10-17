package dto.response.baseResponse;

import common.Result;

public class Response<T> {
    private String message;
    private boolean isOK;
    private Result result;
    private T data;

    public Response() {
        
    }
    public Response(String message, boolean isOK, T data) {
        this.message = message;
        this.isOK = isOK;
        this.data = data;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public boolean isOK() {
        return isOK;
    }
    public void setOK(boolean isOK) {
        this.isOK = isOK;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    public Result getResult() {
        return result;
    }
    public void setResult(Result result) {
        this.result = result;
    }
}
