package RTOneWeekend.HitTable;

import RTOneWeekend.Material.Material;
import RTOneWeekend.Ray;
import RTOneWeekend.HitInfo.HitRecord;
import RTOneWeekend.HitInfo.Interval;
import RTOneWeekend.Vec3;

public class Sphere implements HitTable{
    Vec3 center;
    double radius;
    Material mat;

    public Sphere(Vec3 center, double radius, Material mat){
        this.center = center;
        this.radius = radius;
        this.mat = mat;
    }

    @Override
    public boolean hit(Ray r, Interval t, HitRecord rec) {
        Vec3 oc = r.orig().minus(center);
        double a = r.dir().length_squared();
        double half_b = oc.dot(r.dir());
        double c = oc.length_squared() - radius*radius;

        double discriminant = half_b*half_b - a*c;

        if(discriminant < 0)
            return false;

        double sqrtd = Math.sqrt(discriminant);
        double root = (-half_b - sqrtd) / a;

        if(root < t.min() || t.max() < root)
        {
            root = (-half_b + sqrtd) / a;
            if(root < t.min() || t.max() < root)
                return false;
        }

        rec.t = root;
        rec.p = r.at(root);
        Vec3 outward_normal = rec.p.minus(center).divide(radius);
        rec.set_face_normal(r, outward_normal);
        rec.mat = mat;

        return true;
    }
}
