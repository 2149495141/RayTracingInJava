package RTExperimentalIdeas;

import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.*;

public class RTUtil {
    public static final double infinity = Double.POSITIVE_INFINITY;//1.0 / 0.0;
    public static final double pi = 3.1415926535897932385;

    public static double degrees_to_radians(double degrees)
    {
        return degrees * pi / 180;
    }

    public static double random_double()
    {
        return ThreadLocalRandom.current().nextDouble();
    }

    public static double random_double(double min, double max)
    {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static int random_int(int min, int max)
    {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public static double clamp(double x, double min, double max)
    {
        if(x<min) return min;
        else return Math.min(x, max);
    }

}
