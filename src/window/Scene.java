package src.window;

import src.game.collisions.Manifold;
import src.game.objects.Body;
import src.game.Constants;
import src.utility.Group;

import java.util.ArrayList;

public class Scene {
    public final Group<Body> objectsGroup = new Group<>();
    private final ArrayList<Manifold> collisions = new ArrayList<>();

    /** Iterate over all objects given and check if they're colliding.
     * If so, fill manifold values & add it to collision list */
    private void initCollisions() {
        for (int ia = 0; ia < objectsGroup.objects.size(); ia++) {
            Body a = objectsGroup.objects.get(ia);
            for (int ib = ia + 1; ib < objectsGroup.objects.size(); ib ++) {  // prevent duplicate checks
                Body b = objectsGroup.objects.get(ib);
                if (a.shouldIgnoreCollision(b)) {
                    continue;
                }

                Manifold man = new Manifold(a, b);
                man.solveCollision();

                if (man.cCount > 0) {
                    collisions.add(man);
                }
            }
        }
    }

    /** Updates all objects in scene group */
    public void update(double dt) {
        collisions.clear();
        initCollisions();

        // apply rest of velocity from last frame
        for (Body obj : objectsGroup.objects) {
            obj.updateVelocity(dt);
        }

        // resolve collisions, apply impulses
        for (int iteration = 1; iteration < Constants.RESOLVE_ITERATIONS; iteration++) {
            for (Manifold man : collisions) {
                man.resolveCollision();
            }
        }

        // apply velocity & conclude movement
        for (int i = 0; i < objectsGroup.objects.size(); i++) {
            Body obj = objectsGroup.objects.get(i);
            obj.update(dt);

            if (obj.isOutOfBounds()) {
                objectsGroup.removeAtInx(i);
            }

            obj.force.set(0, 0);
            obj.torque = 0;
        }

        // correct positions
        for (Manifold man : collisions) {
            man.positionalCorrection();
        }
    }

    public void render(CanvasSurface cSurface) {
        for (Body obj : objectsGroup.objects) {
            obj.render(cSurface);
        }

        for (Manifold man : collisions) {
            man.render(cSurface);
        }
    }

    @Override
    public String toString() {
        return String.format("Scene(%s, collisions=%s)", objectsGroup, collisions.size());
    }
}
