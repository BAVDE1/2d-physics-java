package src.utility;

import src.game.Body;

import java.util.concurrent.ThreadLocalRandom;

public class MathUtils {
    public static class IntClass {public int value;}
    public static class DoubleClass {public double value;}

    public static boolean tooFarToCollide(Body b1, Body b2) {
        return tooFarToCollide(b2.pos.sub(b1.pos), b1.getRadius() + b2.getRadius());
    }

    public static boolean tooFarToCollide(Vec2 vec, double radius) {
        return vec.lengthSq() >= radius * radius;
    }

    public static int randInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static double roundUp(double value, int dPlaces) {
        int placesInt = (int) Math.pow(10, dPlaces);
        return Math.ceil(value * placesInt) / placesInt;
    }

    /** Greater than with bias */
    public static boolean greaterThan(double a, double b) {
        return a >= (b * Constants.BIAS_RELATIVE) + (a * Constants.BIAS_ABSOLUTE);
    }

    public static boolean doLinesCross(Vec2 aFrom, Vec2 aTo, Vec2 bFrom, Vec2 bTo) {
        interface Func {int call(Vec2 a, Vec2 b, Vec2 c);}

        Func onSegment = (a, b, c) ->
                ((b.x <= Math.max(a.x, c.x)) && (b.x >= Math.min(a.x, c.x)) &&
                        (b.y <= Math.max(a.y, c.y)) && (b.y >= Math.min(a.y, c.y))) ? 1 : 0;

        Func getOrient = (a, b, c) -> {
            double orient = ((b.y - a.y) * (c.x - b.x)) - ((b.x - a.x) * (c.y - b.y));
            return orient > 0 ? 1 : (orient < 0 ? 2 : 0);  // 0 = collinear, 1 = clockwise, 2 = counter
        };

        int o1 = getOrient.call(bFrom, bTo, aFrom);
        int o2 = getOrient.call(bFrom, bTo, aTo);
        int o3 = getOrient.call(aFrom, aTo, bFrom);
        int o4 = getOrient.call(aFrom, aTo, bTo);

        // normal case (return early)
        if ((o1 != o2) && (o3 != o4)) {
            return true;
        }

        // collinear cases
        boolean cc_1 = (o1 == 0) && onSegment.call(bFrom, aFrom, bTo) == 1;
        boolean cc_2 = (o2 == 0) && onSegment.call(bFrom, aTo, bTo) == 1;
        boolean cc_3 = (o3 == 0) && onSegment.call(aFrom, bFrom, aTo) == 1;
        boolean cc_4 = (o4 == 0) && onSegment.call(aFrom, bTo, aTo) == 1;

        return cc_1 || cc_2 || cc_3 || cc_4;
    }
}
