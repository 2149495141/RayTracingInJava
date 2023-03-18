package RTOneWeekend.Material;

import RTOneWeekend.Color;
import RTOneWeekend.Ray;
import RTOneWeekend.HitInfo.HitRecord;
import RTOneWeekend.HitInfo.Radiance;
import RTOneWeekend.Vec3;


public class Lambertian implements Material{
    Color albedo;

    public Lambertian(Color color){
        albedo = color;
    }

    @Override
    public boolean scatter(Ray r, HitRecord rec, Radiance rad) {
        Vec3 scatter_direction = rec.normal.add(Vec3.random_unit_vector());//Vec3.random_in_hemisphere(rec.normal);

        if(scatter_direction.near_zero())
            scatter_direction = rec.normal;

        rad.scattered = new Ray(rec.p, scatter_direction);
        rad.attenuation = albedo;

        return true;
    }
}
