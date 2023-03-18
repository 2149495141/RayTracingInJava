package RTExperimentalIdeas.Material;

import RTExperimentalIdeas.Color;
import RTExperimentalIdeas.HitInfo.HitRecord;
import RTExperimentalIdeas.HitInfo.Radiance;
import RTExperimentalIdeas.Ray;
import RTExperimentalIdeas.Texture.SolidColor;
import RTExperimentalIdeas.Texture.Texture;
import RTExperimentalIdeas.Vec3;

import static RTExperimentalIdeas.RTUtil.random_double;
import static java.lang.Math.min;

public class Specular implements Material{
    Texture albedo, specular;
    double ir;

    public Specular(Color color, double r){
        albedo = new SolidColor(color);
        specular = new SolidColor(new Color(1));
        ir = r;
    }
    Specular(Color color, Color rColor, double r){
        albedo = new SolidColor(color);
        specular = new SolidColor(rColor);
        ir = r;
    }
    public Specular(Texture texture, double r){
        albedo = texture;
        specular = new SolidColor(new Color(1));
        ir = r;
    }

    @Override
    public boolean scatter(Ray r, HitRecord rec, Radiance rad) {
        Vec3 unit_direction = Vec3.unit_vector(r.dir());
        double cos_theta = min(unit_direction.negative().dot(rec.normal), 1.0);

        if (reflectance(cos_theta, 1/ir) > random_double())
        {
            rad.scattered = new Ray(rec.p, Vec3.reflect(unit_direction, rec.normal));
            rad.attenuation = specular.value(rec.u, rec.v, rec.p);
        } else
        {
            rad.scattered = new Ray(rec.p, rec.normal.add(Vec3.random_unit_vector()));
            rad.attenuation = albedo.value(rec.u, rec.v, rec.p);
        }

        return true;
    }

    private double reflectance(double cosine, double ref_idx) {
        double r0 = (1.0-ref_idx) / (1.0+ref_idx);
        r0 = r0*r0;
        return r0+(1.0-r0)*Math.pow((1.0 - cosine),5.0);
    }
}
