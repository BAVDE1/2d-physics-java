package src.game.collisions;

import src.game.Body;
import src.game.Circle;
import src.game.Manifold;
import src.game.Polygon;

public class PolyCircle implements Collision {
    public static final PolyCircle instance = new PolyCircle();

    public boolean handleCollision(Manifold m, Body a, Body b) {
        boolean value = CirclePoly.instance.handleCollision(m, b, a);
        m.normal.negateSelf();
        return value;
    }
}
