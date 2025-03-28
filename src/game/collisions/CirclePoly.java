package src.game.collisions;

import src.game.objects.Body;
import src.game.objects.Circle;
import src.game.objects.Polygon;
import src.game.Constants;
import src.utility.MathUtils;
import src.utility.Vec2;

public class CirclePoly implements Collision {
    public boolean handleCollision(Manifold m, Body a, Body b) {
        Circle c = (Circle) a;
        Polygon p = (Polygon) b;

        Vec2 centre = p.mat2.transpose().mul(c.pos.sub(p.pos));  // circle center into polygon model space

        // find the best separation within radius
        int vInx = 0;
        double separation = -Double.MAX_VALUE;
        for (int i = 0; i < p.vCount; i++) {
            double s = p.normals.get(i).dot(centre.sub(p.getVert(i)));
            if (s > c.getRadius()) return false;  // too far away, skip collision
            if (s > separation) {
                separation = s;
                vInx = i;
            }
        }

        Vec2 v1 = p.getVert(vInx);
        Vec2 v2 = p.getVert(vInx + 1);

        // centre within poly
        if (separation < Constants.EPSILON) {
            m.cCount = 1;
            m.normal = p.mat2.mul(p.normals.get(vInx)).negate();
            m.cPoints[0] = (m.normal.mul(c.getRadius())).add(c.pos);
            m.penetration = c.getRadius();
            return true;
        }

        // Determine which voronoi region of the edge center of circle lies within
        double dot1 = (centre.sub(v1)).dot(v2.sub(v1));
        double dot2 = (centre.sub(v2)).dot(v1.sub(v2));
        m.penetration = c.getRadius() - separation;

        // get vertex furthest within c, or None if face should be used
        Vec2 v = dot1 <= 0 ? v1 : (dot2 <= 0 ? v2 : null);
        if (v != null) {
            if (centre.sub(v).lengthSq() > Math.pow(c.getRadius(), 2)) return false;

            m.normal = p.mat2.mul(v.sub(centre)).normaliseSelf();
            m.cPoints[0] = p.mat2.mul(v).add(p.pos);
        } else {  // face closest
            Vec2 n = p.normals.get(vInx);
            if ((centre.sub(v1)).dot(n) > c.getRadius()) return false;

            m.normal = p.mat2.mul(n).negate();
            m.cPoints[0] = c.pos.add(m.normal.mul(c.getRadius()));
        }

        m.cCount = 1;
        return true;
    }
}
