package src.game;

import src.rendering.Surface;
import src.utility.Vec2;

public class Circle extends Body {
    private double radius;

    public Circle(Vec2 pos, double radius) {
        super(pos);
        this.radius = radius;

        computeMass();
    }

    @Override
    public void computeMass() {
        double mass = Math.PI * radius * radius * density;
    }

    @Override
    public double getRadius() {
        return radius;
    }

    @Override
    public boolean isPointIn() {
        return false;
    }

    @Override
    public void render(Surface surface) {

    }

    public void setOrient() {}
}
