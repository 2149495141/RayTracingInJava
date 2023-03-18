package RTNextWeek.Material;

import RTNextWeek.Color;
import RTNextWeek.HitInfo.HitRecord;
import RTNextWeek.HitInfo.Radiance;
import RTNextWeek.Point;
import RTNextWeek.Ray;

public interface Material {
    boolean scatter(Ray r, HitRecord rec, Radiance rad);

    default Color emitted(double u, double v, Point p){ return new Color(0); }
}
