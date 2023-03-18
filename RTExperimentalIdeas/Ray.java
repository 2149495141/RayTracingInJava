package RTExperimentalIdeas;


public record Ray(Point orig, Vec3 dir) {

    public Point at(double t)
    {
        return orig.add(dir.multiply(t));
    }
}
