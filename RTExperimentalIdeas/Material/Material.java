package RTExperimentalIdeas.Material;

import RTExperimentalIdeas.HitInfo.HitRecord;
import RTExperimentalIdeas.HitInfo.Radiance;
import RTExperimentalIdeas.Ray;
import RTExperimentalIdeas.Color;
import RTExperimentalIdeas.Point;

public interface Material {
    boolean scatter(Ray r, HitRecord rec, Radiance rad);
    default Color emitted(double u, double v, Point p) { return new Color(0); }

}
