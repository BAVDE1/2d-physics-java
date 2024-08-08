package src.game.collisions;

import src.game.objects.Body;

public interface Collision {
    Collision[][] collide = {
            {new CircleCircle(), new CirclePoly()},
            {new PolyCircle(), new PolyPoly()}
    };

    boolean handleCollision(Manifold m, Body a, Body b);
}
