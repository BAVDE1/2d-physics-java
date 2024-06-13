package src;

import src.render.Event;
import src.render.Window;

import java.util.concurrent.TimeUnit;

public class Main extends Window {
    public static void main(String[] args) throws InterruptedException, IllegalAccessException {
        Window window = new Main();
        window.initWindow("some window", 400, 300);
        window.open();

        while (true) {
            if (!window.eventQueue.isEmpty()) {
                for (Event ev : window.popAllEvents()) {
                    System.out.println(ev.getEventTypeString());
                }
            }
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
