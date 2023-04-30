package RTRestOfYourLife.HitTable;

import RTRestOfYourLife.HitInfo.HitRecord;
import RTRestOfYourLife.HitInfo.Interval;
import RTRestOfYourLife.HitTable.AABB;
import RTRestOfYourLife.Point;
import RTRestOfYourLife.Ray;
import RTRestOfYourLife.Vec3;

public interface HitTable {
    boolean hit(Ray r, Interval t, HitRecord rec);

    AABB bounding_box();

    default double pdf_value(Point o, Vec3 v){ return 0;}

    default Vec3 random(Vec3 o){ return new Vec3(1,0,0);}
}
