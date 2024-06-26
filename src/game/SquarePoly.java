package src.game;

import src.utility.Vec2;

import java.awt.*;
import java.util.ArrayList;

public class SquarePoly extends Polygon {
    public Dimension size;

    public SquarePoly(Vec2 pos, Dimension size) {
        super(pos);
        this.size = size;

        init(generateVertices());
    }

    private ArrayList<Vec2> generateVertices() {
        ArrayList<Vec2> arr = new ArrayList<>(4);
        Vec2 s = Vec2.fromDim(size);
        arr.add(s);
        arr.add(s.mul(new Vec2(1, 0)));
        arr.add(s.mul(new Vec2(1, 1)));
        arr.add(s.mul(new Vec2(0, 1)));
        return arr;
    }
}
