package RTRestOfYourLife;

public class ONB {
    public Vec3 u, v, w;

    public ONB(){
        u = new Vec3(0);
        v = new Vec3(0);
        w = new Vec3(0);
    }

    public ONB(Vec3 iu, Vec3 iv, Vec3 iw){
        u = iu;
        v = iv;
        w = iw;
    }

    public Vec3 get(int i){
        return switch (i){
            case 0 -> u;
            case 1 -> v;
            case 2 -> w;
            default -> {
                throw new IllegalStateException("正交基没有这个轴:" + i);
            }
        };
    }

    public Vec3 local(double a, double b, double c){
        return u.multiply(a).add(v.multiply(b)).add(w.multiply(c));
    }

    public Vec3 local(Vec3 a){
        return u.multiply(a.x).add(v.multiply(a.y)).add(w.multiply(a.z));
    }

    public static ONB buildFromW(Vec3 wi){
        Vec3 unit_w = Vec3.unit_vector(wi);
        Vec3 a = (Math.abs(unit_w.x) > 0.9) ? new Vec3(0,1,0) : new Vec3(1,0,0);
        Vec3 v = Vec3.unit_vector(unit_w.cross(a));
        Vec3 u = unit_w.cross(v);
        return new ONB(u, v, unit_w);
    }
}
