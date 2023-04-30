package RTRestOfYourLife.HitTable;

import RTRestOfYourLife.HitInfo.HitRecord;
import RTRestOfYourLife.HitInfo.Interval;
import RTRestOfYourLife.HitTable.AABB;
import RTRestOfYourLife.HitTable.HitTable;
import RTRestOfYourLife.Material.Material;
import RTRestOfYourLife.ONB;
import RTRestOfYourLife.Point;
import RTRestOfYourLife.Ray;
import RTRestOfYourLife.Vec3;

import static RTRestOfYourLife.RTUtil.*;
import static java.lang.Math.acos;
import static java.lang.Math.atan2;

public class Sphere implements HitTable {
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

    @Override
    public double pdf_value(Point o, Vec3 v) {
        HitRecord rec = new HitRecord();
        if (!this.hit(new Ray(o, v, 1), new Interval(0.001, infinity), rec))
            return 0;

        double cos_theta_max = Math.sqrt(1 - radius*radius/(center.minus(o).length_squared()));
        double solid_angle = 2*pi*(1-cos_theta_max);

        return  1 / solid_angle;
    }

    @Override
    public Vec3 random(Vec3 o) {
        Vec3 direction = center.minus(o);
        double distance_squared = direction.length_squared();
        ONB uvw = ONB.buildFromW(direction);
        return uvw.local(random_to_sphere(radius, distance_squared));
    }

    Vec3 random_to_sphere(double radius, double distance_squared) {
        double r1 = random_double();
        double r2 = random_double();
        double z = 1 + r2*(Math.sqrt(1-radius*radius/distance_squared) - 1);

        double phi = 2*pi*r1;
        double x = Math.cos(phi)*Math.sqrt(1-z*z);
        double y = Math.sin(phi)*Math.sqrt(1-z*z);

        return new Vec3(x, y, z);
    }
}
