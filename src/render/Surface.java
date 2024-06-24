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

    @Override
    public Dimension getPreferredSize() {
        if (super.isPreferredSizeSet()) {
            return super.getPreferredSize();
        }
        return size;
    }

    public void blit(Image canvas) {
        blit(canvas, new Vec2());
    }

    public void blit(Image canvas, Vec2 pos) {
        graphics.drawImage(canvas, (int) pos.x, (int) pos.y, this);
    }

    public void blitScaled(CanvasSurface canvas, int scale) {
        blitScaled(canvas, scale, new Vec2());
    }

    public void blitScaled(CanvasSurface canvas, int scale, Vec2 pos) {
        Image img = canvas.getScaledInstance(canvas.size.width * scale, canvas.size.height * scale, BufferedImage.SCALE_DEFAULT);
        blit(img, pos);
    }

    public CanvasSurface toCanvasSurface() {
        CanvasSurface c = new CanvasSurface(size);
        paintAll(c.graphics.getRawGraphics());
        return c;
    }

    public String toString() {
        return String.format("Surface[pos=%s, size=%s, initialised=%s]", pos, size, initialised);
    }
}
