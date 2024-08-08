package src.window;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Listener {
    public static void addWindowListeners(Window window) {
        JFrame frame = window.getRawFrame();
        frame.addWindowListener(Listener.newWindowListener(window));
        frame.addMouseListener(Listener.newMouseListener(window));
        frame.addMouseWheelListener(Listener.newMouseWheelListener(window));
        frame.addKeyListener(Listener.newKeyListener(window));
    }

    public static WindowListener newWindowListener(Window w) {
        return new WindowListener() {
            public void windowOpened(WindowEvent e) {}
            public void windowClosed(WindowEvent e) {}
            public void windowClosing(WindowEvent e) {w.queueEvent(new Event<>(Event.CLOSE_PRESSED, e));}
            public void windowIconified(WindowEvent e) {w.queueEvent(new Event<>(Event.WINDOW_MINIMISED, e));}
            public void windowDeiconified(WindowEvent e) {w.queueEvent(new Event<>(Event.WINDOW_MAXIMISED, e));}
            public void windowActivated(WindowEvent e) {w.queueEvent(new Event<>(Event.WINDOW_FOCUSSED, e));}
            public void windowDeactivated(WindowEvent e) {w.queueEvent(new Event<>(Event.WINDOW_BLURRED, e));}
        };
    }

    public static MouseListener newMouseListener(Window w) {
        return new MouseListener() {
            public void mouseClicked(MouseEvent e) {}
            public void mousePressed(MouseEvent e) {w.queueEvent(new Event<>(Event.MOUSE_PRESSED, e));}
            public void mouseReleased(MouseEvent e) {w.queueEvent(new Event<>(Event.MOUSE_RELEASED, e));}
            public void mouseEntered(MouseEvent e) {w.queueEvent(new Event<>(Event.MOUSE_ENTERED, e));}
            public void mouseExited(MouseEvent e) {w.queueEvent(new Event<>(Event.MOUSE_EXITED, e));}
        };
    }

    public static MouseWheelListener newMouseWheelListener(Window w) {
        return (e) -> w.queueEvent(new Event<>(Event.MOUSE_WHEEL_SCROLLED, e));
    }

    public static KeyListener newKeyListener(Window w) {
        ArrayList<Integer> currentPressedKeys = new ArrayList<>();

        return new KeyListener() {
            public void keyTyped(KeyEvent e) {w.queueEvent(new Event<>(Event.KEY_TYPED, e));}
            public void keyPressed(KeyEvent e) {
                if (!currentPressedKeys.contains(e.getKeyCode())) {
                    w.queueEvent(new Event<>(Event.KEY_PRESSED, e));
                    currentPressedKeys.add(e.getKeyCode());
                }
            }
            public void keyReleased(KeyEvent e) {
                w.queueEvent(new Event<>(Event.KEY_RELEASED, e));
                currentPressedKeys.remove((Integer) e.getKeyCode());
            }
        };
    }
}
