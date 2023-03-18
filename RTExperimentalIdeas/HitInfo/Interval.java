package RTExperimentalIdeas.HitInfo;

public record Interval(double min, double max) {

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
}