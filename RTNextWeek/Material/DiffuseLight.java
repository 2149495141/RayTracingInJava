package RTNextWeek.Material;

import RTNextWeek.Color;
import RTNextWeek.HitInfo.HitRecord;
import RTNextWeek.HitInfo.Radiance;
import RTNextWeek.Point;
import RTNextWeek.Ray;
import RTNextWeek.Texture.SolidColor;
import RTNextWeek.Texture.Texture;

public class DiffuseLight implements Material{
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
