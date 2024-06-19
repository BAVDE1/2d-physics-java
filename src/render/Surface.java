package src.render;

import src.utility.Vec2;

import javax.swing.*;
import java.awt.*;

public class Surface extends Canvas {
    private Graphics graphics;

    public Surface(Vec2 size) {
        createBufferStrategy(2);
        graphics = getBufferStrategy().getDrawGraphics();

        setBounds(0, 0, (int) size.x, (int) size.y);
    }

    /**
     * ================
     * Graphics methods
     * ================
     */

    public void fill(Color color) {
        graphics.setColor(color);
        graphics.fillRect(0, 0, getWidth(), getHeight());
    }
}
