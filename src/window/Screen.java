package src.window;

import src.game.Constants;
import src.game.Game;
import src.utility.Vec2;

import javax.swing.*;
import java.awt.*;

public class Screen {
    public boolean initialised = false;
    private final Game parent;
    private final JPanel panel = new JPanel() {
        @Override
        public Dimension getPreferredSize() {
            if (super.isPreferredSizeSet()) {
                return super.getPreferredSize();
            }
            return size;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            parent.renderScreen(g);
        }
    };

    private boolean opaqueBg = false;
    public Dimension size;

    public Screen(Game parent, Dimension size) {
        this.size = size;
        this.parent = parent;
    }

    public void init() {
        if (!initialised) {
            panel.setLayout(new BorderLayout());
            panel.setBounds(0, 0, size.width, size.height);
            panel.setOpaque(opaqueBg);

            initialised = true;
        }
    }

    public void unInit() {
        initialised = false;
    }

    public void blit(Graphics g, CanvasSurface canvas) {
        blit(g, canvas.buffImg, new Vec2());
    }

    public void blit(Graphics g, CanvasSurface canvas, Vec2 pos) {
        blit(g, canvas.buffImg, pos);
    }

    public void blit(Graphics g, Image img) {
        blit(g, img, new Vec2());
    }

    public void blit(Graphics g, Image img, Vec2 pos) {
        g.drawImage(img, (int) pos.x, (int) pos.y, this.panel);
    }

    public void blitScaled(Graphics g, CanvasSurface canvas) {
        blitScaled(g, canvas, Constants.RES_MUL, new Vec2());
    }

    public void blitScaled(Graphics g, CanvasSurface canvas, int scale) {
        blitScaled(g, canvas, scale, new Vec2());
    }

    public void blitScaled(Graphics g, CanvasSurface canvas, int scale, Vec2 pos) {
        Image img = canvas.getScaledImg(canvas.size.width * scale, canvas.size.height * scale);
        blit(g, img, pos);
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
        return String.format("Surface(size=%s, initialised=%s)", size, initialised);
    }
}
