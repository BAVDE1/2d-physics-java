package src.render;

import javax.swing.*;
import java.awt.*;

public class Surface extends JPanel {
    public boolean initialised = false;

    public Dimension size;
    private Graphics graphics;

    public Surface (int w, int h) {
        size = new Dimension(w, h);
    }

    public void init() {
        setBounds(0, 0, size.width, size.height);
        setMaximumSize(size);
        setMinimumSize(size);
        setPreferredSize(size);

        initialised = true;
        graphics = getGraphics();
    }

    public void unInit() {
        initialised = false;
        graphics.dispose();
    }

    /**
     * ================
     * Graphics methods
     * ================
     */

    public void fill(Color color) {
        if (initialised) {
            graphics.setColor(color);
            graphics.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
