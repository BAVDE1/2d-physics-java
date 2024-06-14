package src.render;

import src.utility.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;

public class Window {
    public boolean initialized = false;
    public boolean open = false;
    public JFrame frame = new JFrame();

    public LinkedList<Event<?>> eventQueue = new LinkedList<>();

    public Vec2 size;
    public double scale = 1;

    public void initWindow(String windowName, Vec2 size) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Vec2 screenSize = new Vec2(screen.width, screen.height);
        Vec2 pos = screenSize.div(2).sub(size.div(2));
        initWindow(windowName, pos, size);
    }

    public void initWindow(String windowName, Vec2 pos, Vec2 size) {
        if (!initialized) {
            this.size = size;

            frame.setTitle(windowName);
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.setBounds((int) pos.x, (int) pos.y, (int) size.x, (int) size.y);
            frame.setResizable(false);

            frame.addMouseListener(Listener.newMouseListener(this));
            frame.addWindowListener(Listener.newWindowListener(this));
            frame.addKeyListener(Listener.newKeyListener(this));

            initialized = true;
        }
    }

    public void scaleWindow(double scaleMultiplier) {
        Vec2 scaledSize = size.mul(scaleMultiplier / scale);

        // retain position
        Vec2 framePos = new Vec2(frame.getX(), frame.getY());
        Vec2 newPos = framePos.add(size.div(2)).sub(scaledSize.div(2));

        frame.setBounds((int) newPos.x, (int) newPos.y, (int) scaledSize.x, (int) scaledSize.y);
        scale = scaleMultiplier;
        size = scaledSize;
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

    public void blitSurface(Surface surface) {

    }

    public void open() {
        open = true;
        frame.setVisible(true);
    }

    public void close() {
        open = false;
        frame.setVisible(false);
    }

    public void shutDown() {
        if (open) {close();}
        System.exit(0);
    }
}
