package RTExtended;

import static RTExtended.RTUtil.random_double;

public final class Color extends Vec3 {
    public Color(double s){
        super(s);
    }

    public Color(double e0, double e1, double e2) {
        super(e0, e1, e2);
    }

    public Color(Vec3 v){
        super(v.x, v.y, v.z);
    }

    public Color add(Vec3 v){
        return new Color(x+v.x, y+v.y, z+v.z);
    }

    public Color add(double s){
        return new Color(x+s, y+s, z+s);
    }

    public Color minus(Vec3 v){
        return new Color(x-v.x, y-v.y, z-v.z);
    }

    public Color minus(double s){
        return new Color(x-s, y-s, z-s);
    }

    public Color multiply(Vec3 v){
        return new Color(x*v.x, y*v.y, z*v.z);
    }

    public Color multiply(double s){
        return new Color(x*s, y*s, z*s);
    }

    public Color divide(Vec3 v){
        return new Color(x/v.x, y/v.y, z/v.z);
    }

    public Color divide(double s){
        return new Color(x/s, y/s, z/s);
    }

    public Color cross(Vec3 v) {
        return new Color(y * v.z - z * v.y,
                z * v.x - x * v.z,
                x * v.y - y * v.x);
    }

    public Color pow(Vec3 v){
        return new Color(Math.pow(x,v.x), Math.pow(y,v.y), Math.pow(z,v.z));
    }

    public Color pow(double d){
        return new Color(Math.pow(x,d), Math.pow(y,d), Math.pow(z,d));
    }

    public static Color abs(Vec3 v){
        return new Color(Math.abs(v.x), Math.abs(v.y), Math.abs(v.z));
    }

    public static Color Exp(Vec3 v) {
        return new Color(Math.exp(v.x), Math.exp(v.y), Math.exp(v.z));
    }

    public static Color random()
    {
        return new Color(random_double(), random_double(), random_double());
    }

    public static Color random(double min, double max)
    {
        return new Color(random_double(min,max),
                random_double(min,max),
                random_double(min,max));
    }
}
