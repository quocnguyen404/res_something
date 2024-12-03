package debug;

import java.lang.reflect.Field;

import dto.response.ResponseWrapper;

public class Debug {
    public static void printResponse(ResponseWrapper response) {
        System.out.println(String.format("Result: %s", response.isOK()));
        System.out.println(String.format("Message: %s", response.getMessage()));
        System.out.println(String.format("Data: %s", response.getData()));
    }

    public static void printObject(Object obj) {
        System.out.println(objectToString(obj));
    }

    private static String objectToString(Object obj) {
        StringBuilder result = new StringBuilder();
        Class<?> clazz = obj.getClass();
        result.append(clazz.getSimpleName()).append("{");
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true); // Allow access to private fields
            try {
                result.append(field.getName()).append("=")
                      .append(field.get(obj)).append(", ");
            } catch (IllegalAccessException e) {
                result.append(field.getName()).append("=N/A, ");
            }
        }
        if (result.length() > 2) result.setLength(result.length() - 2); // Remove trailing comma
        result.append("}");
        return result.toString();
    }
}
