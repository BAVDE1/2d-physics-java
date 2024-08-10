package src.window;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CanvasSurface extends CanvasGraphics {
    public BufferedImage buffImg;
    public Dimension size;

    public CanvasSurface(Dimension size, Color bgCol) {
        super(size);
        this.buffImg = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        this.size = size;
        init(bgCol);
    }

    public void init(Color bgCol) {
        if (!initialised) {
            init(buffImg.getGraphics(), bgCol);
        }
    }

    public Image getScaledImg(int width, int height) {
        return buffImg.getScaledInstance(width, height, BufferedImage.SCALE_DEFAULT);
    }

    @Override
    public String toString() {
        return String.format("CanvasSurface(size=%s, initialised=%s)", size, initialised);
    }
}
