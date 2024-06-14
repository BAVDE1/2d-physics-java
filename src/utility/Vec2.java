package src.utility;

public class Vec2 {
    public double x;
    public double y;

    public Vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void addSelf(Vec2 vec) {
        x += vec.x;
        y += vec.y;
    }

    public Vec2 add(double d) {
        return add(this, d);
    }

    public Vec2 add(Vec2 vec) {
        return add(this, vec);
    }

    public static Vec2 add(Vec2 vec, double d) {
        return new Vec2(vec.x + d, vec.y + d);
    }

    public static Vec2 add(Vec2 vec1, Vec2 vec2) {
        return new Vec2(vec1.x + vec2.x, vec1.y + vec2.y);
    }

    public void subSelf(Vec2 vec) {
        x -= vec.x;
        y -= vec.y;
    }

    public Vec2 sub(double d) {
        return sub(this, d);
    }

    public Vec2 sub(Vec2 vec) {
        return sub(this, vec);
    }

    public static Vec2 sub(Vec2 vec, double d) {
        return new Vec2(vec.x - d, vec.y - d);
    }

    public static Vec2 sub(Vec2 vec1, Vec2 vec2) {
        return new Vec2(vec1.x - vec2.x, vec1.y - vec2.y);
    }

    public void divSelf(Vec2 vec) {
        x /= vec.x;
        y /= vec.y;
    }

    public Vec2 div(double d) {
        return div(this, d);
    }

    public Vec2 div(Vec2 vec) {
        return div(this, vec);
    }

    public static Vec2 div(Vec2 vec, double d) {
        return new Vec2(vec.x / d, vec.y / d);
    }

    public static Vec2 div(Vec2 vec1, Vec2 vec2) {
        return new Vec2(vec1.x / vec2.x, vec1.y / vec2.y);
    }

    public void mulSelf(Vec2 vec) {
        x *= vec.x;
        y *= vec.y;
    }

    public Vec2 mul(double d) {
        return mul(this, d);
    }

    public Vec2 mul(Vec2 vec) {
        return mul(this, vec);
    }

    public static Vec2 mul(Vec2 vec, double d) {
        return new Vec2(vec.x * d, vec.y * d);
    }

    public static Vec2 mul(Vec2 vec1, Vec2 vec2) {
        return new Vec2(vec1.x * vec2.x, vec1.y * vec2.y);
    }
}
