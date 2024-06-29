package src.game;

import src.game.collisions.Collision;
import src.utility.Vec2;

public class Manifold {
    private final Body a;
    private final Body b;

    public Vec2 normal;
    public double penetration;

    public int cCount;
    public Vec2[] cPoints;

    public Manifold(Body a, Body b) {
        this.a = a;
        this.b = b;
    }

    public void solveCollision() {
        int ia = a.getType().ordinal();
        int ib = b.getType().ordinal();

        Collision.collide[ia][ib].handleCollision(this, a, b);
    }
}
