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
        init();
    }

    public CanvasSurface(Dimension size, boolean noInit) {
        super(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        this.size = size;

        if (!noInit) {init();}
    }

    public void init() {
        if (!initialised) {
            initialised = true;
            graphics = new SurfaceGraphics(getGraphics(), size);
        }
    }

    public void unInit() {
        if (initialised) {
            initialised = false;
            graphics.dispose();
        }
    }

    @Override
    public String toString() {
        return String.format("CanvasSurface[size=%s, initialised=%s]", size, initialised);
    }
}
