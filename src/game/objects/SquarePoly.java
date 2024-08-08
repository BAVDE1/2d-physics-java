package src.game.objects;

import src.utility.Vec2;

import java.awt.*;
import java.util.ArrayList;

public class SquarePoly extends Polygon {
    public Dimension size;

    public SquarePoly(Vec2 pos, Dimension size) {
        this(pos, false, size);
    }

    public SquarePoly(Vec2 pos, boolean isStatic, Dimension size) {
        super(pos);
        this.size = size;
        this.isStatic = isStatic;

        init(generateVertices());
    }

    private ArrayList<Vec2> generateVertices() {
        ArrayList<Vec2> arr = new ArrayList<>(4);
        Vec2 s = Vec2.fromDim(size);
        arr.add(new Vec2());
        arr.add(s.mul(new Vec2(1, 0)));
        arr.add(s.mul(new Vec2(1, 1)));
        arr.add(s.mul(new Vec2(0, 1)));
        return arr;
    }
}
