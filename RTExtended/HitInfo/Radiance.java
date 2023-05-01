package RTExtended.HitInfo;

import RTExtended.Color;
import RTExtended.Point;
import RTExtended.Ray;
import RTExtended.Vec3;

public class Radiance {
    public Ray scattered;
    public Color attenuation;

    public Radiance(){
        scattered = new Ray(new Point(0), new Vec3(0));
        attenuation = new Color(0);
    }
}
