package src.game;

import src.Main;
import src.render.CanvasSurface;
import src.render.Event;
import src.render.Surface;
import src.render.Window;
import src.utility.Constants;
import src.utility.Vec2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class Game {
    public boolean running = false;
    private final Window window = new Window();

    Surface finalSurface = new Surface(Constants.SCALED_SIZE);

    CanvasSurface canvasSurface = new CanvasSurface(Constants.BASE_SIZE, true);

    public Game() {
        window.initWindow(Constants.WINDOW_NAME, Constants.SCALED_SIZE);

        window.addSurface(finalSurface);


        System.out.println(window);
        System.out.println(finalSurface);
        System.out.println(canvasSurface);
    }

    public void start() {
        if (!running) {
            running = true;
            window.open();

            Thread timeStepper = Main.newTicker(Constants.DT, this);
            timeStepper.start();
        }
    }

    private void events() {
        if (!window.eventQueue.isEmpty()) {
            for (Event<?> ev : window.popAllEvents()) {

                // window events
                if (ev.event instanceof WindowEvent e) {
                    if (ev.type == Event.CLOSE_PRESSED) {
                        running = false;
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
                        window.removeSurface(finalSurface);
                    } else if (e.getKeyCode() == KeyEvent.VK_R) {
                        window.addSurface(finalSurface);
                    }
                }
            }
        }
    }

    private void update(double dt) {}

    private void render() {
        canvasSurface.graphics.fill(new Color(Constants.randInt(0, 255)));
        canvasSurface.graphics.line(Color.RED, new Vec2(0, 0), Vec2.fromDim(canvasSurface.size));
        finalSurface.blitScaled(canvasSurface, Constants.RES_MUL);
    }

    public void mainLoop(double dt) {
        events();
        update(dt);
        render();
    }
}
