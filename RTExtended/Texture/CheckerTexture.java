package RTExtended.Texture;

import RTExtended.Color;
import RTExtended.Point;

import static java.lang.Math.floor;

public class CheckerTexture implements Texture {
    Texture even, odd;
    double inv_scale;

    public CheckerTexture(double scale, Color c0, Color c1){
        inv_scale = 1/scale;
        even = new SolidColor(c0);
        odd = new SolidColor(c1);
    }

    @Override
    public Color value(double u, double v, Point p) {
        double xInteger = (int)floor(inv_scale * p.x);
        double yInteger = (int)floor(inv_scale * p.y);
        double zInteger = (int)floor(inv_scale * p.z);

        boolean isEven = (xInteger + yInteger + zInteger) % 2 == 0;

        return isEven ? even.value(u, v, p) : odd.value(u, v, p);
    }
}
