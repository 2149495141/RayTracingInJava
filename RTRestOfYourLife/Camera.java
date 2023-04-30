package RTRestOfYourLife;


import static RTRestOfYourLife.RTUtil.degrees_to_radians;
import static RTRestOfYourLife.RTUtil.random_double;

public class Camera {
    Point origin;
    Vec3 horizontal;
    Vec3 vertical;
    Vec3 focal_length3;
    Vec3 lower_left_corner;
    double lens_radius;
    Vec3 u,v,w;

    Camera(Point lookfrom, Point lookat, Vec3 vup,
           double vfov, double aperture, double focus_dist , double aspect_ratio)
    {
        double theta = degrees_to_radians(vfov);
        double h = Math.tan(theta/2);
        double viewport_height = 2.0*h;
        double viewport_width = aspect_ratio * viewport_height;

        w = Vec3.unit_vector(lookfrom.minus(lookat));
        u = Vec3.unit_vector(vup.cross(w));
        v = w.cross(u);

        origin = lookfrom;
        horizontal = u.multiply(focus_dist*viewport_width);
        vertical = v.multiply(focus_dist*viewport_height);
        lower_left_corner = origin
                            .minus(horizontal.divide(2))
                            .minus(vertical.divide(2))
                            .minus(w.multiply(focus_dist));

        lens_radius = aperture / 2;
    }

    Ray get_ray(double s, double t)
    {
        Vec3 rd = Vec3.random_in_unit_disk().multiply(lens_radius);
        Vec3 offset = u.multiply(rd.x).add(v.multiply(rd.y));

        Point o = origin.add(offset);
        Vec3 l = lower_left_corner
                .add(horizontal.multiply(s))
                .add(vertical.multiply(1-t))
                .minus(origin)
                .minus(offset);
        double r_time = random_double();

        return new Ray(o,l, r_time);
    }
}
