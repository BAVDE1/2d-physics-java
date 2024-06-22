package src.render;

import src.utility.Vec2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class SurfaceGraphics {
    private final Graphics graphics;
    private final Dimension size;

    public SurfaceGraphics(Graphics graphics, Dimension size) {
        if (graphics == null) {
            throw new NullPointerException("Graphics param passed to SurfaceGraphics() is null. Has the Surface been added to a Window?");
        }

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

    public void fillRect(Color col, Vec2 pos, Dimension size) {
        graphics.setColor(col);
        graphics.fillRect((int) pos.x, (int) pos.y, size.width, size.height);
    }

    public void drawRect(Color col, Vec2 pos, Dimension size) {
        graphics.setColor(col);
        graphics.drawRect((int) pos.x, (int) pos.y, size.width, size.height);
    }

    public void fillCircle(Color col, Vec2 pos, int radius) {
        graphics.setColor(col);
        pos = pos.sub(radius / 2.0);
        graphics.fillOval((int) pos.x, (int) pos.y, radius, radius);
    }

    public void drawCircle(Color col, Vec2 pos, int radius) {
        graphics.setColor(col);
        pos = pos.sub(radius / 2.0);
        graphics.drawOval((int) pos.x, (int) pos.y, radius, radius);
    }

    public void fillOval(Color col, Vec2 pos, Dimension size) {
        graphics.setColor(col);
        graphics.fillOval((int) pos.x, (int) pos.y, size.width, size.height);
    }

    public void drawOval(Color col, Vec2 pos, Dimension size) {
        graphics.setColor(col);
        graphics.drawOval((int) pos.x, (int) pos.y, size.width, size.height);
    }

    public void drawImage(BufferedImage img, int x, int y, ImageObserver o) {
        graphics.drawImage(img, x, y, o);
    }

    public void drawImage(Image img, int x, int y, ImageObserver o) {
        graphics.drawImage(img, x, y, o);
    }

    public Graphics getRawGraphics() {
        return graphics;
    }

    public void dispose() {
        graphics.dispose();
    }
}
