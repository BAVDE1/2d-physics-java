package src.render;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.LinkedList;

public class Window {
    public boolean initiated = false;
    public boolean open = false;
    public JFrame frame = new JFrame();

    public LinkedList<Event> eventQueue = new LinkedList<>();

    public int width;
    public int height;

    public void initWindow(String windowName, int ScreenWidth, int ScreenHeight) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width/2) - (ScreenWidth/2);
        int y = (screen.height/2) - (ScreenHeight/2);
        initWindow(windowName, x, y, ScreenWidth, ScreenHeight);
    }

    public void initWindow(String windowName, int posX, int posY, int ScreenWidth, int ScreenHeight) {
        if (!initiated) {
            width = ScreenWidth;
            height = ScreenHeight;

            frame.setName(windowName);
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.addWindowListener(createWindowListener());
            frame.setBounds(posX, posY, width, height);
            initiated = true;
        }
    }

    private WindowListener createWindowListener() {
        return new WindowListener() {
            public void windowOpened(WindowEvent e) {}
            public void windowClosed(WindowEvent e) {}
            public void windowClosing(WindowEvent e) {queueEvent(Event.CLOSE_PRESSED);}
            public void windowIconified(WindowEvent e) {queueEvent(Event.WINDOW_ICONIFIED);}
            public void windowDeiconified(WindowEvent e) {queueEvent(Event.WINDOW_DEICONIFIED);}
            public void windowActivated(WindowEvent e) {queueEvent(Event.WINDOW_FOCUSSED);}
            public void windowDeactivated(WindowEvent e) {queueEvent(Event.WINDOW_BLURRED);}
        };
    }

    public void setWindowIcon(Image image) {
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

    public void open() {
        open = true;
        frame.setVisible(true);
    }

    public void close() {
        open = false;
        frame.setVisible(false);
    }

    public void shutDown() {
        System.exit(0);
    }
}
