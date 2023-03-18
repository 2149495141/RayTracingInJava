package RTOneWeekend.HitInfo;

import RTOneWeekend.Material.Material;
import RTOneWeekend.Point;
import RTOneWeekend.Ray;
import RTOneWeekend.Vec3;

public class HitRecord {
    public double t;
    public Point p;
    public Vec3 normal;
    public boolean front_face;
    public Material mat;


    public void set_face_normal(Ray r, Vec3 outward_normal)
    {
        front_face = r.dir().dot(outward_normal) < 0;
        normal = front_face ? outward_normal : outward_normal.negative();
    }
}
