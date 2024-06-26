package src.utility;

import java.awt.*;
import java.lang.foreign.ValueLayout;
import java.util.concurrent.ThreadLocalRandom;

public class Constants {
    public static final int FPS = 10;
    public static final double DT = 1 / (double) FPS;

    public static final Vec2 GRAVITY = new Vec2(0, 100);

    public static final double INF_MASS = -1;
    public static final double EPSILON = 0.0001;
    public static final double EPSILON_SQ = EPSILON * EPSILON;

    public static final String WINDOW_NAME = "some window!!!";
    public static final int BASE_WIDTH = 500;  // minimum: 136
    public static final int BASE_HEIGHT = 300;
    public static final int RES_MUL = 1;

    public static final Dimension BASE_SIZE = new Dimension(BASE_WIDTH, BASE_HEIGHT);
    public static final Dimension SCALED_SIZE = new Dimension(BASE_WIDTH * RES_MUL, BASE_HEIGHT * RES_MUL);

    public static final boolean DEFAULT_STATIC = false;
    public static final int DEFAULT_LAYER = 10;

    public static final int MIN_VERTEX_COUNT = 3;
    public static final int MAX_VERTEX_COUNT = 32;

    public static final Color BG_COL = new Color(0, 10, 5);

    public static int randInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static boolean doLinesCross(Vec2 aFrom, Vec2 aTo, Vec2 bFrom, Vec2 bTo) {
        interface Func {int func(Vec2 a, Vec2 b, Vec2 c);}
        
        Func onSegment = (a, b, c) ->
                ((b.x <= Math.max(a.x, c.x)) && (b.x >= Math.min(a.x, c.x)) &&
                (b.y <= Math.max(a.y, c.y)) && (b.y >= Math.min(a.y, c.y))) ? 1 : 0;
        
        Func getOrient = (a, b, c) -> {
            double orient = ((b.y - a.y) * (c.x - b.x)) - ((b.x - a.x) * (c.y - b.y));
            return orient > 0 ? 1 : (orient < 0 ? 2 : 0);  // 0 = collinear, 1 = clockwise, 2 = counter
        };

        int o1 = getOrient.func(bFrom, bTo, aFrom);
        int o2 = getOrient.func(bFrom, bTo, aTo);
        int o3 = getOrient.func(aFrom, aTo, bFrom);
        int o4 = getOrient.func(aFrom, aTo, bTo);

        // normal case (return early)
        if ((o1 != o2) && (o3 != o4)) {
            return true;
        }

        // collinear cases
        boolean cc_1 = (o1 == 0) && onSegment.func(bFrom, aFrom, bTo) == 1;
        boolean cc_2 = (o2 == 0) && onSegment.func(bFrom, aTo, bTo) == 1;
        boolean cc_3 = (o3 == 0) && onSegment.func(aFrom, bFrom, aTo) == 1;
        boolean cc_4 = (o4 == 0) && onSegment.func(aFrom, bTo, aTo) == 1;

        return cc_1 || cc_2 || cc_3 || cc_4;
    }
}
