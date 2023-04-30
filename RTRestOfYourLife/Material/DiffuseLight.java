package RTRestOfYourLife.Material;

import RTRestOfYourLife.Color;
import RTRestOfYourLife.HitInfo.HitRecord;
import RTRestOfYourLife.HitInfo.ScatterInfo;
import RTRestOfYourLife.Point;
import RTRestOfYourLife.Ray;
import RTRestOfYourLife.Texture.SolidColor;
import RTRestOfYourLife.Texture.Texture;

public class DiffuseLight implements Material {
    Texture emit;

    public DiffuseLight(Color c){
        emit = new SolidColor(c);
    }
    DiffuseLight(Texture texture){
        emit = texture;
    }

    @Override
    public boolean scatter(Ray r, HitRecord rec, ScatterInfo srec) {
        return false;
    }

    @Override
    public Color emitted(Ray r, HitRecord rec, Point p) {
        if (!rec.front_face)
            return new Color(0);
        return emit.value(rec.u, rec.v, p);
    }
}
