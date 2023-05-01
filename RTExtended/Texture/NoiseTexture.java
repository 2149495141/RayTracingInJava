package RTExtended.Texture;

import RTExtended.Color;
import RTExtended.Point;

import static java.lang.Math.sin;

public class NoiseTexture implements Texture {
    perlin noise;
    Color color;
    double scale;

    public NoiseTexture(){
        noise = new perlin();
        color = new Color(1);
    }
    public NoiseTexture(double sc){
        noise = new perlin();
        color = new Color(1);
        scale = sc;
    }
    public NoiseTexture(Color c, double sc){
        noise = new perlin();
        color = c;
        scale = sc;
    }

    @Override
    public Color value(double u, double v, Point p) {
        //return new Color(1).multiply(0.5).multiply(1.0 + noise.noise(p.multiply(scale)));
        Point s = p.multiply(scale);
        return color.multiply(0.5).multiply(1+sin(s.z + 10*noise.turb(s)));
    }
}
