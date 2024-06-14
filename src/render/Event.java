package src.render;

import java.lang.reflect.Field;

public class Event<E> {
    public static int CLOSE_PRESSED = 1;
    public static int WINDOW_MINIMISED = 2;
    public static int WINDOW_MAXIMISED = 3;
    public static int WINDOW_FOCUSSED = 4;
    public static int WINDOW_BLURRED = 5;

    public static int MOUSE_PRESSED = 10;
    public static int MOUSE_RELEASED = 11;
    public static int MOUSE_ENTERED = 12;
    public static int MOUSE_EXITED = 13;

    public static int KEY_TYPED = 20;
    public static int KEY_PRESSED = 21;
    public static int KEY_RELEASED = 22;

    public int type;
    public E event;

    public String getEventName() throws IllegalAccessException {
        return getEventName(type);
    }

    public static String getEventName(int eventType) throws IllegalAccessException {
        for (Field f : Event.class.getFields()) {
            try {
                if (f.get(f) instanceof Integer value) {
                    if (value == eventType) {
                        return f.getName();
                    }
                }
            } catch (IllegalArgumentException ignored) {}
        }
        return "FAILED: couldn't find event";
    }

    public Event(int type, E event) {
        this.type = type;
        this.event = event;
    }
}
