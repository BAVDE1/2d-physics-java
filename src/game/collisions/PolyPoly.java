package src.game.collisions;

import src.game.objects.Body;
import src.game.objects.Polygon;
import src.utility.Mat2;
import src.utility.MathUtils;
import src.utility.Vec2;

public class PolyPoly implements Collision {
    public boolean handleCollision(Manifold m, Body a, Body b) {
        Polygon p1 = (Polygon) a;
        Polygon p2 = (Polygon) b;

        MathUtils.IntClass intStorage = new MathUtils.IntClass();
        MathUtils.DoubleClass doubleStorage = new MathUtils.DoubleClass();

        // check for penetrating faces with both a and b polygons
        findAxisPenetration(p1, p2, intStorage, doubleStorage);
        int aFaceInx = intStorage.value;
        double aPen = doubleStorage.value;
        if (aPen > 0.0) return false;

        findAxisPenetration(p2, p1, intStorage, doubleStorage);
        int bFaceInx = intStorage.value;
        double bPen = doubleStorage.value;
        if (bPen > 0.0) return false;

        // polys are colliding, get collision values
        boolean flip = !MathUtils.greaterThan(aPen, bPen);  // always a to b

        Polygon rPoly = flip ? p2 : p1;  // reference
        Polygon iPoly = flip ? p1 : p2;  // incident
        int rInx = flip ? bFaceInx : aFaceInx;

        // get face vertices (in world space)
        Vec2 iV1 = new Vec2();
        Vec2 iV2 = new Vec2();
        findIncidentFaceVertices(rPoly, iPoly, rInx, iV1, iV2);
        Vec2 rV1 = rPoly.getOrientedVert(rInx);
        Vec2 rV2 = rPoly.getOrientedVert(rInx + 1);

        Vec2 sidePlaneNorm = (rV2.sub(rV1)).normaliseSelf();
        Vec2 rFaceNorm = new Vec2(sidePlaneNorm.y, -sidePlaneNorm.x);  // orthogonal

        // c distance from origin
        double rC = rFaceNorm.dot(rV1);
        double negSide = -sidePlaneNorm.dot(rV1);
        double posSide = sidePlaneNorm.dot(rV2);

        // Clip incident face to reference face side planes
        clipFaces(sidePlaneNorm.negate(), negSide, iV1, iV2, intStorage);  // intStorage holds clip amount
        if (intStorage.value < 2) return false;

        clipFaces(sidePlaneNorm, posSide, iV1, iV2, intStorage);
        if (intStorage.value < 2) return false;

        // flip
        m.normal = rFaceNorm.getClone();
        if (flip) m.normal.negateSelf();

        // Keep points behind reference face
        int cp = 0;  // clipped points behind reference face
        double separation = rFaceNorm.dot(iV1) - rC;
        if (separation <= 0) {
            m.cPoints[cp++] = iV1.getClone();
            m.penetration = -separation;
        }

        separation = rFaceNorm.dot(iV2) - rC;
        if (separation <= 0) {
            m.cPoints[cp++] = iV2.getClone();
            m.penetration += -separation;

            m.penetration /= cp;  //average
        }
        m.cCount = cp;
        return true;
    }

    private static void findAxisPenetration(Polygon p1, Polygon p2,
                                            MathUtils.IntClass returnedBestInx, MathUtils.DoubleClass returnedBestPen) {
        double bestPen = -Double.MAX_VALUE;
        int bestInx = 0;

        for (int i = 0; i < p1.vCount; i++) {
            Vec2 normal = p1.mat2.mul(p1.normals.get(i));

            // transform face normal into b's model space
            Mat2 p2Mat = p2.mat2.transpose();
            Vec2 p2OrientedNorm = p2Mat.mul(normal);

            Vec2 supportPoint = p2.getSupport(p2OrientedNorm.negate());

            // transform support vertex into b's model space
            Vec2 vert = (p1.getOrientedVert(i)).sub(p2.pos);
            vert = p2Mat.mul(vert);

            // distance of penetration
            double penetration = p2OrientedNorm.dot(supportPoint.sub(vert));

            // store biggest distance
            if (penetration > bestPen) {
                bestPen = penetration;
                bestInx = i;
            }
        }
        returnedBestPen.value = bestPen;
        returnedBestInx.value = bestInx;
    }

    private static void findIncidentFaceVertices(Polygon rPoly, Polygon iPoly, int rInx,
                                                 Vec2 returnedIncFace1, Vec2 returnedIncFace2) {
        Vec2 rNorm = rPoly.normals.get(rInx);

        // Calculate normal in incident's frame of reference
        rNorm = rPoly.mat2.mul(rNorm);  // world space
        rNorm = iPoly.mat2.transpose().mul(rNorm);  // inc model space

        // Find most anti-normal face on incident polygon
        int iFaceInx = 0;
        double minDot = Double.MAX_VALUE;
        for (int i = 0; i < iPoly.vCount; i++) {
            double dot = rNorm.dot(iPoly.normals.get(i));

            if (dot < minDot) {
                minDot = dot;
                iFaceInx = i;
            }
        }
        returnedIncFace1.set(iPoly.getOrientedVert(iFaceInx));
        returnedIncFace2.set(iPoly.getOrientedVert(iFaceInx + 1));
    }

    private static void clipFaces(Vec2 norm, double side, Vec2 iFace1, Vec2 iFace2,
                                  MathUtils.IntClass returnedClipNum) {
        int clipNum = 0;
        Vec2[] faces = {iFace1.getClone(), iFace2.getClone()};

        // Retrieve distances from each endpoint to the line
        double dist1 = norm.dot(iFace1) - side;
        double dist2 = norm.dot(iFace2) - side;

        // If negative (behind plane) clip
        if (dist1 <= 0) faces[clipNum++] = iFace1.getClone();
        if (dist2 <= 0) faces[clipNum++] = iFace2.getClone();

        // If the points are on different sides of the plane
        if (dist1 * dist2 < 0) {
            double alpha = dist1 / (dist2 - dist1);
            faces[clipNum] = (iFace1.sub(iFace2)).mul(alpha);
            faces[clipNum++].addSelf(iFace1);
        }

        iFace1.set(faces[0]);
        iFace2.set(faces[1]);
        returnedClipNum.value = clipNum;
    }
}
