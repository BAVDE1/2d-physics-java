package src.game;

import src.Main;
import src.rendering.CanvasSurface;
import src.rendering.Event;
import src.rendering.Surface;
import src.rendering.Window;
import src.utility.Constants;
import src.utility.Vec2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

public class Game {
    public boolean running = false;
    private final Window window = new Window(Constants.BG_COL);

    CanvasSurface canvasSurface = new CanvasSurface(Constants.BASE_SIZE);
    Surface finalSurface = new Surface(Constants.SCALED_SIZE);

    public Game() {
        window.init(Constants.WINDOW_NAME, Constants.SCALED_SIZE, finalSurface);
    }

    public void start() {
        if (!running) {
            running = true;
            window.open();

            Thread timeStepper = Main.newTicker(Constants.DT, this);
            timeStepper.start();

            canvasSurface.drawLine(Color.WHITE, new Vec2(), Vec2.fromDim(canvasSurface.size));
        }
    }

    private void windowEvent(int type, WindowEvent we) {
        if (type == Event.CLOSE_PRESSED) {
            running = false;
            window.shutDown();
        }
    }

    private void mouseEvent(int type, MouseEvent me) {

    }

    private void keyEvent(int type, KeyEvent ke) {
        if (type == Event.KEY_PRESSED) {
            if (ke.getKeyChar() == KeyEvent.VK_ESCAPE) {
                window.closeWindowSafe();
            }
        }
    }

    private void events() {
        if (!window.eventQueue.isEmpty()) {
            for (Event<?> event : window.popAllEvents()) {
                switch (event.event) {
                    case WindowEvent e: windowEvent(event.type, e); break;
                    case MouseEvent e: mouseEvent(event.type, e); break;
                    case KeyEvent e: keyEvent(event.type, e); break;
                    default: throw new ClassFormatError(String.format("'%s' case not handled. add it to events switch.", event.event));
                }
            }
        }
    }

    private void update(double dt) {}

    private void render() {
        finalSurface.blitScaled(canvasSurface, Constants.RES_MUL);
    }

    public void mainLoop(double dt) {
        events();
        update(dt);
        render();
    }
}
