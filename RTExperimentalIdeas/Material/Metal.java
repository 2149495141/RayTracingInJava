package RTExperimentalIdeas.Material;

import RTExperimentalIdeas.Color;
import RTExperimentalIdeas.HitInfo.HitRecord;
import RTExperimentalIdeas.HitInfo.Radiance;
import RTExperimentalIdeas.Ray;
import RTExperimentalIdeas.Texture.SolidColor;
import RTExperimentalIdeas.Texture.Texture;
import RTExperimentalIdeas.Vec3;


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
    public boolean scatter(Ray r, HitRecord rec, Radiance rad) {
        Vec3 unitVector = Vec3.unit_vector(r.dir());
        Vec3 reflected = Vec3.reflect(unitVector, rec.normal);
        Vec3 randV = Vec3.random_in_unit_sphere();
        Vec3 fuzzV =  new Vec3(fuzz).multiply(randV);

        rad.scattered = new Ray(rec.p,reflected.add(fuzzV));
        rad.attenuation = albedo.value(rec.u, rec.v, rec.p);

        return (rad.scattered.dir().dot(rec.normal) > 0);
    }
}
