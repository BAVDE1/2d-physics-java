package src.render;

import java.lang.reflect.Field;

public class Event {
    public static int CLOSE_PRESSED = 1;  // close button pressed
    public static int WINDOW_ICONIFIED = 2;  // window minimised
    public static int WINDOW_DEICONIFIED = 3;  // window de-minimised
    public static int WINDOW_FOCUSSED = 4;  // window focused
    public static int WINDOW_BLURRED = 5;  // window blurred

    public int type;

    public String getEventTypeString() throws IllegalAccessException {
        return getEventTypeString(type);
    }

    public static String getEventTypeString(int eventType) throws IllegalAccessException {
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
