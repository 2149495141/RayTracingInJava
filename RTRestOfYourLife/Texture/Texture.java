package RTRestOfYourLife.Texture;

import RTRestOfYourLife.Color;
import RTRestOfYourLife.Point;

public interface Texture {
    Color value(double u, double v, Point p);
}
