package RTExtended.Texture;

import RTExtended.Color;
import RTExtended.Point;

public interface Texture {
    Color value(double u, double v, Point p);
}
