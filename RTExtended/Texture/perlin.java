package RTExtended.Texture;

import RTExtended.Point;
import RTExtended.Vec3;

import static RTExtended.RTUtil.random_int;
import static RTExtended.Vec3.random;
import static RTExtended.Vec3.unit_vector;
import static java.lang.Math.abs;
import static java.lang.Math.floor;

public class perlin {
    static final int point_count = 256;
    Vec3[] ranvec;
    int[] perm_x;
    int[] perm_y;
    int[] perm_z;

    perlin(){
        ranvec = new Vec3[point_count];
        for (int i = 0; i < point_count; ++i) {
            ranvec[i] = unit_vector(random(-1,1));
        }

        perm_x = perlin_generate_perm();
        perm_y = perlin_generate_perm();
        perm_z = perlin_generate_perm();
    }

    static int[] perlin_generate_perm() {
        int[] p = new int[point_count];

        for (int i = 0; i < point_count; i++)
            p[i] = i;

        permute(p, point_count);

        return p;
    }

    static void permute(int[] p, int n) {
        for (int i = n-1; i > 0; i--) {
            int target = random_int(0, i);
            int tmp = p[i];
            p[i] = p[target];
            p[target] = tmp;
        }
    }

    static double trilinear_interp(double c[][][], double u, double v, double w) {
        double accum = 0.0;
        for (int i=0; i < 2; i++)
            for (int j=0; j < 2; j++)
                for (int k=0; k < 2; k++)
                    accum += (i*u + (1-i)*(1-u))*
                            (j*v + (1-j)*(1-v))*
                            (k*w + (1-k)*(1-w))*c[i][j][k];

        return accum;
    }

    static double perlin_interp(Vec3 c[][][], double u, double v, double w) {
        double uu = u*u*(3-2*u);
        double vv = v*v*(3-2*v);
        double ww = w*w*(3-2*w);
        double accum = 0.0;

        for (int i=0; i < 2; i++)
            for (int j=0; j < 2; j++)
                for (int k=0; k < 2; k++) {
                    Vec3 weight_v = new Vec3(u-i, v-j, w-k);
                    accum += (i*uu + (1-i)*(1-uu))
                            * (j*vv + (1-j)*(1-vv))
                            * (k*ww + (1-k)*(1-ww))
                            * c[i][j][k].dot(weight_v);
                }

        return accum;
    }

    double noise(Point p) {
//        int i = (int) (4*p.x) & 255;
//        int j = (int) (4*p.y) & 255;
//        int k = (int) (4*p.z) & 255;
//
//        return ranfloat[perm_x[i] ^ perm_y[j] ^ perm_z[k]];
        double u = p.x - floor(p.x);
        double v = p.y - floor(p.y);
        double w = p.z - floor(p.z);
        u = u*u*(3-2*u);
        v = v*v*(3-2*v);
        w = w*w*(3-2*w);

        int i = (int) floor(p.x);
        int j = (int) floor(p.y);
        int k = (int) floor(p.z);
        Vec3[][][] c = new Vec3[2][2][2];

        for (int di=0; di < 2; di++)
            for (int dj=0; dj < 2; dj++)
                for (int dk=0; dk < 2; dk++)
                    c[di][dj][dk] = ranvec[
                                    perm_x[(i+di) & 255] ^
                                    perm_y[(j+dj) & 255] ^
                                    perm_z[(k+dk) & 255]
                            ];

        return perlin_interp(c, u, v, w);
    }

    double turb(Point p){
        double accum = 0.0;
        Point temp_p = p;
        double weight = 1.0;

        for (int i = 0; i < 7; i++) {
            accum += weight*noise(temp_p);
            weight *= 0.5;
            temp_p.multiply_Equal(2);
        }

        return abs(accum);
    }
}
