package src.render;

import src.utility.Constants;

import javax.swing.*;
import java.awt.*;

public class Surface extends JPanel {
    public Dimension size;
    public boolean hasFrame = false;

    public Surface (int w, int h) {
        size = new Dimension(w, h);
    }

    public void init() {
        setBounds(0, 0, size.width, size.height);
        setMaximumSize(size);
        setMinimumSize(size);
        setPreferredSize(size);
    }

    public void render() {
        if (hasFrame) {
            Graphics g = getGraphics();

            g.setColor(new Color(Constants.randInt(0, 255)));
            g.fillRect(0, 0, getWidth(), getHeight());

            g.dispose();
        }
    }

    /**
     * ================
     * Graphics methods
     * ================
     */
}
