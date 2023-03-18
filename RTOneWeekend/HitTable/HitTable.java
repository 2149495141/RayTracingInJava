package RTOneWeekend.HitTable;

import RTOneWeekend.Ray;
import RTOneWeekend.HitInfo.Interval;
import RTOneWeekend.HitInfo.HitRecord;

public interface HitTable {
    boolean hit(Ray r, Interval t, HitRecord rec);
}
