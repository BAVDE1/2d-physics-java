package src.game;

import src.render.Event;
import src.render.Window;

public class Game {
    Window window;

    public Game(Window window) {
        this.window = window;
    }

    public void events() {
        if (!window.eventQueue.isEmpty()) {
            for (Event ev : window.popAllEvents()) {
                if (ev.type == Event.CLOSE_PRESSED) {
                    window.shutDown();
                }
            }
        }
    }

    public void update(double dt) {}

    public void render() {}

    public void mainLoop(double dt) {
        events();
        update(dt);
        render();
    }
}
