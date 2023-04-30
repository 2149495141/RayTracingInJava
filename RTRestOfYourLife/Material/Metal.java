package RTRestOfYourLife.Material;

import RTRestOfYourLife.Color;
import RTRestOfYourLife.HitInfo.HitRecord;
import RTRestOfYourLife.HitInfo.ScatterInfo;
import RTRestOfYourLife.Ray;
import RTRestOfYourLife.Texture.SolidColor;
import RTRestOfYourLife.Texture.Texture;
import RTRestOfYourLife.Vec3;


public class Metal implements Material {
    Texture albedo;
    double fuzz;

    public Metal(Color color, double f){
        albedo = new SolidColor(color);
        fuzz = f;
    }
    public Metal(Texture texture, double f){
        albedo = texture;
        fuzz = f;
    }

    @Override
    public boolean scatter(Ray r, HitRecord rec, ScatterInfo srec) {
//        Vec3 unitVector = Vec3.unit_vector(r.dir());
//        Vec3 reflected = Vec3.reflect(unitVector, rec.normal);
//        Vec3 randV = Vec3.random_in_unit_sphere();
//        Vec3 fuzzV =  new Vec3(fuzz).multiply(randV);
//
//        srec.scattered = new Ray(rec.p,reflected.add(fuzzV), r.time());
//        srec.attenuation = albedo.value(rec.u, rec.v, rec.p);
//
//        return (srec.scattered.dir().dot(rec.normal) > 0);

        srec.attenuation = albedo.value(rec.u,rec.v,rec.p);
        srec.pdf = null;
        srec.skip_pdf = true;
        Vec3 reflected = Vec3.reflect(Vec3.unit_vector(r.dir()), rec.normal);
        srec.skip_pdf_ray =
                new Ray(rec.p, reflected.add(Vec3.random_in_unit_sphere().multiply(fuzz)), r.time());
        return true;
    }
}
