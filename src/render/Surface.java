package src.render;

import src.utility.Constants;
import src.utility.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;
import java.util.random.RandomGenerator;

public class Surface extends Canvas {
    public Dimension size;

    public Surface (int w, int h) {
        size = new Dimension(w, h);
    }

    public void init() {
        createBufferStrategy(2);
        setBounds(0, 0, size.width, size.height);
        setMaximumSize(size);
        setMinimumSize(size);
        setPreferredSize(size);
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        Graphics g = bs.getDrawGraphics();

        g.setColor(new Color(Constants.randInt(0, 255)));
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
