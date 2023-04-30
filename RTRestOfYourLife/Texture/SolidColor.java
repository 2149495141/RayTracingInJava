package RTRestOfYourLife.Texture;

import RTRestOfYourLife.Color;
import RTRestOfYourLife.Point;
import RTRestOfYourLife.Texture.Texture;

public class SolidColor implements Texture {
    Color color;

    public SolidColor(Color c){
        color = c;
    }

    @Override
    public Color value(double u, double v, Point p) {
        return color;
    }
}
