package src.window;

import src.game.Constants;
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

    public Window() {}
    public Window(Color bg_col) {
        this.bgCol = bg_col;
    }

    public void init(String windowName, Dimension size, Screen finalScreen) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        Vec2 screenSize = new Vec2(screen.width, screen.height);
        Vec2 pos = screenSize.div(2).sub(Vec2.fromDim(size).div(2));

        init(windowName, pos, size, finalScreen);
    }

    public void init(String windowName, Vec2 pos, Dimension size, Screen finalScreen) {
        if (!initialised) {
            this.size = size;

            setTitle(windowName);
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.getContentPane().setLayout(new GridLayout(1, 1));
            frame.setBounds((int) pos.x, (int) pos.y, size.width, size.height);
            frame.getContentPane().setBackground(bgCol);

            addFinalScreen(finalScreen);
            Listener.addWindowListeners(this);

            frame.setResizable(false);
            initialised = true;
        }
    }

    private void addFinalScreen(Screen finalScreen) {
        if (!initialised) {
            frame.getContentPane().add(finalScreen.getRawPanel(), BorderLayout.CENTER);  // do before packing
            frame.pack();
            finalScreen.init();  // init after packing so surface graphics are current
        }
    }

    public void addSurface(Screen surface) {
        if (!surface.initialised) {
            frame.getContentPane().add(surface.getRawPanel(), BorderLayout.CENTER);
            surface.init();
        }
    }

    public void removeSurface(Screen surface) {
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

    /** Raises an event that must be caught, and then shutDown() the window */
    public void closeWindowSafe() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    /** Unsafe shutdown. Call closeWindowSafe() instead, catch the event and then call shutDown() */
    public void shutDown() {
        open = false;
        System.exit(0);
    }

    public Insets getInsets() {
        return frame.getInsets();
    }

    public void setTitle(String str) {
        frame.setTitle(str);
    }

    public Vec2 getWindowPos() {
        return new Vec2(frame.getLocation());
    }

    /** Returns mouse position relative to top left of window's screen */
    public Vec2 getMousePos() {
        Vec2 pos = new Vec2(MouseInfo.getPointerInfo().getLocation());
        pos.subSelf(getWindowPos());
        return pos.sub(new Vec2(getInsets().left, getInsets().top));
    }

    /** Returns mouse position scaled by the resolution multiplier */
    public Vec2 getScaledMousePos() {
        return getMousePos().div(Constants.RES_MUL);
    }

    public void repaint() {
        frame.repaint();
    }

    public JFrame getRawFrame() {
        return frame;
    }

    @Override
    public String toString() {
        return String.format("Window(size=%s)", size);
    }
}
