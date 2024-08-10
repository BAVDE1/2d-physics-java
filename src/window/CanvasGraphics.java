package src.window;

import src.game.Constants;
import src.game.objects.Polygon;
import src.utility.Vec2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Objects;

public abstract class CanvasGraphics {
    public boolean initialised = false;
    private Graphics2D graphics;
    private final Dimension size;

    public CanvasGraphics(Dimension size) {
        this.size = size;
    }

    public void init(Graphics graphics, Color bgCol) {
        if (!initialised) {
            initialised = true;
            this.graphics = (Graphics2D) graphics;

            this.graphics.setFont(Constants.DEFAULT_FONT);
            this.graphics.setBackground(bgCol);
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

    /** Clears to the colour of the background */
    public void clear() {
        graphics.clearRect(0, 0, size.width, size.height);
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

    public void setFont(String family, int style, int size) {
        Font f = new Font(family, style, size);
        graphics.setFont(f);
    }

    public void setFontFamily(String family) {
        Font f = graphics.getFont();
        if (Objects.equals(f.getFamily(), family)) return;
        setFont(family, f.getStyle(), f.getSize());
    }

    public void setFontStyle(int style) {
        Font f = graphics.getFont();
        if (Objects.equals(f.getStyle(), style)) return;
        setFont(f.getFamily(), style, f.getSize());
    }

    public void setFontSize(int size) {
        Font f = graphics.getFont();
        if (Objects.equals(f.getSize(), size)) return;
        setFont(f.getFamily(), f.getStyle(), size);
    }

    public void drawText(Color col, Vec2 pos, int size, String str) {
        setFontSize(size);
        drawText(col, pos, str);
    }

    public void drawText(Color col, Vec2 pos, String str) {
        setColour(col);
        drawText(pos, str);
    }

    public void drawText(Vec2 pos, String str) {
        graphics.drawString(str, (int) pos.x, (int) pos.y + graphics.getFont().getSize());  // place at top left
    }

    public void setColour(Color col) {
        if (getColour() == col) return;
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
