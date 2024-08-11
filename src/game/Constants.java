package src.game;

import src.utility.Vec2;

import java.awt.*;

public class Constants {
    public static final int FPS = 60;
    public static final double DT = 1 / (double) FPS;

    public static final double INF_MASS = -1;
    public static final double EPSILON = 0.0001;
    public static final double EPSILON_SQ = EPSILON * EPSILON;
    public static final double PENETRATION_ALLOWANCE = 0.05;  // slop
    public static final double POSITIONAL_CORRECTION = 0.5;  // 20% - 80%
    public static final double BIAS_RELATIVE = 0.95;
    public static final double BIAS_ABSOLUTE = 0.01;
    public static final int RESOLVE_ITERATIONS = 8;  // higher = more stable but less performant

    public static final int MAX_MOUSE_FORCE = 60;
    public static final Vec2 GRAVITY = new Vec2(0, 200);
    public static final double RESTING = GRAVITY.mul(DT).lengthSq() + EPSILON;

    public static final String WINDOW_NAME = "agagalalalalala";
    public static final int BASE_WIDTH = 500;  // minimum: 136
    public static final int BASE_HEIGHT = 300;
    public static final int RES_MUL = 2;

    public static final Dimension BASE_SIZE = new Dimension(BASE_WIDTH, BASE_HEIGHT);
    public static final Dimension SCALED_SIZE = new Dimension(BASE_WIDTH * RES_MUL, BASE_HEIGHT * RES_MUL);

    public static final Font DEFAULT_FONT = new Font(Font.DIALOG_INPUT, Font.PLAIN, 8 * RES_MUL);
    public static final boolean DEFAULT_STATIC = false;
    public static final int DEFAULT_LAYER = 10;

    public static final int MIN_VERTEX_COUNT = 3;
    public static final int MAX_VERTEX_COUNT = 32;

    public static final Color BG_COL = new Color(0, 10, 5);
    public static final Color TRANSPARENT = new Color(255, 255, 255, 0);
}
