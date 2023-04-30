package RTRestOfYourLife.Material;

import RTRestOfYourLife.Color;
import RTRestOfYourLife.HitInfo.HitRecord;
import RTRestOfYourLife.HitInfo.ScatterInfo;
import RTRestOfYourLife.PDF.SpherePDF;
import RTRestOfYourLife.Ray;
import RTRestOfYourLife.Texture.SolidColor;
import RTRestOfYourLife.Texture.Texture;

import static RTRestOfYourLife.RTUtil.pi;
import static RTRestOfYourLife.Vec3.random_unit_vector;

public class Isotropic implements Material {
    Texture albedo;

    public Isotropic(Color c){
        albedo = new SolidColor(c);
    }

    @Override
    public boolean scatter(Ray r, HitRecord rec, ScatterInfo srec) {
        srec.attenuation = albedo.value(rec.u, rec.v, rec.p);
        srec.pdf = new SpherePDF();
        srec.skip_pdf = false;
        return true;
    }

    @Override
    public double scattering_pdf(Ray r, HitRecord rec, Ray scattered) {
        return 1 / (4 * pi);
    }
}
