package src.rendering;

import src.game.Polygon;
import src.utility.Vec2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public abstract class SurfaceGraphics {
    public boolean initialised = false;
    private Graphics graphics;
    private final Dimension size;

    public SurfaceGraphics(Dimension size) {
        this.size = size;
    }

    public void init(Graphics graphics) {
        if (!initialised) {
            initialised = true;
            this.graphics = graphics;
        }
    }

    public void unInit() {
        if (initialised) {
            initialised = false;
            graphics.dispose();
        }
    }

    public void fill(Color col) {
        checkInit();
        graphics.setColor(col);
        graphics.fillRect(0, 0, size.width, size.height);
    }

    public void drawLine(Color col, Vec2 from, Vec2 to) {
        checkInit();
        graphics.setColor(col);
        graphics.drawLine((int) from.x, (int) from.y, (int) to.x, (int) to.y);
    }

    public void fillRect(Color col, Vec2 pos, Dimension size) {
        checkInit();
        graphics.setColor(col);
        graphics.fillRect((int) pos.x, (int) pos.y, size.width, size.height);
    }

    public void drawRect(Color col, Vec2 pos, Dimension size) {
        checkInit();
        graphics.setColor(col);
        graphics.drawRect((int) pos.x, (int) pos.y, size.width, size.height);
    }

    public void fillCircle(Color col, Vec2 pos, int radius) {
        checkInit();
        graphics.setColor(col);
        pos = pos.sub(radius / 2.0);
        graphics.fillOval((int) pos.x, (int) pos.y, radius, radius);
    }

    public void drawCircle(Color col, Vec2 pos, int radius) {
        checkInit();
        graphics.setColor(col);
        pos = pos.sub(radius / 2.0);
        graphics.drawOval((int) pos.x, (int) pos.y, radius, radius);
    }

    public void fillOval(Color col, Vec2 pos, Dimension size) {
        checkInit();
        graphics.setColor(col);
        graphics.fillOval((int) pos.x, (int) pos.y, size.width, size.height);
    }

    public void drawOval(Color col, Vec2 pos, Dimension size) {
        checkInit();
        graphics.setColor(col);
        graphics.drawOval((int) pos.x, (int) pos.y, size.width, size.height);
    }

    public void drawImage(BufferedImage img, int x, int y, ImageObserver o) {
        checkInit();
        graphics.drawImage(img, x, y, o);
    }

    public void drawImage(Image img, int x, int y, ImageObserver o) {
        checkInit();
        graphics.drawImage(img, x, y, o);
    }

    public void drawPolygon(Polygon p) {
        checkInit();
        Vec2 lastVert = p.getOrientedVert(0);  // store lastV to avoid re-calculation

        for (int i = 1; i < p.vCount; i++) {
            Vec2 vert = p.getOrientedVert(i);
            drawLine(p.colour, vert, lastVert);
            lastVert = vert;
        }
    }

    public Color getColour() {
        checkInit();
        return graphics.getColor();
    }

    public Graphics getRawGraphics() {
        checkInit();
        return graphics;
    }

    public void checkInit() {
        if (!initialised || graphics == null) {
            throw new NullPointerException("Surface graphics is not initialised, or graphics is null. Has the Surface been added to a window?");
        }
    }
}
