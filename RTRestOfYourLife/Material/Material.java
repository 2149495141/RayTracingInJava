package RTRestOfYourLife.Material;

import RTRestOfYourLife.Color;
import RTRestOfYourLife.HitInfo.HitRecord;
import RTRestOfYourLife.HitInfo.ScatterInfo;
import RTRestOfYourLife.Point;
import RTRestOfYourLife.Ray;

public interface Material {
    boolean scatter(Ray r, HitRecord rec, ScatterInfo srec);

    default double scattering_pdf(Ray r, HitRecord rec, Ray scattered){ return 0; };

    default Color emitted(Ray r, HitRecord rec, Point p){ return new Color(0); }
}
