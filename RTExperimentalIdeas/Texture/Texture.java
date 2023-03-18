package RTExperimentalIdeas.Texture;

import RTExperimentalIdeas.Color;
import RTExperimentalIdeas.Point;

public interface Texture {
    Color value(double u, double v, Point p);
}
