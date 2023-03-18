package RTNextWeek;

import static RTOneWeekend.RTUtil.random_double;

public final class Point extends Vec3 {
    public Point(double s){
        super(s);
    }

    public Point(double e0, double e1, double e2) {
        super(e0, e1, e2);
    }

    public Point(double[] array){
        super(array[0], array[1], array[2]);
    }

    public Point add(Vec3 v){
        return new Point(x+v.x, y+v.y, z+v.z);
    }

    public Point add(double s){
        return new Point(x+s, y+s, z+s);
    }

    public Point minus(Vec3 v){
        return new Point(x-v.x, y-v.y, z-v.z);
    }

    public Point minus(double s){
        return new Point(x-s, y-s, z-s);
    }

    public Point multiply(Vec3 v){
        return new Point(x*v.x, y*v.y, z*v.z);
    }

    public Point multiply(double s){
        return new Point(x*s, y*s, z*s);
    }

    public Point divide(Vec3 v){
        return new Point(x/v.x, y/v.y, z/v.z);
    }

    public Point divide(double s){
        return new Point(x/s, y/s, z/s);
    }

    public static Point random()
    {
        return new Point(random_double(), random_double(), random_double());
    }

    public static Point random(double min, double max)
    {
        return new Point(random_double(min,max),
                random_double(min,max),
                random_double(min,max));
    }
}
