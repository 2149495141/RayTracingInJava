package RTNextWeek.HitTable;

import RTNextWeek.HitInfo.HitRecord;
import RTNextWeek.HitInfo.Interval;
import RTNextWeek.Ray;

public interface HitTable {
    boolean hit(Ray r, Interval t, HitRecord rec);

    AABB bounding_box();
}
