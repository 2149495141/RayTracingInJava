package RTNextWeek.Texture;

import RTNextWeek.Color;
import RTNextWeek.Point;

public interface Texture {
    Color value(double u, double v, Point p);
}
