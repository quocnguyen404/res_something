package utilities;

import java.time.LocalTime;

public class AppUtilities {
    public static LocalTime localTimeParse(String str) {
        if(str.equals("null"))
            return null;
        return LocalTime.parse(str);
    }
}
