package src.game;

import src.utility.Vec2;

public class Manifold {
    private Body a;
    private Body b;

    public Vec2 normal;
    public double penetration;

    public int cCount;
    public Vec2[] cPoints;
}
