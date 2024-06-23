package src.render;

import src.utility.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Surface extends JPanel {
    public boolean initialised = false;

    public Vec2 pos = new Vec2();
    public Dimension size;
    public SurfaceGraphics graphics;

    public Surface(int width, int height) {
        this.size = new Dimension(width, height);
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

        initialised = true;
        graphics = new SurfaceGraphics(getGraphics(), size);
    }

    public void unInit() {
        initialised = false;
        graphics.dispose();
    }

    public void blit(CanvasSurface canvas) {
        graphics.drawImage(canvas, 0, 0, this);
    }

    public void blitScaled(CanvasSurface canvas, int scale) {
        Image img = canvas.getScaledInstance(canvas.size.width * scale, canvas.size.width * scale, BufferedImage.SCALE_DEFAULT);
        graphics.drawImage(img, 0, 0, this);
    }

    public CanvasSurface toCanvasSurface() {
        CanvasSurface c = new CanvasSurface(size);
        paintAll(c.graphics.getRawGraphics());
        return c;
    }

    @Override
    public Dimension getPreferredSize() {
        if (super.isPreferredSizeSet()) {
            return super.getPreferredSize();
        }
        return size;
    }

    public String toString() {
        return String.format("Surface[pos=%s, size=%s, initialised=%s]", pos, size, initialised);
    }
}
