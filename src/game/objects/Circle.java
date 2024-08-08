package src.game.objects;

import src.window.CanvasSurface;
import src.game.Constants;
import src.utility.Vec2;

public class Circle extends Body {
    private final int radius;

    public Circle(Vec2 pos, int radius) {
        this(pos, false, radius);
    }

    public Circle(Vec2 pos, boolean isStatic, int radius) {
        super(pos);
        this.radius = radius;
        this.isStatic = isStatic;

        computeMass();  // do last
    }

    @Override
    public Type getType() {
        return Type.Circle;
    }

    @Override
    public void computeMass() {
        double m = Math.PI * radius * radius * density;
        mass = isStatic ? Constants.INF_MASS : m;
        invMass = isStatic ? 0 : 1 / m;

        inertia = m * radius * radius;
        invInertia = isStatic ? 0 : 1 / inertia;
    }

    @Override
    public double getRadius() {
        return radius;
    }

    @Override
    public boolean isPointIn(Vec2 p) {
        double dist = p.sub(pos).length();
        return dist < radius;
    }

    @Override
    public void render(CanvasSurface cSurface) {
        Vec2 rot = new Vec2(Math.cos(orientation) * radius, Math.sin(orientation) * radius);

        cSurface.drawCircle(colour, pos, radius);
        cSurface.drawLine(colour, pos, pos.add(rot));
    }

    public void setOrient() {}
}
