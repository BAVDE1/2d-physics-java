package src.game.collisions;

import src.game.objects.Body;
import src.game.objects.Circle;
import src.utility.MathUtils;
import src.utility.Vec2;

public class CircleCircle implements Collision {
    public boolean handleCollision(Manifold m, Body a, Body b) {
        Circle c1 = (Circle) a;
        Circle c2 = (Circle) b;

        Vec2 vec = c2.pos.sub(c1.pos);
        double dist = vec.length();
        m.cCount = 1;

        if (dist == 0) {  // same pos
            m.normal.set(0, 1);  // go wherever
            m.penetration = c1.getRadius();
            m.cPoints[0] = c1.pos.getClone();
        } else {
            m.normal = vec.div(dist);
            m.penetration = (c1.getRadius() + c2.getRadius()) - dist;
            m.cPoints[0] = m.normal.mul(c1.getRadius()).add(c1.pos);
        }
        return true;
    }
}
