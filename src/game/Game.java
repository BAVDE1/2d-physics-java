package src.game;

import src.Main;
import src.game.objects.Body;
import src.game.objects.Circle;
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

public class Game {
    public int frameCounter = 0;
    public double timeStarted = 0;  // in milliseconds
    public int secondsElapsed = 0;

    public boolean running = false;
    public boolean optimiseTimeStepper = true;  // recommended if fps < 200
    private final Window window = new Window(Constants.BG_COL);

    Scene mainScene = new Scene();

    Screen finalScreen = new Screen(this, Constants.SCALED_SIZE);
    CanvasSurface mainCanvas = new CanvasSurface(Constants.BASE_SIZE, Constants.BG_COL);
    CanvasSurface uiCanvas = new CanvasSurface(Constants.SCALED_SIZE, Constants.TRANSPARENT);

    Body holdingObj;

    public Game() {
        window.init(Constants.WINDOW_NAME, Constants.SCALED_SIZE, finalScreen);
    }

    public void start() {
        if (!running) {
            running = true;
            window.open();

            Thread timeStepper = Main.newTimeStepper(Constants.DT, this);
            timeStarted = System.currentTimeMillis();
            timeStepper.start();

            SquarePoly g = new SquarePoly(new Vec2(50, 250), true, new Dimension(400, 20));
            SquarePoly l = new SquarePoly(new Vec2(50, 100), true, new Dimension(20, 150));
            mainScene.objectsGroup.add(g);
            mainScene.objectsGroup.add(l);

            Circle c = new Circle(new Vec2(200, 0), 20);
            mainScene.objectsGroup.add(c);
            Circle c2 = new Circle(new Vec2(200, 0), 15);
            mainScene.objectsGroup.add(c2);
            SquarePoly sp = new SquarePoly(new Vec2(200, 0), new Dimension(200, 150));
            mainScene.objectsGroup.add(sp);
        }
    }

    /** Reduce natural velocity and replace with a mouse force */
    private void holdObj() {
        Vec2 force = window.getScaledMousePos().sub(holdingObj.pos);
        double maxF = Constants.MAX_MOUSE_FORCE * (Constants.FPS / 60.);
        double len = force.length();
        if (len > maxF) {
            force.divSelf(len);
            force.mulSelf(maxF);
        }
        force.mulSelf(holdingObj.mass);

        holdingObj.velocity.mulSelf(.85);  // reduce natural velocity
        holdingObj.applyForce(force.mul((holdingObj.invMass * 100)));
    }

    private void windowEvent(int type, WindowEvent we) {
        if (type == Event.CLOSE_PRESSED) {
            running = false;
            window.shutDown();
        }
    }

    private void mouseEvent(int type, MouseEvent me) {
        if (type == Event.MOUSE_PRESSED) {
            if (holdingObj == null) {  // probably don't need this but whatever
                for (Body obj : mainScene.objectsGroup.objects) {
                    if (!obj.isStatic && obj.isPointIn(window.getScaledMousePos())) {
                        holdingObj = obj;
                        break;
                    }
                }
            }
        }

        if (type == Event.MOUSE_RELEASED) {
            if (holdingObj != null) holdingObj = null;
        }
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

    private void update(double dt) {
        if (holdingObj != null) holdObj();
        mainScene.update(dt);
    }

    private void renderCanvases() {
        mainCanvas.clear();
        uiCanvas.clear();

        mainScene.render(mainCanvas);
        uiCanvas.drawText(Color.RED, new Vec2(), holdingObj == null ? "---" : holdingObj.toString());
        uiCanvas.drawText(Color.RED, new Vec2(0, 15), mainScene.toString());
    }

    /** Called in Screen so proper graphics is used */
    public void renderScreen(Graphics g) {
        finalScreen.blitScaled(g, mainCanvas);
        finalScreen.blit(g, uiCanvas);
    }

    /** returns time taken in seconds */
    public double mainLoop(double dt) {
        double tStart = System.nanoTime();
        frameCounter++;

        // update fps count every second
        int newSeconds = (int) Math.floor(MathUtils.millisToSecond(System.currentTimeMillis()) - MathUtils.millisToSecond(timeStarted));
        if (newSeconds != secondsElapsed) {
            String sFps = String.valueOf(MathUtils.round(frameCounter, 1));
            window.setTitle(String.format("%s (%s fps)", Constants.WINDOW_NAME, sFps));
            secondsElapsed = newSeconds;
            frameCounter = 0;  // so frame counter doesn't pass max int value
        }

        events();
        update(dt);
        renderCanvases();
        window.repaint();  // calls paintComponent on all surfaces added to window
        return MathUtils.nanoToSecond(System.nanoTime() - tStart);
    }
}
