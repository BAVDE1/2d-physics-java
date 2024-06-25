package src.utility;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class Constants {
    public static final int FPS = 10;
    public static final double DT = 1 / (double) FPS;

    public static final String WINDOW_NAME = "some window!!!";
    public static final int BASE_WIDTH = 500;  // minimum: 136
    public static final int BASE_HEIGHT = 300;
    public static final int RES_MUL = 1;

    public static final Dimension BASE_SIZE = new Dimension(BASE_WIDTH, BASE_HEIGHT);
    public static final Dimension SCALED_SIZE = new Dimension(BASE_WIDTH * RES_MUL, BASE_HEIGHT * RES_MUL);

    public static final Color BG_COL = new Color(0, 15, 15);

    public static int randInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
