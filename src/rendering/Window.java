package src.rendering;

import src.utility.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Window {
    public boolean initialised = false;
    public boolean open = false;
    private final JFrame frame = new JFrame();
    private Color bgCol = Color.WHITE;

    public ArrayList<Event<?>> eventQueue = new ArrayList<>();

    public Dimension size;
    public double scale = 1;

    public Window() {}
    public Window(Color bg_col) {
        this.bgCol = bg_col;
    }

    public void init(String windowName, Dimension size, Surface finalSurface) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        Vec2 screenSize = new Vec2(screen.width, screen.height);
        Vec2 pos = screenSize.div(2).sub(Vec2.fromDim(size).div(2));

        init(windowName, pos, size, finalSurface);
    }

    public void init(String windowName, Vec2 pos, Dimension size, Surface finalSurface) {
        if (!initialised) {
            this.size = size;

            frame.setTitle(windowName);
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.getContentPane().setLayout(new GridLayout(1, 1));
            frame.setBounds((int) pos.x, (int) pos.y, size.width, size.height);
            frame.getContentPane().setBackground(bgCol);

            addFinalSurface(finalSurface);
            Listener.addWindowListeners(this);

            frame.setResizable(false);
            initialised = true;
        }
    }

    private void addFinalSurface(Surface finalSurface) {
        if (!initialised) {
            frame.getContentPane().add(finalSurface.getRawPanel(), BorderLayout.CENTER);
            frame.pack();
            finalSurface.init();  // init after packing so surface graphics are current
        }
    }

    public void addSurface(Surface surface) {
        if (!surface.initialised) {
            frame.getContentPane().add(surface.getRawPanel(), BorderLayout.CENTER);
            surface.init();
        }
    }

    public void removeSurface(Surface surface) {
        if (surface.initialised) {
            frame.remove(surface.getRawPanel());
            surface.unInit();
        }
    }

    public void setIcon(Image image) {
        frame.setIconImage(image);
    }

    public void queueEvent(Event<?> event) {
        eventQueue.add(event);
    }

    public ArrayList<Event<?>> popAllEvents() {
        ArrayList<Event<?>> list = new ArrayList<>(eventQueue);
        eventQueue.clear();
        return list;
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

    public JFrame getRawFrame() {
        return frame;
    }

    @Override
    public String toString() {
        return String.format("Window[size=%s]", size);
    }
}
