package RTExperimentalIdeas.HitInfo;

import RTExperimentalIdeas.Color;
import RTExperimentalIdeas.Point;
import RTExperimentalIdeas.Ray;
import RTExperimentalIdeas.Vec3;

public class Radiance {
    public Ray scattered;
    public Color attenuation;

    public Radiance(){
        scattered = new Ray(new Point(0), new Vec3(0));
        attenuation = new Color(0);
    }
}
