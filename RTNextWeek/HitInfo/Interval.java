package RTNextWeek.HitInfo;

public record Interval(double min, double max) {
    public static Interval merge(Interval a, Interval b){
        return new Interval(Math.min(a.min, b.min), Math.max(a.max, b.max));
    }

    public Interval merge(Interval b){
        return new Interval(Math.min(this.min, b.min), Math.max(this.max, b.max));
    }

    public double size() {
        return max - min;
    }

    public Interval expand(double delta) {
        double padding = delta/2;
        return new Interval(min - padding, max + padding);
    }

    public boolean contains(double x) {
        return min <= x && x <= max;
    }

    public Interval add_offset(double offset){
        return new Interval(min+offset, max+offset);
    }
}