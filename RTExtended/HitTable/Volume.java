package RTExtended.HitTable;

import RTExtended.HitInfo.HitRecord;
import RTExtended.HitInfo.Interval;
import RTExtended.Material.Isotropic;
import RTExtended.Ray;
import RTExtended.Color;
import RTExtended.Material.Material;
import RTExtended.Texture.Texture;
import RTExtended.Vec3;

import static RTExtended.RTUtil.random_double;
import static RTExtended.RTUtil.infinity;
import static java.lang.Math.abs;

public class Volume implements HitTable {
    HitTable boundary;
    double neg_inv_density;
    Material phase_function;
    record volume_hit_info(double first_p, double distance){}

    public Volume(HitTable b, double d, Color c){
        boundary = b;
        neg_inv_density = -1/d;
        phase_function = new Isotropic(c);
        enable_surface_mat = false;
    }
    public Volume(HitTable b, double d, Texture t){
        boundary = b;
        neg_inv_density = -1/d;
        phase_function = new Isotropic(t);
        enable_surface_mat = false;
    }
    

    @Override
    public boolean hit(Ray r, Interval t, HitRecord rec) {
        volume_hit_info volumeInfo = getHitPointAndDistance(r, t, boundary);

        double ray_length = r.dir().length();
        double distance_inside_boundary = volumeInfo.distance * ray_length;
        double hit_distance = neg_inv_density * Math.log(random_double());

        if (hit_distance >= distance_inside_boundary)
                return false;

        rec.t = volumeInfo.first_p + hit_distance / ray_length;
        rec.p = r.at(rec.t);
        rec.normal = new Vec3(1);
        rec.front_face = true;
        rec.mat = phase_function;

        return true;
    }

    volume_hit_info getHitPointAndDistance(Ray r, Interval t, HitTable boundary) {
        HitRecord hit_p1 = new HitRecord(), hit_p2 = new HitRecord();
        Interval tt = new Interval(-infinity,infinity);

        double first_point = 0;
        double distance = 0;
        while (boundary.hit(r, tt, hit_p1)) {
            if (hit_p1.t < t.min()) hit_p1.t = t.min();
            if (hit_p1.t < 0) hit_p1.t = 0;
            if (first_point==0) first_point = hit_p1.t;
            tt = new Interval(hit_p1.t + 1e-5, infinity);
            if (boundary.hit(r, tt, hit_p2)) {
                if (hit_p2.t > t.max()) hit_p2.t = t.max();
                if (hit_p1.t >= hit_p2.t) break;
                distance = hit_p2.t - hit_p1.t + distance;
                tt = new Interval(hit_p2.t + 1e-5, infinity);
            } else break;
        }
        return new volume_hit_info(first_point, distance);
    }

    @Override
    public AABB bounding_box() {
        return boundary.bounding_box();
    }
}
