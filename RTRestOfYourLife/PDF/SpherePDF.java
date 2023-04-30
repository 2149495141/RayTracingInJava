package RTRestOfYourLife.PDF;

import RTRestOfYourLife.Vec3;

import static RTRestOfYourLife.RTUtil.pi;

public class SpherePDF implements PDF{

    @Override
    public double value(Vec3 direction) {
        return 1/ (4 * pi);
    }

    @Override
    public Vec3 generate() {
        return Vec3.random_unit_vector();
    }
}
