package RTExperimentalIdeas.HitTable;

import RTExperimentalIdeas.HitInfo.HitRecord;
import RTExperimentalIdeas.HitInfo.Interval;
import RTExperimentalIdeas.Ray;

public interface HitTable {
    boolean hit(Ray r, Interval t, HitRecord rec);
    AABB bounding_box();
}
