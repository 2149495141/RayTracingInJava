package RTExtended.Material;

import RTExtended.Color;
import RTExtended.HitInfo.HitRecord;
import RTExtended.HitInfo.Radiance;
import RTExtended.Ray;
import RTExtended.Texture.SolidColor;
import RTExtended.Texture.Texture;

import static RTExtended.Vec3.random_unit_vector;

public class Isotropic implements Material {
    Texture albedo;

    public Isotropic(Color color){
        albedo = new SolidColor(color);
    }
    public Isotropic(Texture texture){
        albedo = texture;
    }

    @Override
    public boolean scatter(Ray r, HitRecord rec, Radiance rad) {
        rad.scattered = new Ray(rec.p, random_unit_vector());
        rad.attenuation = albedo.value(rec.u, rec.v, rec.p);
        return true;
    }
}
