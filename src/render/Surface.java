package src.render;

import src.utility.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Surface extends JPanel {
    public boolean initialised = false;

    public Vec2 pos = new Vec2(0, 0);
    public Dimension size;
    public SurfaceGraphics graphics;

    public Surface(int width, int height) {
        size = new Dimension(width, height);
    }

    public Surface(Dimension size) {
        this.size = size;
    }

    public Surface(int width, int height, Vec2 pos) {
        size = new Dimension(width, height);
        this.pos = pos;
    }

    public void init() {
        setLayout(new BorderLayout());

        setBounds((int) pos.x,  (int) pos.y, size.width, size.height);
        setMaximumSize(size);
        setMinimumSize(size);
        setPreferredSize(size);

        initialised = true;
        graphics = new SurfaceGraphics(getGraphics(), size);
    }

    public void unInit() {
        initialised = false;
        graphics.dispose();
    }

    public void blit(BufferedImage bImg) {
        graphics.drawImage(bImg, 0, 0, this);
    }

    public void blitScaled(BufferedImage bImg, int scale) {
        Image ing = bImg.getScaledInstance(bImg.getWidth() * scale, bImg.getHeight() * scale, BufferedImage.SCALE_DEFAULT);
        graphics.drawImage(ing, 0, 0, this);
    }

    @Override
    public String toString() {
        return String.format("Surface[pos=%s, size=%s, initialised=%s]", pos, size, initialised);
    }
}
