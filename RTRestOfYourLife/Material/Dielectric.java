package RTRestOfYourLife.Material;

import RTRestOfYourLife.Color;
import RTRestOfYourLife.HitInfo.HitRecord;
import RTRestOfYourLife.HitInfo.ScatterInfo;
import RTRestOfYourLife.Ray;
import RTRestOfYourLife.Texture.SolidColor;
import RTRestOfYourLife.Texture.Texture;
import RTRestOfYourLife.Vec3;
import RTRestOfYourLife.RTUtil;

public class Dielectric implements Material {
    Texture albedo;
    double ir;

    public Dielectric(Color color, double r){
        albedo = new SolidColor(color);
        ir = r;
    }

    @Override
    public boolean scatter(Ray r, HitRecord rec, ScatterInfo srec) {
//        double refraction_ratio = rec.front_face ? (1.0/ir):ir;
//
//        Vec3 unit_direction = Vec3.unit_vector(r.dir());
//        double cos_theta = Math.min(unit_direction.negative().dot(rec.normal), 1.0);
//        double sin_theta = Math.sqrt(1.0 - cos_theta*cos_theta);
//
//        boolean cannot_refract = refraction_ratio*sin_theta > 1.0;
//        boolean reflectance = reflectance(cos_theta, refraction_ratio) > RTUtil.random_double();
//
//        Vec3 direction;
//        if(cannot_refract || reflectance)
//            direction = Vec3.reflect(unit_direction, rec.normal);
//        else
//            direction = Vec3.refract(unit_direction, rec.normal, refraction_ratio);
//
//        srec.scattered = new Ray(rec.p, direction, r.time());
//        srec.attenuation = albedo.value(rec.u, rec.v, rec.p);
        srec.attenuation = albedo.value(rec.u, rec.v, rec.p);
        srec.pdf = null;
        srec.skip_pdf = true;
        double refraction_ratio = rec.front_face ? (1.0/ir) : ir;

        Vec3 unit_direction = Vec3.unit_vector(r.dir());
        double cos_theta = Math.min(unit_direction.negative().dot(rec.normal), 1.0);
        double sin_theta = Math.sqrt(1.0 - cos_theta*cos_theta);

        boolean cannot_refract = refraction_ratio * sin_theta > 1.0;

        Vec3 direction;
        if (cannot_refract || reflectance(cos_theta, refraction_ratio) > RTUtil.random_double())
            direction = Vec3.reflect(unit_direction, rec.normal);
        else
            direction = Vec3.refract(unit_direction, rec.normal, refraction_ratio);

        srec.skip_pdf_ray = new Ray(rec.p, direction, r.time());

        return true;
    }

    private double reflectance(double cosine, double ref_idx) {
        double r0 = (1.0-ref_idx) / (1.0+ref_idx);
        r0 = r0*r0;
        return r0+(1.0-r0)*Math.pow((1.0 - cosine),5.0);
    }
}
