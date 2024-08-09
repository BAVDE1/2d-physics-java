package src.game.collisions;

import src.game.objects.Body;

public class PolyCircle implements Collision {
    public boolean handleCollision(Manifold m, Body a, Body b) {
        boolean value = new CirclePoly().handleCollision(m, b, a);
        if (value) m.normal.negateSelf();
        return value;
    }
}
