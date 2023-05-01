package RTExtended.Material;

import RTExtended.Color;
import RTExtended.HitInfo.HitRecord;
import RTExtended.HitInfo.Radiance;
import RTExtended.Ray;
import RTExtended.Texture.SolidColor;
import RTExtended.Texture.Texture;
import RTExtended.Vec3;


public class Lambertian implements Material {
    Texture albedo;

    public Lambertian(Color color){
        albedo = new SolidColor(color);
    }
    public Lambertian(Texture texture){
        albedo = texture;
    }

    @Override
    public boolean scatter(Ray r, HitRecord rec, Radiance rad) {
        Vec3 scatter_direction = rec.normal.add(Vec3.random_unit_vector());;

        if(scatter_direction.near_zero())
            scatter_direction = rec.normal;

        rad.scattered = new Ray(rec.p, scatter_direction);
        rad.attenuation = albedo.value(rec.u, rec.v, rec.p);

        return true;
    }
}
