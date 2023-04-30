package RTRestOfYourLife.HitTable;

import RTRestOfYourLife.HitInfo.HitRecord;
import RTRestOfYourLife.HitInfo.Interval;
import RTRestOfYourLife.HitTable.AABB;
import RTRestOfYourLife.HitTable.HitTable;
import RTRestOfYourLife.Material.Material;
import RTRestOfYourLife.Point;
import RTRestOfYourLife.Ray;
import RTRestOfYourLife.Vec3;

import static RTRestOfYourLife.RTUtil.degrees_to_radians;
import static RTRestOfYourLife.RTUtil.infinity;
import static java.lang.Math.*;

public class Rotate implements HitTable {
    HitTable object;
    double sin_theta;
    double cos_theta;
    int axis;
    Material mat;
    AABB box;

    public Rotate(HitTable o, double angle, int rotate_axis){
        object = o;
        axis = rotate_axis;
        double radians = degrees_to_radians(angle);
        sin_theta = sin(radians);
        cos_theta = cos(radians);
        box = object.bounding_box();

        Point min = new Point( infinity,  infinity,  infinity);
        Point max = new Point(-infinity, -infinity, -infinity);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    double x = i*box.x.max() + (1-i)*box.x.min();
                    double y = j*box.y.max() + (1-j)*box.y.min();
                    double z = k*box.z.max() + (1-k)*box.z.min();

                    Vec3 tester = new Vec3(x, y, z);
                    switch (axis) {
                        case 0 -> {
                            double newy =  cos_theta * y + sin_theta * z;
                            double newz = -sin_theta * y + cos_theta * z;
                            tester = new Vec3(x, newy, newz);
                        }
                        case 1 -> {
                            double newx =  cos_theta * x + sin_theta * z;
                            double newz = -sin_theta * x + cos_theta * z;
                            tester = new Vec3(newx, y, newz);
                        }
                        case 2 -> {
                            double newx =  cos_theta * x + sin_theta * y;
                            double newy = -sin_theta * x + cos_theta * y;
                            tester = new Vec3(newx, newy, z);
                        }
                    }


                    min = new Point(min(min.x, tester.x), min(min.y, tester.y), min(min.z, tester.z));
                    max = new Point(max(max.x, tester.x), max(max.y, tester.y), max(max.z, tester.z));

                }
            }
        }

        box = new AABB(min, max);
    }

    @Override
    public boolean hit(Ray r, Interval t, HitRecord rec) {
        double[] origin = r.orig().toArray();
        double[] direction = r.dir().toArray();

        int r0 = 0, r1 = 0;
        switch (axis){
            case 0 -> {r0 = 1; r1 = 2;}
            case 1 -> {r0 = 0; r1 = 2;}
            case 2 -> {r0 = 0; r1 = 1;}
        }

        origin[r0] = cos_theta*r.orig().toArray()[r0] - sin_theta*r.orig().toArray()[r1];
        origin[r1] = sin_theta*r.orig().toArray()[r0] + cos_theta*r.orig().toArray()[r1];

        direction[r0] = cos_theta*r.dir().toArray()[r0] - sin_theta*r.dir().toArray()[r1];
        direction[r1] = sin_theta*r.dir().toArray()[r0] + cos_theta*r.dir().toArray()[r1];

        Ray rotated_r = new Ray(new Point(origin), new Vec3(direction), r.time());

        if (!object.hit(rotated_r, t, rec))
            return false;

        double[] p = rec.p.toArray();
        p[r0] =  cos_theta*rec.p.toArray()[r0] + sin_theta*rec.p.toArray()[r1];
        p[r1] = -sin_theta*rec.p.toArray()[r0] + cos_theta*rec.p.toArray()[r1];

        double[] normal = rec.normal.toArray();
        normal[r0] =  cos_theta*rec.normal.toArray()[r0] + sin_theta*rec.normal.toArray()[r1];
        normal[r1] = -sin_theta*rec.normal.toArray()[r0] + cos_theta*rec.normal.toArray()[r1];

        rec.p = new Point(p);
        rec.normal = new Vec3(normal);

        return true;
    }

    @Override
    public AABB bounding_box() {
        return box;
    }
}
