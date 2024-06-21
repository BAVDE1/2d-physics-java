package src.render;

import src.utility.Vec2;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CanvasSurface extends BufferedImage {
    boolean initialised = false;

    public Dimension size;
    public SurfaceGraphics graphics;

    public CanvasSurface(Dimension size) {
        super(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        this.size = size;
    }

    public CanvasSurface(Dimension size, boolean autoInit) {
        super(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        this.size = size;

        if (autoInit) {init();}
    }

    public void init() {
        initialised = true;
        graphics = new SurfaceGraphics(getGraphics(), size);
    }

    public void unInit() {
        initialised = false;
        graphics.dispose();
    }

    @Override
    public String toString() {
        return String.format("CanvasSurface[size=%s, initialised=%s]", size, initialised);
    }
}
