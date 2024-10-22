package common;

public class Result {
    private String message;
    private String code;
    private boolean isOK;

    public Result() {
    }
    
    public Result(String message, boolean isOK, String code) {
        this.message = message;
        this.isOK = isOK;
        this.code = code;
    }

    public static Result OK() {
        return new Result("OK", true, null);
    }

    public static Result NotOK() {
        return new Result("Not OK", false, null);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isOK() {
        return isOK;
    }

    public void setOK(boolean isOK) {
        this.isOK = isOK;
    }
}
