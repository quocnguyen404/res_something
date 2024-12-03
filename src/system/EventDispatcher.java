package system;

import java.util.HashMap;
import java.util.Map;

import listener.Event;
import listener.Listener;

public class EventDispatcher {
    private EventDispatcher() {}
    private static Map<Event, Listener> dispatcher = new HashMap<>();

    // If event already exist, replace old listener with the new one
    static void addEvent(Event event, Listener listener) {
        dispatcher.put(event, listener);
    }

    static void removeEvent(Event event) {
        try {
            if(dispatcher.containsKey(event)) {
                dispatcher.remove(event);
            } else {
                throw new Exception(String.format("Not exist event [%s] to remove", event.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final static Listener getListener(Event event) {
        return dispatcher.get(event);
    }

    public static void invoke(Event event) {
        dispatcher.get(event).invoke();
    }

    public static <T> void invoke(Event event, T d) {
        dispatcher.get(event).invoke(d);
    }

    public static <T, T1> void invoke(Event event, T d, T1 d1) {
        dispatcher.get(event).invoke(d, d1);
    }

    public static <T, T1, T2> void invoke(Event event, T d, T1 d1, T2 d2) {
        dispatcher.get(event).invoke(d, d1, d2);
    }
}
