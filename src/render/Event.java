package src.render;

import java.lang.reflect.Field;

public class Event {
    public static int CLOSE_PRESSED = 1;
    public static int WINDOW_MINIMISED = 2;
    public static int WINDOW_MAXIMISED = 3;
    public static int WINDOW_FOCUSSED = 4;
    public static int WINDOW_BLURRED = 5;

    public int type;

    public String getEventName() throws IllegalAccessException {
        return getEventName(type);
    }

    public static String getEventName(int eventType) throws IllegalAccessException {
        for (Field f : Event.class.getFields()) {
            try {
                int value = (int) f.get(f);
                if (value == eventType) {
                    return f.getName();
                }
            } catch (IllegalArgumentException | ClassCastException ignored) {}
        }
        return "FAILED: couldn't find event";
    }

    public Event(int eventType) {
        type = eventType;
    }
}
