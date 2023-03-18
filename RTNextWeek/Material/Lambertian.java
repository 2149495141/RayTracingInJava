package RTNextWeek.Material;

import RTNextWeek.Color;
import RTNextWeek.HitInfo.HitRecord;
import RTNextWeek.HitInfo.Radiance;
import RTNextWeek.Ray;
import RTNextWeek.Texture.SolidColor;
import RTNextWeek.Texture.Texture;
import RTNextWeek.Vec3;


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

        rad.scattered = new Ray(rec.p, scatter_direction, r.time());
        rad.attenuation = albedo.value(rec.u, rec.v, rec.p);

        return true;
    }
}
