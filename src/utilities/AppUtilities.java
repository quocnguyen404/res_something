package utilities;

import java.time.LocalTime;

public class AppUtilities {
    public static LocalTime localTimeParse(String str) {
        if(str.equals("null"))
            return null;
        return LocalTime.parse(str);
    }

    public static Boolean validateUserName(String userName) {
        return userName != null && !userName.isEmpty();
    }

    public static Boolean validatePassword(String password) {
        return password != null && password.length() >= 4;
    }
}
