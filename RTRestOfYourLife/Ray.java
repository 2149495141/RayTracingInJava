package RTRestOfYourLife;

public record Ray(Point orig, Vec3 dir, double time) {

    public Point at(double t)
    {
        return orig.add(dir.multiply(t));
    }
}
