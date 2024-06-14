package src.render;

import src.utility.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.LinkedList;

public class Window {
    public boolean initialized = false;
    public boolean open = false;
    public JFrame frame = new JFrame();

    public LinkedList<Event> eventQueue = new LinkedList<>();

    public Vec2 size;
    public double scale = 1;

    public void initWindow(String windowName, int width, int height) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width/2) - (width/2);
        int y = (screen.height/2) - (height/2);
        initWindow(windowName, x, y, width, height);
    }

    public void initWindow(String windowName, int posX, int posY, int width, int height) {
        if (!initialized) {
            size = new Vec2(width, height);

            frame.setName(windowName);
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.addWindowListener(newWindowListener());
            frame.setBounds(posX, posY, (int) size.x, (int) size.y);
            frame.setResizable(false);
            initialized = true;
        }
    }

    private WindowListener newWindowListener() {
        return new WindowListener() {
            public void windowOpened(WindowEvent e) {}
            public void windowClosed(WindowEvent e) {}
            public void windowClosing(WindowEvent e) {queueEvent(Event.CLOSE_PRESSED);}
            public void windowIconified(WindowEvent e) {queueEvent(Event.WINDOW_MINIMISED);}
            public void windowDeiconified(WindowEvent e) {queueEvent(Event.WINDOW_MAXIMISED);}
            public void windowActivated(WindowEvent e) {queueEvent(Event.WINDOW_FOCUSSED);}
            public void windowDeactivated(WindowEvent e) {queueEvent(Event.WINDOW_BLURRED);}
        };
    }

    public void scaleWindow(double scaleMultiplier) {
        Vec2 scaledSize = size.mul(scaleMultiplier / scale);

        // retain position
        Vec2 framePos = new Vec2(frame.getX(), frame.getY());
        Vec2 newPos = (framePos.add(size.div(2))).sub(scaledSize.div(2));

        frame.setBounds((int) newPos.x, (int) newPos.y, (int) scaledSize.x, (int) scaledSize.y);
        scale = scaleMultiplier;
        size = scaledSize;
    }

    public void setIcon(Image image) {
        frame.setIconImage(image);
    }

    public void queueEvent(int eventType) {
        Event event = new Event(eventType);
        eventQueue.add(event);
    }

    public LinkedList<Event> popAllEvents() {
        LinkedList<Event> e = new LinkedList<>(eventQueue);
        eventQueue.clear();
        return e;
    }

    public void blitSurface(Surface surface) {}

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
