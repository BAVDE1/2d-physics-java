package src.game;

import src.Main;
import src.render.Event;
import src.render.Surface;
import src.render.Window;
import src.utility.Constants;
import src.utility.Vec2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

public class Game {
    public boolean running = false;
    private final Window window = new Window();

    Surface s = new Surface(Constants.BASE_WIDTH, Constants.BASE_HEIGHT);

    public Game() {
        Dimension size = new Dimension(Constants.BASE_WIDTH, Constants.BASE_HEIGHT);
        window.initWindow(Constants.WINDOW_NAME, size);
        window.scaleWindow(Constants.RES_MUL);

        window.addSurface(s);
    }

    public void start() {
        running = true;
        window.open();

        Thread timeStepper = Main.newTicker(Constants.DT, this);
        timeStepper.start();
    }

    private void events() {
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

                    if (e.getKeyCode() == KeyEvent.VK_E) {
                        window.removeSurface(s);
                    } else if (e.getKeyCode() == KeyEvent.VK_R) {
                        window.addSurface(s);
                    }
                }
            }
        }
    }

    private void update(double dt) {}

    private void render() {
        s.render();
    }

    public void mainLoop(double dt) {
        events();
        update(dt);
        render();
    }
}
