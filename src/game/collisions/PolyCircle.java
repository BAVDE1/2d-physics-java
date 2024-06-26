package src.game.collisions;

import src.game.Circle;
import src.game.Manifold;
import src.game.Polygon;

public class PolyCircle {
    public static boolean PolyToCircle(Manifold m, Polygon p, Circle c) {
        boolean value = CirclePoly.CircleToPoly(m, c, p);
        m.normal.negateSelf();
        return value;
    }
}
