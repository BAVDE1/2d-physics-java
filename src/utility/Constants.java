package src.utility;

import java.util.concurrent.ThreadLocalRandom;

public class Constants {
    public static final int FPS = 10;
    public static final double DT = 1 / (double) FPS;

    public static final String WINDOW_NAME = "some window!!!";
    public static final int BASE_WIDTH = 300;
    public static final int BASE_HEIGHT = 200;
    public static final int RES_MUL = 2;

    public static int randInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
