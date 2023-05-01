package RTExtended.Material;

import RTExtended.HitInfo.HitRecord;
import RTExtended.HitInfo.Radiance;
import RTExtended.Ray;
import RTExtended.Color;
import RTExtended.Point;

public interface Material {
    boolean scatter(Ray r, HitRecord rec, Radiance rad);
    default Color emitted(double u, double v, Point p) { return new Color(0); }

}
