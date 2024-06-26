package src.game.collisions;

import src.game.Circle;
import src.game.Manifold;
import src.utility.Vec2;

public class CircleCircle {
    public static boolean CircleToCircle(Manifold m, Circle c1, Circle c2) {
        Vec2 normal = c2.pos.sub(c1.pos);
        double radius = c1.getRadius() + c2.getRadius();

        // not colliding, ignore
        if (normal.lengthSq() >= radius * radius) {
            return false;
        }

        double dist = normal.length();
        m.cCount = 1;

        if (dist == 0) {  // same pos
            m.normal.set(1, 0);
            m.penetration = c1.getRadius();
            m.cPoints[0] = c1.pos.getClone();
        } else {
            m.normal = normal.div(dist);
            m.penetration = radius - dist;
            m.cPoints[0] = m.normal.mul(c1.getRadius()).add(c1.pos);
        }
        return true;
    }
}
