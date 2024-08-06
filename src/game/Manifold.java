package src.game;

import src.game.collisions.Collision;
import src.rendering.CanvasSurface;
import src.utility.Constants;
import src.utility.Vec2;

import java.awt.*;

public class Manifold {
    private final Body a;
    private final Body b;

    public Vec2 normal;
    public double penetration;

    public int cCount;
    public Vec2[] cPoints = {new Vec2(), new Vec2()};

    public Manifold(Body a, Body b) {
        this.a = a;
        this.b = b;
    }

    public void solveCollision() {
        int ia = a.getType().ordinal();
        int ib = b.getType().ordinal();

        Collision.collide[ia][ib].handleCollision(this, a, b);
    }

    public Vec2 getRelativeVel(Vec2 relA, Vec2 relB) {
        return ((b.velocity.sub(relB.cross(b.angularVelocity))).sub(
                (a.velocity.sub(relA.cross(a.angularVelocity)))));
    }

    public void resolveCollision() {
        if (cCount == 0) {
            return;
        }

        for (int i = 0; i < cCount; i++) {
            // relative values
            Vec2 relA = cPoints[i].sub(a.pos);
            Vec2 relB = cPoints[i].sub(b.pos);
            Vec2 relVel = getRelativeVel(relA, relB);

            double contactVel = relVel.dot(normal);
            if (contactVel > 0) {  // separating, do not apply impulse
                return;
            }

            double raCrossN = relA.cross(normal);
            double rbCrossN = relB.cross(normal);
            double invMasses = a.invMass + b.invMass + (Math.pow(raCrossN, 2) * a.invInertia) + (Math.pow(rbCrossN, 2) * b.invInertia);

            // restitution & rebound
            boolean isResting = Math.pow(relVel.y, 2) <= Constants.RESTING;
            double restitution = Math.min(a.restitution, b.restitution);  // coefficient of restitution
            Vec2 restitutionVec = new Vec2(restitution, isResting ? 0.0 : restitution);  // fix jitter-ing objects
            Vec2 reboundVec = restitutionVec.add(1).negate();

            // impulse
            Vec2 impulseScalar = reboundVec.mul(contactVel);
            impulseScalar.divSelf(invMasses);
            impulseScalar.divSelf(cCount);

            Vec2 impulse = normal.mul(impulseScalar);
            a.applyImpulse(impulse.negate(), relA);
            b.applyImpulse(impulse, relB);

            // FRICTION IMPULSE
            relVel = getRelativeVel(relA, relB);  // re-calculate after applying main impulse
            Vec2 tan = relVel.add((normal.mul(-relVel.dot(normal))));  // tangent
            tan.normaliseSelf();

            double impulseTanScalar = -relVel.dot(tan);
            impulseTanScalar /= invMasses;
            impulseTanScalar /= cCount;

            if (impulseTanScalar != 0) {
                double sf = Math.sqrt(Math.pow(a.staticFriction, 2) + Math.pow(b.staticFriction, 2));
                Vec2 tanImpulse;

                // Coulumb 's law
                if (Math.abs(impulseTanScalar) < impulseScalar.x * sf) {  // assumed at rest
                    tanImpulse = tan.mul(impulseTanScalar);
                } else {  // already moving (energy of activation broken, less friction required)
                    double df = Math.sqrt(Math.pow(a.dynamicFriction, 2) + Math.pow(b.dynamicFriction, 2));
                    tanImpulse = (tan.mul(impulseScalar)).mul(-df);
                }

                a.applyImpulse(tanImpulse.negate(), relA);
                b.applyImpulse(tanImpulse, relB);
            }
        }
    }

    /** Fix floating point errors (using linear projection) and objects within one another */
    public void positionalCorrection() {
        double correction = Math.max(penetration - Constants.PENETRATION_ALLOWANCE, 0.0) / (a.invMass + b.invMass) * Constants.POSITIONAL_CORRECTION;

        a.pos.addSelf(normal.mul(-a.invMass * correction));
        b.pos.addSelf(normal.mul(b.invMass * correction));
    }

    public void render(CanvasSurface cSurface) {
        for (int i = 0; i < cCount; i++) {
            Vec2 cp = cPoints[i];  // contact point... it stands for contact point
            if (!cp.equals(new Vec2())) {
                cSurface.fillRect(Color.RED, cp, new Dimension(1, 1));  // point
                cSurface.drawLine(Color.YELLOW, cp, cp.add(normal.mul(2)));  // 2 pixel long line
            }
        }
    }
}
