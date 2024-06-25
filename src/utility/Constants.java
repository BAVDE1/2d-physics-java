package src.utility;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class Constants {
    public static final int FPS = 10;
    public static final double DT = 1 / (double) FPS;

    public static final Vec2 GRAVITY = new Vec2(0, 100);

    public static final double EPSILON = 0.0001;
    public static final double EPSILON_SQ = EPSILON * EPSILON;

    public static final String WINDOW_NAME = "some window!!!";
    public static final int BASE_WIDTH = 500;  // minimum: 136
    public static final int BASE_HEIGHT = 300;
    public static final int RES_MUL = 2;

    public static final Dimension BASE_SIZE = new Dimension(BASE_WIDTH, BASE_HEIGHT);
    public static final Dimension SCALED_SIZE = new Dimension(BASE_WIDTH * RES_MUL, BASE_HEIGHT * RES_MUL);

    public static final boolean DEFAULT_STATIC = false;
    public static final int DEFAULT_LAYER = 10;

    public static final Color BG_COL = new Color(0, 10, 5);

    public static int randInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
