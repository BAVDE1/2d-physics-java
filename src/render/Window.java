package src.render;

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

    public int width;
    public int height;
    public float scale = 1;

    public void initWindow(String windowName, int width, int height) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width/2) - (width/2);
        int y = (screen.height/2) - (height/2);
        initWindow(windowName, x, y, width, height);
    }

    public void initWindow(String windowName, int posX, int posY, int width, int height) {
        if (!initialized) {
            this.width = width;
            this.height = height;

            frame.setName(windowName);
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.addWindowListener(newWindowListener());
            frame.setBounds(posX, posY, this.width, this.height);
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

    public void scaleWindow(float scaleMultiplier) {
        float relativeScale = scaleMultiplier / scale;

        int scaledW = (int) (width * relativeScale);
        int scaledH = (int) (height * relativeScale);

        // retain position
        int newX = (frame.getX() + width / 2) - scaledW / 2;
        int newY = ((frame.getY() - 10) + height / 2) - scaledH / 2;

        frame.setBounds(newX, newY, scaledW, scaledH);
        scale = scaleMultiplier;
        width = scaledW;
        height = scaledH;
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
