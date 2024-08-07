package src.game;

import src.rendering.CanvasSurface;
import src.utility.Mat2;
import src.utility.MathUtils;
import src.utility.Vec2;

import java.awt.*;
import java.util.ArrayList;

public class Polygon extends Body {
    public int vCount;
    public ArrayList<Vec2> vertices;
    public ArrayList<Vec2> normals = new ArrayList<>();
    public Mat2 mat2 = new Mat2(orientation);
    private double radiusOuter;
    private double radiusInner;

    Polygon(Vec2 pos) {
        super(pos);
    }

    public Polygon(Vec2 pos, ArrayList<Vec2> vertices) {
        this(pos, false, vertices);
    }

    public Polygon(Vec2 pos, boolean isStatic, ArrayList<Vec2> vertices) {
        super(pos);
        this.isStatic = isStatic;

        init(vertices);
    }

    void init(ArrayList<Vec2> vertices) {
        this.vertices = vertices;
        vCount = vertices.size();

        for (int i = 0; i < vCount; i++) {
            Vec2 face = getVert(i + 1).sub(vertices.get(i));
            Vec2 normal = face.cross();
            normals.add(normal.normaliseSelf());
        }

        computeMass();
        computeRadii();  // after mass
    }

    @Override
    public void computeMass() {
        Vec2 com = new Vec2();
        double area = 0.0;
        double tempInertia = 0.0;
        double inv3 = (double) 1 / 3;

        for (int i = 0; i < vCount; i++) {
            Vec2 v1 = getVert(i);
            Vec2 v2 = getVert(i + 1);

            double areaSq = v1.cross(v2);
            double areaTri = areaSq * 0.5;
            area += areaTri;

            double weight = areaTri * inv3;
            com.addSelf(v1.mul(weight));
            com.addSelf(v2.mul(weight));

            double x2 = Math.pow(v1.x, 2) + (v2.x * v1.x) + Math.pow(v2.x, 2);
            double y2 = Math.pow(v1.y, 2) + (v2.y * v1.y) + Math.pow(v2.y, 2);
            tempInertia += (.25 * inv3 * areaSq) * (x2 + y2);
        }

        com.mulSelf(1 / area);
        pos = ogPos.add(com);  // move pos to com

        // translate vertices to new pos (com)
        for (int i = 0; i < vCount; i++) {
            getVert(i).subSelf(com);
        }

        mass = Math.abs(density * area);
        invMass = isStatic ? 0 : 1 / mass;
        inertia = Math.abs(density * tempInertia);
        invInertia = isStatic ? 0 : 1 / tempInertia;
    }

    private void computeRadii() {
        double innerR = Double.MAX_VALUE;
        double outerR = Double.MIN_VALUE;

        for (Vec2 vert : vertices) {
            double dist = MathUtils.roundUp((pos.sub(pos.add(vert))).length(), 2);
            innerR = Math.min(dist, innerR);
            outerR = Math.max(dist, outerR);
        }
        radiusInner = innerR;
        radiusOuter = outerR;
    }

    public Vec2 getOrientedVert(int i) {
        return mat2.mul(getVert(i)).add(pos);
    }

    public Vec2 getVert(int i) {
        return vertices.get(i % vCount);
    }

    @Override
    public Type getType() {
        return Type.Polygon;
    }

    @Override
    public void setOrient() {
        mat2.setRad(orientation);
    }

    @Override
    public double getRadius() {
        return radiusOuter;
    }

    /** if intersections is odd, point is within poly. Assumes poly does not loop in on itself */
    @Override
    public boolean isPointIn(Vec2 p1) {
        int intersections = 0;
        Vec2 p2 = new Vec2(0, p1.y);

        for (int i = 0; i < vCount; i++) {
            Vec2 v1 = getOrientedVert(i);
            Vec2 v2 = getOrientedVert(i + 1);

            intersections += MathUtils.doLinesCross(p1, p2, v1, v2) ? 1 : 0;
        }
        return (intersections % 2) != 0;
    }

    /** Find objects furthest support point (vertex) along given direction */
    public Vec2 getSupport(Vec2 direction) {
        double bestProjection = Double.MIN_VALUE;
        Vec2 bestVert = new Vec2();

        for (Vec2 vert : vertices) {
            double proj = vert.dot(direction);

            if (proj > bestProjection) {
                bestProjection = proj;
                bestVert = vert;
            }
        }
        return bestVert;
    }

    @Override
    public void render(CanvasSurface cSurface) {
        cSurface.drawRect(colour, pos, new Dimension(0, 0));  // com
        cSurface.drawPolygon(this);
    }
}
