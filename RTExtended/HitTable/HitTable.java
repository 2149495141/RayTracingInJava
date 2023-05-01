package RTExtended.HitTable;

import RTExtended.HitInfo.HitRecord;
import RTExtended.HitInfo.Interval;
import RTExtended.Ray;

public interface HitTable {
    boolean hit(Ray r, Interval t, HitRecord rec);
    AABB bounding_box();
}
