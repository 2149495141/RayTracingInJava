package RTExperimentalIdeas.Material;

import RTExperimentalIdeas.Color;
import RTExperimentalIdeas.HitInfo.HitRecord;
import RTExperimentalIdeas.HitInfo.Radiance;
import RTExperimentalIdeas.Point;
import RTExperimentalIdeas.Ray;
import RTExperimentalIdeas.Texture.SolidColor;
import RTExperimentalIdeas.Texture.Texture;

public class DiffuseLight implements Material {
    Texture emit;

    public DiffuseLight(Color c){
        emit = new SolidColor(c);
    }
    DiffuseLight(Texture texture){
        emit = texture;
    }

    @Override
    public boolean scatter(Ray r, HitRecord rec, Radiance rad) {
        return false;
    }

    @Override
    public Color emitted(double u, double v, Point p) {
        return emit.value(u, v, p);
    }
}
