package RTExtended;

import static RTExtended.RTUtil.pi;
import static java.lang.Math.*;
import static java.lang.Math.sin;

public record Vec2(double x, double y) {
    public Vec2 add(Vec2 v){
        return new Vec2(x+v.x, y+v.y);
    }

    public Vec2 add(double s){
        return new Vec2(x+s, y+s);
    }

    public Vec2 minus(Vec2 v){
        return new Vec2(x-v.x, y-v.y);
    }

    public Vec2 minus(double s){
        return new Vec2(x-s, y-s);
    }

    public Vec2 multiply(Vec2 v){
        return new Vec2(x*v.x, y*v.y);
    }

    public Vec2 multiply(double s){
        return new Vec2(x*s, y*s);
    }

    public Vec2 divide(Vec2 v){
        return new Vec2(x/v.x, y/v.y);
    }

    public Vec2 divide(double s){
        return new Vec2(x/s, y/s);
    }

    public double dot(Vec2 v) {
        return x * v.x + y * v.y;
    }

    public Vec2 cross(Vec2 v) {
        return new Vec2(x*v.y - v.x*y,
                        y*v.x - v.y*x);
    }

    public static Vec2 randomGaussian()
    {
        double u1    = max(1e-38, random());
        double u2    = random();  // In [0, 1]
        double r     = sqrt(-2.0 * log(u1));
        double theta = 2 * pi * u2;  // Random in [0, 2pi]
        return new Vec2(cos(theta), sin(theta)).multiply(r);
    }
}
