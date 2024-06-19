package src.game;

import src.render.Event;
import src.render.Surface;
import src.render.Window;
import src.utility.Constants;
import src.utility.Vec2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

public class Game {
    private final Window window;

    public Game(Window window) {
        this.window = window;
    }

    public void events() {
        if (!window.eventQueue.isEmpty()) {
            for (Event<?> ev : window.popAllEvents()) {

                // window events
                if (ev.event instanceof WindowEvent e) {
                    if (ev.type == Event.CLOSE_PRESSED) {
                        window.shutDown();
                    }
                }

                // key events
                if (ev.event instanceof KeyEvent e) {
                    if (ev.type == Event.KEY_PRESSED) {
                        if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
                            window.closeWindowSafe();
                        }
                    }
                }
            }
        }
    }

    public void update(double dt) {}

    public void render() {
    }

    public void mainLoop(double dt) {
        events();
        update(dt);
        render();
    }
}
