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

public class Game extends Canvas {
    public boolean running = false;
    private final Window window = new Window();

    public Game() {
        Dimension size = new Dimension(Constants.BASE_WIDTH, Constants.BASE_HEIGHT);
        window.initWindow(Constants.WINDOW_NAME, size, this);
        window.scaleWindow(Constants.RES_MUL);

        createBufferStrategy(2);
        setSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
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
                }
            }
        }
    }

    private void update(double dt) {}

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.RED);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.dispose();
        bs.show();
    }

    public void mainLoop(double dt) {
        events();
        update(dt);
        render();
    }
}
