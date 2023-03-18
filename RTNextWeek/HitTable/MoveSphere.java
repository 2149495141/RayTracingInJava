package RTNextWeek.HitTable;

import RTNextWeek.HitInfo.HitRecord;
import RTNextWeek.HitInfo.Interval;
import RTNextWeek.Material.Material;
import RTNextWeek.Point;
import RTNextWeek.Ray;
import RTNextWeek.Vec3;

public class MoveSphere implements HitTable{
    Point center0, center1;
    Vec3 center_vec;
    double radius;
    Material mat;
    AABB box;

    public MoveSphere(Point c0, Point c1, double r, Material m){
        center0 = c0;
        center1 = c1;
        center_vec = c1.minus(c0);
        radius = r;
        mat = m;

        Point radius_vec = new Point(radius, radius, radius);
        AABB box0 = new AABB(center0.minus(radius_vec), center0.add(radius));
        AABB box1 = new AABB(center1.minus(radius_vec), center1.add(radius));
        box = new AABB(box0, box1);
    }

    Point center(double time){
        return center0.add(center_vec.multiply(time));
    }

    @Override
    public boolean hit(Ray r, Interval t, HitRecord rec) {
        Vec3 oc = r.orig().minus(center(r.time()));
        double a = r.dir().length_squared();
        double half_b = oc.dot(r.dir());
        double c = oc.length_squared() - radius*radius;

        double discriminant = half_b*half_b - a*c;
        if (discriminant < 0) return false;
        double sqrtd = Math.sqrt(discriminant);

        double root = (-half_b - sqrtd) / a;
        if(root < t.min() || t.max() < root)
        {
            root = (-half_b + sqrtd) / a;
            if(root < t.min() || t.max() < root)
                return false;
        }

        rec.t = root;
        rec.p = r.at(rec.t);
        Vec3 outward_normal = rec.p.minus(center(r.time())).divide(radius);;
        rec.set_face_normal(r, outward_normal);
        rec.mat = mat;

        return true;
    }

    @Override
    public AABB bounding_box() {
        return box;
    }
}
