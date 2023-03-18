package RTOneWeekend;

import static RTOneWeekend.RTUtil.random_double;

public sealed class Vec3 permits Color, Point {
    double x, y, z;

    public Vec3(double e0, double e1, double e2){
        x = e0; y = e1; z = e2;
    }

    public Vec3(double s){
        x = s; y = s; z = s;
    }

    public Vec3 negative()
    {
        return new Vec3(-x, -y, -z);
    }

    public double[] toArray(){ return new double[]{x, y, z}; }

    public double length_squared()
    {
        return x*x + y*y + z*z;
    }

    public double length(){
        return Math.sqrt(length_squared());
    }

    public void plus_Equal(Vec3 v){
        x+=v.x; y+=v.y; z+=v.z;
    }

    public void plus_Equal(double s){
        x+=s; y+=s; z+=s;
    }

    public void minus_Equal(Vec3 v){
        x-=v.x; y-=v.y; z-=v.z;
    }

    public void minus_Equal(double s){
        x-=s; y-=s; z-=s;
    }

    public void multiply_Equal(Vec3 v){
        x*=v.x; y*=v.y; z*=v.z;
    }

    public void multiply_Equal(double s){
        x*=s; y*=s; z*=s;
    }

    public void divide_Equal(Vec3 v){
        x/=v.x; y/=v.y; z/=v.z;
    }

    public void divide_Equal(double s){
        x/=s; y/=s; z/=s;
    }

    public Vec3 add(Vec3 v){
        return new Vec3(x+v.x, y+v.y, z+v.z);
    }

    public Vec3 add(double s){
        return new Vec3(x+s, y+s, z+s);
    }

    public Vec3 minus(Vec3 v){
        return new Vec3(x-v.x, y-v.y, z-v.z);
    }

    public Vec3 minus(double s){
        return new Vec3(x-s, y-s, z-s);
    }

    public Vec3 multiply(Vec3 v){
        return new Vec3(x*v.x, y*v.y, z*v.z);
    }

    public Vec3 multiply(double s){
        return new Vec3(x*s, y*s, z*s);
    }

    public Vec3 divide(Vec3 v){
        return new Vec3(x/v.x, y/v.y, z/v.z);
    }

    public Vec3 divide(double s){
        return new Vec3(x/s, y/s, z/s);
    }

    public double dot(Vec3 v) {
        return x * v.x + y * v.y + z * v.z;
    }

    public Vec3 cross(Vec3 v) {
        return new Vec3(y * v.z - z * v.y,
                z * v.x - x * v.z,
                x * v.y - y * v.x);
    }

    public Vec3 pow(Vec3 v){
        return new Vec3(Math.pow(x,v.x), Math.pow(y,v.y), Math.pow(z,v.z));
    }

    public Vec3 pow(double d){
        return new Vec3(Math.pow(x,d), Math.pow(y,d), Math.pow(z,d));
    }

    public static Vec3 abs(Vec3 v){
        return new Vec3(Math.abs(v.x), Math.abs(v.y), Math.abs(v.z));
    }

    public static Vec3 Exp(Vec3 v) {
        return new Vec3(Math.exp(v.x), Math.exp(v.y), Math.exp(v.z));
    }

    public static Vec3 unit_vector(Vec3 v)
    {
        return v.divide(v.length());
    }

    public static Vec3 random()
    {
        return new Vec3(random_double(), random_double(), random_double());
    }

    public static Vec3 random(double min, double max)
    {
        return new Vec3(random_double(min,max),
                        random_double(min,max),
                        random_double(min,max));
    }

    public static Vec3 random_in_unit_sphere()
    {
        while(true)
        {
            Vec3 p = random(-1,1);
            if(p.length_squared() >= 1) continue;
            return p;
        }
    }

    public static Vec3 random_unit_vector()
    {
        return unit_vector(random_in_unit_sphere());
    }

    public static Vec3 random_in_hemisphere(Vec3 normal) {
        Vec3 in_unit_sphere = random_in_unit_sphere();
        if(in_unit_sphere.dot(normal) > 0.0)
            return in_unit_sphere;
        else
            return in_unit_sphere.negative();
    }

    public boolean near_zero()
    {
        double s = 1e-8;
        return (Math.abs(x) < s)&&(Math.abs(y) < s)&&(Math.abs(z) < s);
    }

    public static Vec3 reflect(Vec3 v, Vec3 n)
    {
        double vn = v.dot(n);
        return v.minus(n.multiply(2*vn));
    }

    public static Vec3 refract(Vec3 uv, Vec3 n, double etai_over_etat)
    {
        double cos_theta = Math.min(uv.negative().dot(n), 1.0);
        Vec3 r_out_perp = uv.add(n.multiply(cos_theta)).multiply(etai_over_etat);
        Vec3 r_out_parallel = n.multiply(-Math.sqrt(Math.abs(1.0 - r_out_perp.length_squared())));
        return r_out_perp.add(r_out_parallel);
    }

    public static Vec3 random_in_unit_disk()
    {
        while(true)
        {
            Vec3 p = new Vec3(random_double(-1,1), random_double(-1,1), 0);
            if(p.length_squared() >= 1) continue;
            return p;
        }
    }
}