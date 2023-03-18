package RTOneWeekend.Material;

import RTOneWeekend.Ray;
import RTOneWeekend.HitInfo.HitRecord;
import RTOneWeekend.HitInfo.Radiance;

public interface Material {
    boolean scatter(Ray r, HitRecord rec, Radiance rad);
}
