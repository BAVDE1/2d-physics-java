package src.utility;

public class Mat2 {
    private double m00 = 0.0;
    private double m01 = 0.0;
    private double m10 = 0.0;
    private double m11 = 0.0;

    private double radians;

    public Mat2() {
        setRad(0);
    }

    public Mat2(double radians) {
        setRad(radians);
    }

    public void setRad(double radians) {
        this.radians = radians;
        refresh();
    }

    public void addRad(double radians) {
        setRad(this.radians + radians);
    }

    private void refresh() {
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);

        m00 = cos;
        m01 = -sin;
        m10 = sin;
        m11 = cos;
    }

    public Mat2 getClone() {
        return new Mat2(radians);
    }

    public Mat2 abs() {
        Mat2 out = getClone();
        out.m00 = Math.abs(m00);
        out.m01 = Math.abs(m01);
        out.m10 = Math.abs(m10);
        out.m11 = Math.abs(m11);
        return out;
    }

    public Vec2 mul(Vec2 vec) {
        return new Vec2(
                (m00 * vec.x) + (m01 * vec.y),
                (m10 * vec.x) + (m11 * vec.y)
        );
    }

    public Mat2 mul(Mat2 other) {
        return Mat2.mul(this, other);
    }

    public static Mat2 mul(Mat2 m1, Mat2 m2) {
        Mat2 out = new Mat2();
        out.m00 = (m1.m00 * m2.m00) + (m1.m01 * m2.m10);
        out.m01 = (m1.m00 * m2.m01) + (m1.m01 * m2.m11);
        out.m10 = (m1.m10 * m2.m00) + (m1.m11 * m2.m10);
        out.m11 = (m1.m10 * m2.m01) + (m1.m11 * m2.m11);
        return out;
    }

    public Mat2 transpose() {
        Mat2 out = getClone();
        out.m01 = m10;
        out.m10 = m01;
        return out;
    }
}
