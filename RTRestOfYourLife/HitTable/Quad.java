package RTRestOfYourLife.HitTable;

import RTRestOfYourLife.HitInfo.HitRecord;
import RTRestOfYourLife.HitInfo.Interval;
import RTRestOfYourLife.HitTable.AABB;
import RTRestOfYourLife.HitTable.HitTable;
import RTRestOfYourLife.HitTable.HitTableList;
import RTRestOfYourLife.Material.Material;
import RTRestOfYourLife.Point;
import RTRestOfYourLife.Ray;
import RTRestOfYourLife.Vec3;

import static RTRestOfYourLife.RTUtil.infinity;
import static RTRestOfYourLife.RTUtil.random_double;
import static java.lang.Math.*;

public class Quad implements HitTable {
    Point q;
    Vec3 u, v, w;
    Vec3 normal;
    double d;
    double area;
    Material mat;
    AABB box;

    public Quad(Point start_point, Vec3 u_side_length, Vec3 v_side_length, Material m){
        q = start_point;
        u = u_side_length;
        v = v_side_length;
        Vec3 n = u.cross(v);
        w = n.divide(n.dot(n));
        normal = Vec3.unit_vector(n);
        d = normal.dot(q);
        area = n.length();
        mat = m;
        box = new AABB(q, q.add(u).add(v)).pad();
    }

    public static HitTableList box(Point a, Point b, Material mat){
        HitTableList sides = new HitTableList();

        // Construct the two opposite vertices with the minimum and maximum coordinates.
        Point min = new Point(min(a.x, b.x), min(a.y, b.y), min(a.z, b.z));
        Point max = new Point(max(a.x, b.x), max(a.y, b.y), max(a.z, b.z));

        Vec3 dx = new Vec3(max.x-min.x, 0, 0);
        Vec3 dy = new Vec3(0, max.y-min.y, 0);
        Vec3 dz = new Vec3(0, 0, max.z-min.z);

        sides.add(new Quad(new Point(min.x, min.y, max.z),  dx,            dy,            mat));
        sides.add(new Quad(new Point(max.x, min.y, max.z),  dz.negative(), dy,            mat));
        sides.add(new Quad(new Point(max.x, min.y, min.z),  dx.negative(), dy,            mat));
        sides.add(new Quad(new Point(min.x, min.y, min.z),  dz,            dy,            mat));
        sides.add(new Quad(new Point(min.x, max.y, max.z),  dx,            dz.negative(), mat));
        sides.add(new Quad(new Point(min.x, min.y, min.z),  dx,            dz,            mat));

        return sides;
    }

    @Override
    public boolean hit(Ray r, Interval t, HitRecord rec) {
        double denom = normal.dot(r.dir());
        if (abs(denom) < 1e-8)
            return false;

        double dt = (d - normal.dot(r.orig())) / denom;
        if (!t.contains(dt))
            return false;

        Point intersection = r.at(dt);
        Vec3 planar_hitpt_vector = intersection.minus(q);
        double alpha = w.dot(planar_hitpt_vector.cross(v));
        double beta = w.dot(u.cross(planar_hitpt_vector));

        if ((alpha < 0) || (1 < alpha) || (beta < 0) || (1 < beta))
            return false;

        rec.u = alpha;
        rec.v = beta;
        rec.t = dt;
        rec.p = intersection;
        rec.mat = mat;
        rec.set_face_normal(r, normal);

        return true;
    }

    @Override
    public AABB bounding_box() {
        return box;
    }

    @Override
    public double pdf_value(Point origin, Vec3 v) {
        HitRecord rec = new HitRecord();
        if (!this.hit(new Ray(origin, v, 1), new Interval(0.001, infinity), rec))
            return 0;

        double distance_squared = rec.t * rec.t * v.length_squared();
        double cosine = Math.abs(v.dot(rec.normal) / v.length());

        return distance_squared / (cosine * area);
    }

    @Override
    public Vec3 random(Vec3 o) {
        Vec3 p = q.add(u.multiply(random_double())).add(v.multiply(random_double()));
        return p.minus(o);
    }
}
