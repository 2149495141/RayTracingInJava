package RTRestOfYourLife.HitTable;

import RTRestOfYourLife.HitInfo.HitRecord;
import RTRestOfYourLife.HitInfo.Interval;
import RTRestOfYourLife.HitTable.AABB;
import RTRestOfYourLife.HitTable.HitTable;
import RTRestOfYourLife.Material.Isotropic;
import RTRestOfYourLife.Ray;
import RTRestOfYourLife.Color;
import RTRestOfYourLife.Material.Material;
import RTRestOfYourLife.Vec3;

import static RTRestOfYourLife.RTUtil.*;

public class Medium implements HitTable {
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
        HitRecord point1 = new HitRecord(), point2 = new HitRecord();
        if (!boundary.hit(r, new Interval(-infinity, infinity), point1))
            return false;

        if (!boundary.hit(r, new Interval(point1.t+0.0001, infinity), point2))
            return false;

        if (point1.t < t.min()) point1.t = t.min();
        if (point2.t > t.max()) point2.t = t.max();

        if (point1.t >= point2.t)
            return false;

        if (point1.t < 0)
            point1.t = 0;

        double ray_length = r.dir().length();
        double distance_inside_boundary = (point2.t - point1.t) * ray_length;
        double hit_distance = neg_inv_density * Math.log(random_double());

        if (hit_distance > distance_inside_boundary)
            return false;

        rec.t = point1.t + hit_distance / ray_length;
        rec.p = r.at(rec.t);
        rec.normal = new Vec3(1,0,0);  // arbitrary
        rec.front_face = true;     // also arbitrary
        rec.mat = phase_function;

        return true;
    }

    @Override
    public AABB bounding_box() {
        return boundary.bounding_box();
    }
}
