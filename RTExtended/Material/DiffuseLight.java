package RTExtended.Material;

import RTExtended.Color;
import RTExtended.HitInfo.HitRecord;
import RTExtended.HitInfo.Radiance;
import RTExtended.Point;
import RTExtended.Ray;
import RTExtended.Texture.SolidColor;
import RTExtended.Texture.Texture;

public class DiffuseLight implements Material {
    Texture emit;

    public DiffuseLight(Color c){
        emit = new SolidColor(c);
    }
    DiffuseLight(Texture texture){
        emit = texture;
    }

    @Override
    public boolean scatter(Ray r, HitRecord rec, Radiance rad) {
        return false;
    }

    @Override
    public Color emitted(double u, double v, Point p) {
        return emit.value(u, v, p);
    }
}
