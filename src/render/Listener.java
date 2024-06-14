package src.render;

import java.awt.event.*;

public class Listener {
    public static MouseListener newMouseListener(Window w) {
        return new MouseListener() {
            public void mouseClicked(MouseEvent e) {}
            public void mousePressed(MouseEvent e) {w.queueEvent(new Event<MouseEvent>(Event.MOUSE_PRESSED, e));}
            public void mouseReleased(MouseEvent e) {w.queueEvent(new Event<MouseEvent>(Event.MOUSE_RELEASED, e));}
            public void mouseEntered(MouseEvent e) {w.queueEvent(new Event<MouseEvent>(Event.MOUSE_ENTERED, e));}
            public void mouseExited(MouseEvent e) {w.queueEvent(new Event<MouseEvent>(Event.MOUSE_EXITED, e));}
        };
    }

    public static WindowListener newWindowListener(Window w) {
        return new WindowListener() {
            public void windowOpened(WindowEvent e) {}
            public void windowClosed(WindowEvent e) {}
            public void windowClosing(WindowEvent e) {w.queueEvent(new Event<WindowEvent>(Event.CLOSE_PRESSED, e));}
            public void windowIconified(WindowEvent e) {w.queueEvent(new Event<WindowEvent>(Event.WINDOW_MINIMISED, e));}
            public void windowDeiconified(WindowEvent e) {w.queueEvent(new Event<WindowEvent>(Event.WINDOW_MAXIMISED, e));}
            public void windowActivated(WindowEvent e) {w.queueEvent(new Event<WindowEvent>(Event.WINDOW_FOCUSSED, e));}
            public void windowDeactivated(WindowEvent e) {w.queueEvent(new Event<WindowEvent>(Event.WINDOW_BLURRED, e));}
        };
    }

    public static KeyListener newKeyListener(Window w) {
        return new KeyListener() {
            public void keyTyped(KeyEvent e) {w.queueEvent(new Event<KeyEvent>(Event.KEY_TYPED, e));}
            public void keyPressed(KeyEvent e) {w.queueEvent(new Event<KeyEvent>(Event.KEY_PRESSED, e));}
            public void keyReleased(KeyEvent e) {w.queueEvent(new Event<KeyEvent>(Event.KEY_RELEASED, e));}
        };
    }
}
