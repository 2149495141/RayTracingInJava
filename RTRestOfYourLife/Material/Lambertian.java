package RTRestOfYourLife.Material;

import RTRestOfYourLife.Color;
import RTRestOfYourLife.HitInfo.HitRecord;
import RTRestOfYourLife.HitInfo.ScatterInfo;
import RTRestOfYourLife.ONB;
import RTRestOfYourLife.PDF.CosinePDF;
import RTRestOfYourLife.Ray;
import RTRestOfYourLife.Texture.SolidColor;
import RTRestOfYourLife.Texture.Texture;
import RTRestOfYourLife.Vec3;

import static RTRestOfYourLife.RTUtil.pi;


public class Lambertian implements Material {
    Texture albedo;

    public Lambertian(Color color){
        albedo = new SolidColor(color);
    }
    public Lambertian(Texture texture){
        albedo = texture;
    }
    
    @Override
    public boolean scatter(Ray r, HitRecord rec, ScatterInfo srec) {
//        Vec3 scatter_direction = Vec3.random_in_hemisphere(rec.normal);//rec.normal.add(Vec3.random_unit_vector());;
//
//        if(scatter_direction.near_zero())
//            scatter_direction = rec.normal;
//
//        rad.scattered = new Ray(rec.p, scatter_direction, r.time());
//        rad.attenuation = albedo.value(rec.u, rec.v, rec.p);

        srec.attenuation = albedo.value(rec.u, rec.v, rec.p);
        srec.pdf = new CosinePDF(rec.normal);
        srec.skip_pdf = false;

        return true;
    }

    @Override
    public double scattering_pdf(Ray r, HitRecord rec, Ray scattered) {
        double cos_theta = rec.normal.dot(Vec3.unit_vector(scattered.dir()));
        return cos_theta < 0 ? 0:cos_theta/pi;
//        return 1 / (2*pi);
    }
}
