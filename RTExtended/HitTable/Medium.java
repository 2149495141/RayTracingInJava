package RTExtended.HitTable;

import RTExtended.Color;
import RTExtended.HitInfo.HitRecord;
import RTExtended.HitInfo.Interval;
import RTExtended.Material.Isotropic;
import RTExtended.Material.Material;
import RTExtended.Ray;
import RTExtended.Vec3;

import java.util.ArrayList;

import static RTExtended.RTUtil.*;

public class Medium implements HitTable{
    record hit_intervals(ArrayList<Interval> intervals){}
    HitTable boundary;
    double neg_inv_density;
    Material phase_function;

    public Medium(HitTable b, double d, Color c){
        boundary = b;
        neg_inv_density = -1/d;
        phase_function = new Isotropic(c);
    }

    @Override
    public boolean hit(Ray r, Interval t, HitRecord rec) {
        hit_intervals hits = getHitIntervals(r, t);
        if (hits.intervals.size() == 0) return false;

        int index = random_int(0, hits.intervals.size());
        double point1 = hits.intervals.get(index).min();
        double point2 = hits.intervals.get(index).max();

        double ray_length = r.dir().length();
        double distance_inside_boundary = (point2 - point1) * ray_length;
        double hit_distance = neg_inv_density * Math.log(random_double());
        if (hit_distance >= distance_inside_boundary)
            return false;

        rec.t = point1 + hit_distance / ray_length;
        rec.p = r.at(rec.t);
        rec.normal = new Vec3(0,1,0);  // arbitrary
        rec.front_face = true;     // also arbitrary
        rec.mat = phase_function;

        return true;
    }

    hit_intervals getHitIntervals(Ray r, Interval t) {
        ArrayList<Interval> intervals = new ArrayList<>();
        HitRecord p1 = new HitRecord(), p2 = new HitRecord();
        Interval tt = new Interval(-infinity,infinity);

        while (boundary.hit(r, tt, p1)){
            tt = new Interval(p1.t+1e-5, infinity);
            if (boundary.hit(r, tt, p2)){
                if (p1.t < t.min()) p1.t = t.min();
                if (p1.t < 0) p1.t = 0;
                if (p2.t > t.max()) p2.t = t.max();
                if (p1.t >= p2.t) break;
                tt = new Interval(p2.t+1e-5, infinity);
                intervals.add(new Interval(p1.t, p2.t));

            }else break;
        }
        return new hit_intervals(intervals);
    }

    @Override
    public AABB bounding_box() {
        return boundary.bounding_box();
    }
}
