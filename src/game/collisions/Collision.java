package src.game.collisions;

import src.game.Body;
import src.game.Manifold;

public interface Collision {
    Collision[][] collide = {
            {new CircleCircle(), new CirclePoly()},
            {new PolyCircle(), new PolyPoly()}
    };

    boolean handleCollision(Manifold m, Body a, Body b);
}
