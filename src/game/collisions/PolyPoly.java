package src.game.collisions;

import src.game.Manifold;
import src.game.Polygon;
import src.utility.MathUtils;
import src.utility.Vec2;

public class PolyPoly {
    public static boolean PolyToPoly(Manifold m, Polygon p1, Polygon p2) {
        // not close enough to collide, ignore
        if (MathUtils.tooFarToCollide(p1, p2)) {
            return false;
        }
        return false;
    }

    private static void findAxisPenetration(Polygon p1, Polygon p2) {

    }

    private static void findIncidentFaceVertices(Polygon pRef, Polygon pInc, int iRef) {

    }

    private static void clipFaces(Vec2 iFace1, Vec2 iFace2, Vec2 normal, double side) {

    }
}
