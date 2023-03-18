package RTExperimentalIdeas.Material;

import RTExperimentalIdeas.Color;
import RTExperimentalIdeas.HitInfo.HitRecord;
import RTExperimentalIdeas.HitInfo.Radiance;
import RTExperimentalIdeas.Ray;
import RTExperimentalIdeas.Texture.SolidColor;
import RTExperimentalIdeas.Texture.Texture;

import static RTExperimentalIdeas.Vec3.random_unit_vector;

public class Isotropic implements Material {
    Texture albedo;

    public Isotropic(Color color){
        albedo = new SolidColor(color);
    }

    @Override
    public boolean scatter(Ray r, HitRecord rec, Radiance rad) {
        rad.scattered = new Ray(rec.p, random_unit_vector());
        rad.attenuation = albedo.value(rec.u, rec.v, rec.p);
        return true;
    }
}
