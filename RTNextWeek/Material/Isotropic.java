package RTNextWeek.Material;

import RTNextWeek.Color;
import RTNextWeek.HitInfo.HitRecord;
import RTNextWeek.HitInfo.Radiance;
import RTNextWeek.Ray;
import RTNextWeek.Texture.SolidColor;
import RTNextWeek.Texture.Texture;

import static RTNextWeek.Vec3.random_unit_vector;

public class Isotropic implements Material{
    Texture albedo;

    public Isotropic(Color c){
        albedo = new SolidColor(c);
    }

    @Override
    public boolean scatter(Ray r, HitRecord rec, Radiance rad) {
        rad.scattered = new Ray(rec.p, random_unit_vector(), r.time());
        rad.attenuation = albedo.value(rec.u, rec.v, rec.p);
        return true;
    }
}
