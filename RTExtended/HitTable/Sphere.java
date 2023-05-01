package RTExtended.HitTable;

import RTExtended.HitInfo.HitRecord;
import RTExtended.HitInfo.Interval;
import RTExtended.Material.Material;
import RTExtended.Point;
import RTExtended.Ray;
import RTExtended.Vec3;

import static RTExtended.RTUtil.pi;
import static java.lang.Math.acos;
import static java.lang.Math.atan2;

public class  Sphere implements HitTable {
    Vec3 center;
    double radius;
    Material mat;
    AABB box;

    public Sphere(Point center, double radius, Material mat){
        this.center = center;
        this.radius = radius;
        this.mat = mat;

        Point radius_vec = new Point(radius, radius, radius);
        box = new AABB(center.minus(radius_vec), center.add(radius));
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
        get_sphere_uv(outward_normal, rec);
        rec.mat = mat;

        return true;
    }

    void get_sphere_uv(Vec3 normal, HitRecord rec){
        double theta = acos(-normal.y);
        double phi = atan2(-normal.z, normal.x) + pi;

        rec.u = phi / (2*pi);
        rec.v = theta / pi;
    }

    @Override
    public AABB bounding_box() {
        return box;
    }
}
