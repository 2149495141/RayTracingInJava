package RTRestOfYourLife.Texture;

import RTRestOfYourLife.Color;
import RTRestOfYourLife.Point;
import RTRestOfYourLife.Texture.Texture;
import RTRestOfYourLife.Texture.perlin;
import RTNextWeek.Vec3;

import static java.lang.Math.sin;

public class NoiseTexture implements Texture {
    perlin noise;
    double scale;

    public NoiseTexture(){
        noise = new perlin();
    }
    public NoiseTexture(double sc){
        noise = new perlin();
        scale = sc;
    }

    @Override
    public Color value(double u, double v, Point p) {
        //return new Color(1).multiply(0.5).multiply(1.0 + noise.noise(p.multiply(scale)));
        Point s = p.multiply(scale);
        return new Color(1).multiply(0.5).multiply(1+sin(s.z + 10*noise.turb(s)));
    }
}
