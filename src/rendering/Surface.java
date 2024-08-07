package src.rendering;

import src.utility.Vec2;

import javax.swing.*;
import java.awt.*;

public class Surface extends SurfaceGraphics {
    private final JPanel panel = new JPanel() {
        @Override
        public Dimension getPreferredSize() {
            if (super.isPreferredSizeSet()) {
                return super.getPreferredSize();
            }
            return size;
        }
    };

    private boolean opaqueBg = false;
    public Vec2 pos = new Vec2();
    public Dimension size;

    public Surface(Dimension size) {
        super(size);
        this.size = size;
    }

    public Surface(Dimension size, Vec2 pos) {
        super(size);
        this.size = size;
        this.pos = pos;
    }

    public void init() {
        if (!initialised) {
            panel.setLayout(new BorderLayout());
            panel.setBounds((int) pos.x, (int) pos.y, size.width, size.height);
            panel.setOpaque(opaqueBg);

            init(panel.getGraphics());
        }
    }

    public void blit(CanvasSurface canvas) {
        blit(canvas.buffImg, new Vec2());
    }

    public void blit(CanvasSurface canvas, Vec2 pos) {
        blit(canvas.buffImg, pos);
    }

    public void blit(Image img) {
        blit(img, new Vec2());
    }

    public void blit(Image img, Vec2 pos) {
        drawImage(img, (int) pos.x, (int) pos.y, this.panel);
    }

    public void blitScaled(CanvasSurface canvas, int scale) {
        blitScaled(canvas, scale, new Vec2());
    }

    public void blitScaled(CanvasSurface canvas, int scale, Vec2 pos) {
        Image img = canvas.getScaledImg(canvas.size.width * scale, canvas.size.height * scale);
        blit(img, pos);
    }

    public void setOpaqueBg(boolean isOpaque) {
        if (initialised) {
            throw new InternalError("ERROR: Cannot set opaque if surface is already initialised");
        }
        opaqueBg = isOpaque;
    }

    public JPanel getRawPanel() {
        return panel;
    }

    public String toString() {
        return String.format("Surface(pos=%s, size=%s, initialised=%s)", pos, size, initialised);
    }
}
