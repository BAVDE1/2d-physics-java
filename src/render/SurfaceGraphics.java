package src.render;

import src.utility.Vec2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class SurfaceGraphics {
    private final Graphics graphics;
    private final Dimension size;

    public SurfaceGraphics(Graphics graphics, Dimension size) {
        this.graphics = graphics;
        this.size = size;
    }

    public void fill(Color col) {
        graphics.setColor(col);
        graphics.fillRect(0, 0, size.width, size.height);
    }

    public void line(Color col, Vec2 from, Vec2 to) {
        graphics.setColor(col);
        graphics.drawLine((int) from.x, (int) from.y, (int) to.x, (int) to.y);
    }

    public void drawImage(BufferedImage img, int x, int y, ImageObserver o) {
        graphics.drawImage(img, x, y, o);
    }

    public void drawImage(Image img, int x, int y, ImageObserver o) {
        graphics.drawImage(img, x, y, o);
    }

    public void dispose() {
        graphics.dispose();
    }
}
