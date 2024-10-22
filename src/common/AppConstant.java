package common;

public class AppConstant {
    public static final String DATA_PREFIX = System.getProperty("user.dir")+"\\data\\";
    public static final String DATA_SUFFIX = ".dat";
    public static final String DATA_TEMP_SUFFIX = ".tmp";
    public static final String OK_RESULT = "OK";
    public static final String NOT_OK_RESULT = "Not OK";
    public interface RESPONSE_KEY {
        String RESULT = "RESULT_KEY";
        String DATA = "DATA_KEY";
        String MESSAGE = "MESSAGE_KEY";
    }
}
