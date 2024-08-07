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
        checkInit();
    }

    public void unInit() {
        checkInit();
        if (initialised) {
            initialised = false;
            graphics.dispose();
        }
    }

    public void fill(Color col) {
        setColour(col);
        fill();
    }

    public void fill() {
        graphics.fillRect(0, 0, size.width, size.height);
    }

    public void drawLine(Color col, Vec2 from, Vec2 to) {
        setColour(col);
        drawLine(from, to);
    }

    public void drawLine(Vec2 from, Vec2 to) {
        graphics.drawLine((int) from.x, (int) from.y, (int) to.x, (int) to.y);
    }

    public void fillRect(Color col, Vec2 pos, Dimension size) {
        setColour(col);
        fillRect(pos, size);
    }

    public void fillRect(Vec2 pos, Dimension size) {
        graphics.fillRect((int) pos.x, (int) pos.y, size.width, size.height);
    }

    public void drawRect(Color col, Vec2 pos, Dimension size) {
        setColour(col);
        drawRect(pos, size);
    }

    public void drawRect(Vec2 pos, Dimension size) {
        graphics.drawRect((int) pos.x, (int) pos.y, size.width, size.height);
    }

    public void fillCircle(Color col, Vec2 pos, int radius) {
        setColour(col);
        fillCircle(pos, radius);
    }

    public void fillCircle(Vec2 pos, int radius) {
        radius *= 2;  // counteract oval draw cutting expected size in half
        pos = pos.sub(radius / 2.0);
        graphics.fillOval((int) pos.x, (int) pos.y, radius, radius);
    }

    public void drawCircle(Color col, Vec2 pos, int radius) {
        setColour(col);
        drawCircle(pos, radius);
    }

    public void drawCircle(Vec2 pos, int radius) {
        radius *= 2;  // counteract oval draw cutting expected size in half
        pos = pos.sub(radius / 2.0);
        graphics.drawOval((int) pos.x, (int) pos.y, radius, radius);
    }

    public void fillOval(Color col, Vec2 pos, Dimension size) {
        setColour(col);
        fillOval(pos, size);
    }

    public void fillOval(Vec2 pos, Dimension size) {
        graphics.fillOval((int) pos.x, (int) pos.y, size.width, size.height);
    }

    public void drawOval(Color col, Vec2 pos, Dimension size) {
        setColour(col);
        drawOval(pos, size);
    }

    public void drawOval(Vec2 pos, Dimension size) {
        graphics.drawOval((int) pos.x, (int) pos.y, size.width, size.height);
    }

    public void drawImage(BufferedImage img, int x, int y, ImageObserver o) {
        graphics.drawImage(img, x, y, o);
    }

    public void drawImage(Image img, int x, int y, ImageObserver o) {
        graphics.drawImage(img, x, y, o);
    }

    public void drawPolygon(Color col, Polygon p) {
        setColour(col);
        drawPolygon(p);
    }

    public void drawPolygon(Polygon p) {
        Vec2 prevVert = p.getOrientedVert(p.vCount - 1);  // store lastV to avoid re-calculation

        for (int i = 0; i < p.vCount; i++) {
            Vec2 vert = p.getOrientedVert(i);
            drawLine(vert, prevVert);
            prevVert = vert;
        }
    }

    public void setColour(Color col) {
        graphics.setColor(col);
    }

    public Color getColour() {
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
