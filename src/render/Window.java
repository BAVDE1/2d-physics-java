package src.render;

import src.game.Game;
import src.utility.Constants;
import src.utility.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

public class Window {
    public boolean initialized = false;
    public boolean open = false;
    public final JFrame frame = new JFrame();

    public LinkedList<Event<?>> eventQueue = new LinkedList<>();

    public Dimension size;
    public double scale = 1;

    public void initWindow(String windowName, Dimension size, Game game) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        Vec2 screenSize = new Vec2(screen.width, screen.height);
        Vec2 pos = screenSize.div(2).sub(Vec2.fromDim(size).div(2));

        initWindow(windowName, pos, size, game);
    }

    public void initWindow(String windowName, Vec2 pos, Dimension size, Game game) {
        if (!initialized) {
            this.size = size;

            frame.add(game);
            frame.setTitle(windowName);
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.setBounds((int) pos.x, (int) pos.y, size.width, size.height);
            frame.setResizable(false);
            frame.setLayout(null);

            frame.addWindowListener(Listener.newWindowListener(this));
            frame.addMouseListener(Listener.newMouseListener(this));
            frame.addKeyListener(Listener.newKeyListener(this));

            frame.pack();
            initialized = true;
        }
    }

    public void scaleWindow(double scaleMultiplier) {
        Vec2 s = Vec2.fromDim(size);
        Vec2 scaledSize = s.mul(scaleMultiplier / scale);

        // retain position
        Vec2 framePos = new Vec2(frame.getX(), frame.getY());
        Vec2 newPos = framePos.add(s.div(2)).sub(scaledSize.div(2));

        frame.setBounds((int) newPos.x, (int) newPos.y, (int) scaledSize.x, (int) scaledSize.y);
        scale = scaleMultiplier;
        size = scaledSize.toDim();
    }

    public void addSurface(Surface surface) {
        frame.add(surface);
//        surface.createBufferStrategy(Constants.BUFFER_STRAT);
    }

    public void setIcon(Image image) {
        frame.setIconImage(image);
    }

    public void queueEvent(Event<?> event) {
        eventQueue.add(event);
    }

    public LinkedList<Event<?>> popAllEvents() {
        LinkedList<Event<?>> e = new LinkedList<>(eventQueue);
        eventQueue.clear();
        return e;
    }

    public void open() {
        open = true;
        frame.setVisible(true);
    }

    public void closeWindowSafe() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    /**
     * Unsafe shutdown. Call closeWindowSafe() instead, catch the event and then call shutDown()
     */
    public void shutDown() {
        open = false;
        System.exit(0);
    }
}
