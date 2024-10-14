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
        return new Result("OK", true, "200");
    }

    public static Result NotOK() {
        return new Result("Not OK", false, null);
    }
}
