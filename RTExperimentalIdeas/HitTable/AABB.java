package RTExperimentalIdeas.HitTable;

import RTExperimentalIdeas.HitInfo.Interval;
import RTExperimentalIdeas.Point;
import RTExperimentalIdeas.Ray;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class AABB {
    Interval x, y, z;

    AABB(){
        x = new Interval(0,0);
        y = new Interval(0,0);
        z = new Interval(0,0);
    }
    AABB(Interval ix, Interval iy, Interval iz){
        x = ix; y = iy; z = iz;
    }
    AABB(Point a, Point b){
        x = new Interval(min(a.x, b.x), max(a.x, b.x));
        y = new Interval(min(a.y, b.y), max(a.y, b.y));
        z = new Interval(min(a.z, b.z), max(a.z, b.z));
    }
    AABB(AABB box0, AABB box1){
        x = box0.x.merge(box1.x);
        y = box0.y.merge(box1.y);
        z = box0.z.merge(box1.z);
    }

    AABB pad() {
        double delta = 0.0001;
        Interval new_x = (x.size() >= delta) ? x : x.expand(delta);
        Interval new_y = (y.size() >= delta) ? y : y.expand(delta);
        Interval new_z = (z.size() >= delta) ? z : z.expand(delta);

        return new AABB(new_x, new_y, new_z);
    }

    Interval axis(int n){
        if (n == 1) return y;
        if (n == 2) return z;
        return x;
    }

    boolean hit(Ray r, Interval t) {
        for (int a = 0; a < 3; a++) {
            double invD = 1.0f / r.dir().toArray()[a];
            double t0 = (axis(a).min() - r.orig().toArray()[a]) * invD;
            double t1 = (axis(a).max() - r.orig().toArray()[a]) * invD;
            if (invD < 0.0f){
                double s = t1;
                t1 = t0;
                t0 = s;
            }
            t = new Interval(max(t0, t.min()), min(t1, t.max()));
            if (t.max() <= t.min())
                return false;
        }
        return true;
    }

}
