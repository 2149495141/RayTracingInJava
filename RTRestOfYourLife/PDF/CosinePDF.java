package RTRestOfYourLife.PDF;

import RTRestOfYourLife.ONB;
import RTRestOfYourLife.Vec3;

import static RTRestOfYourLife.RTUtil.pi;;

public class CosinePDF implements PDF{
    ONB uvw;

    public CosinePDF(Vec3 w){
        uvw = ONB.buildFromW(w);
    }

    @Override
    public double value(Vec3 direction) {
        double cosine_theta = Vec3.unit_vector(direction).dot(uvw.w);
        return Math.max(0, cosine_theta/pi);
    }

    @Override
    public Vec3 generate() {
        return uvw.local(Vec3.random_cosine_direction());
    }
}
