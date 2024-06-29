package src.game.collisions;

import src.game.Body;
import src.game.Manifold;

public interface Collision {
    Collision[][] collide = {
            {CircleCircle.instance, CirclePoly.instance},
            {PolyCircle.instance, PolyPoly.instance}
    };

    boolean handleCollision(Manifold m, Body a, Body b);
}
