package utilities;

import java.time.LocalTime;

import dto.response.AttendanceResponse;

public class AppUtilities {
    public static LocalTime localTimeParse(String str) {
        if(str.equals("null"))
            return null;
        return LocalTime.parse(str);
    }

    public static String attendanceResponseToStr(AttendanceResponse response) {
        return String.format("%s, %s, %s", response.getID(), response.getCheckinTime(), response.getCheckoutTime());
    }

    public static Boolean validateUserName(String userName) {
        return userName != null && !userName.isEmpty();
    }

    public static Boolean validatePassword(String password) {
        return password != null && password.length() >= 4;
    }
}
