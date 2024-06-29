package src.game.collisions;

import src.game.Body;
import src.game.Circle;
import src.game.Manifold;
import src.utility.MathUtils;
import src.utility.Vec2;

public class CircleCircle implements Collision {
    public static final CircleCircle instance = new CircleCircle();

    public boolean handleCollision(Manifold m, Body a, Body b) {
        Circle c1 = (Circle) a;
        Circle c2 = (Circle) b;

        // not close enough to collide, ignore
        Vec2 vec = c2.pos.sub(c1.pos);
        double radius = c1.getRadius() + c2.getRadius();
        if (MathUtils.tooFarToCollide(vec, radius)) {
            return false;
        }

        double dist = vec.length();
        m.cCount = 1;

        if (dist == 0) {  // same pos
            m.normal.set(1, 0);
            m.penetration = c1.getRadius();
            m.cPoints[0] = c1.pos.getClone();
        } else {
            m.normal = vec.div(dist);
            m.penetration = radius - dist;
            m.cPoints[0] = m.normal.mul(c1.getRadius()).add(c1.pos);
        }
        return true;
    }
}
