package src.render;

import src.utility.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Surface extends Canvas {
    public Surface (double w, double h) {
        new Surface(new Vec2(w, h));
    }

    public Surface(Vec2 size) {
        Dimension d = new Dimension((int) size.x, (int) size.y);

        setBounds(0, 0, d.width, d.height);
        setMaximumSize(d);
        setMinimumSize(d);
        setPreferredSize(d);
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.RED);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.dispose();
        bs.show();
    }

    /**
     * ================
     * Graphics methods
     * ================
     */
}
