package src.rendering;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CanvasSurface extends SurfaceGraphics {
    public BufferedImage buffImg;
    public Dimension size;

    public CanvasSurface(Dimension size) {
        super(size);
        this.buffImg = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        this.size = size;
        init();
    }

    public void init() {
        if (!initialised) {
            init(buffImg.getGraphics());
        }
    }

    public Image getScaledImg(int width, int height) {
        return buffImg.getScaledInstance(width, height, BufferedImage.SCALE_DEFAULT);
    }

    @Override
    public String toString() {
        return String.format("CanvasSurface[size=%s, initialised=%s]", size, initialised);
    }
}
