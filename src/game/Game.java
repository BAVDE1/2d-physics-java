package src.game;

import src.Main;
import src.game.objects.Circle;
import src.game.objects.Polygon;
import src.game.objects.SquarePoly;
import src.window.*;
import src.utility.MathUtils;
import src.utility.Vec2;
import src.window.Event;
import src.window.Window;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class Game {
    public int frameCounter = 0;
    public double timeStarted;  // milliseconds

    public boolean running = false;
    public boolean optimiseTimeStepper = true;  // recommended if fps < 200
    private final Window window = new Window(Constants.BG_COL);

    Scene mainScene = new Scene();

    CanvasSurface canvasSurface = new CanvasSurface(Constants.BASE_SIZE);
    Surface finalSurface = new Surface(Constants.SCALED_SIZE);

    public Game() {
        window.init(Constants.WINDOW_NAME, Constants.SCALED_SIZE, finalSurface);
    }

    public void start() {
        if (!running) {
            running = true;
            window.open();

            Thread timeStepper = Main.newTimeStepper(Constants.DT, this);
            timeStarted = System.currentTimeMillis();
            timeStepper.start();

            Circle c = new Circle(new Vec2(10, 10), 10);
            SquarePoly sp = new SquarePoly(new Vec2(50, 10), new Dimension(10, 30));
            src.game.objects.Polygon p = new Polygon(new Vec2(30, 50), new ArrayList<>(List.of(new Vec2(), new Vec2(15, 0), new Vec2(0, 20))));
            mainScene.objectsGroup.add(c);
            mainScene.objectsGroup.add(sp);
            mainScene.objectsGroup.add(p);
        }
    }

    private void windowEvent(int type, WindowEvent we) {
        if (type == Event.CLOSE_PRESSED) {
            running = false;
            window.shutDown();
        }
    }

    private void mouseEvent(int type, MouseEvent me) {}

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

    private void update(double dt) {
        mainScene.update(dt);
    }

    private void render() {
        canvasSurface.fill(Constants.BG_COL);  // before rendering

        mainScene.render(canvasSurface);
        canvasSurface.drawText(Color.WHITE, new Vec2(), 10, "abcdefghijklmnopqrstuvwxyz1234567890");

        finalSurface.blitScaled(canvasSurface, Constants.RES_MUL);  // finish rendering
    }

    /** returns time taken in seconds */
    public double mainLoop(double dt) {
        double tStart = System.nanoTime();

        double fps = ++frameCounter / ((System.currentTimeMillis() - timeStarted) / 1_000);
        String sFps = String.valueOf(MathUtils.round(fps, 1));
        window.setTitle(String.format("%s (%s fps)", Constants.WINDOW_NAME, sFps));

        events();
        update(dt);
        render();
        return MathUtils.nanoToSecond(System.nanoTime() - tStart);
    }
}
