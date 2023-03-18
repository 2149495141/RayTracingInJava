package RTNextWeek.HitInfo;

import RTNextWeek.Color;
import RTNextWeek.Point;
import RTNextWeek.Ray;
import RTNextWeek.Vec3;

public class Radiance {
    public Ray scattered;
    public Color attenuation;

    public Radiance(){
        scattered = new Ray(new Point(0), new Vec3(0), 0);
        attenuation = new Color(0);
    }
}
