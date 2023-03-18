package RTExperimentalIdeas.Texture;

import RTExperimentalIdeas.Color;
import RTExperimentalIdeas.Point;

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
