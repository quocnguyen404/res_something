package common;

public class AppConstant {
    public static final String DATA_PREFIX = System.getProperty("user.dir")+"\\data\\";
    public static final String DATA_SUFFIX = ".dat";
    public static final String DATA_TEMP_SUFFIX = ".tmp";
    public static final Result OK_RESULT = new Result("OK", true, null);
    public static final Result NOT_OK_RESULT = new Result("Not OK", false, null);
    public interface RESPONSE_KEY {
        String RESULT = "RESULT_KEY";
        String DATA = "DATA_KEY";
        String MESSAGE = "MESSAGE_KEY";
    }
}
