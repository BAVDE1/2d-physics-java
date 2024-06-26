package src.game;

import src.rendering.Surface;
import src.utility.Constants;
import src.utility.Vec2;

import java.awt.*;

public abstract class Body {
    public Vec2 pos;
    public final Vec2 ogPos;

    public boolean isStatic = Constants.DEFAULT_STATIC;
    public int layer = Constants.DEFAULT_LAYER;
    public Color colour = Color.WHITE;

    // linear
    public Vec2 velocity = new Vec2();
    public Vec2 force = new Vec2();
    public double staticFriction = 0.5;  // at rest
    public double dynamicFriction = 0.3;  // already moving

    // angular
    public double orientation = 0;
    public double angularVelocity = 0;
    public double torque = 0;

    // mass
    public double density = 1;
    public double restitution = 0.2;
    public double mass;
    public double invMass;
    public double inertia;
    public double invInertia;

    public Body(Vec2 pos) {
        this.pos = pos;
        this.ogPos = pos.getClone();
    }

    public abstract void computeMass();
    public abstract void setOrient();
    public abstract double getRadius();
    public abstract boolean isPointIn(Vec2 p);
    public abstract void render(Surface surface);

    public void applyForce(Vec2 force) {
        this.force.addSelf(force);
    }

    public void applyImpulse(Vec2 impulse, Vec2 contact) {
        if (!isStatic) {
            velocity.addSelf(impulse.mul(invMass));
            angularVelocity += contact.cross(impulse) * invInertia;
        }
    }

    public void updateVelocity(double dt) {
        if (!isStatic) {
            double dtH = dt * 0.5;

            velocity.addSelf(force.mul(dtH));
            velocity.addSelf(Constants.GRAVITY.mul(dtH));

            angularVelocity += torque * invInertia * dtH;
        }
    }

    public void update(double dt) {
        if (!isStatic) {
            pos.addSelf(velocity.mul(dt));
            orientation += angularVelocity * dt;
            setOrient();

            updateVelocity(dt);
        }
    }

    public boolean isOutOfBounds() {
        boolean below = pos.y > Constants.BASE_HEIGHT * 2;
        boolean left = pos.x < -Constants.BASE_WIDTH;
        boolean right = pos.x > Constants.BASE_WIDTH * 2;
        return below || left || right;
    }

    public boolean shouldIgnoreCollision(Body other) {
        boolean bothStatic = isStatic && other.isStatic;
        boolean diffLayer = layer != other.layer;
        return bothStatic || diffLayer;
    }

    @Override
    public String toString() {
        return String.format("Body[pos=%s, static=%s, layer=%s]", pos, isStatic, layer);
    }
}
