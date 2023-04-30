package RTRestOfYourLife.PDF;

import RTRestOfYourLife.Vec3;

import static RTRestOfYourLife.RTUtil.random_double;

public class MixturePDF implements PDF{
    PDF[] p;

    public MixturePDF(PDF pdf1, PDF pdf2){
        p = new PDF[]{pdf1, pdf2};
    }

    @Override
    public double value(Vec3 direction) {
        return 0.5 * p[0].value(direction) + 0.5 *p[1].value(direction);
    }

    @Override
    public Vec3 generate() {
        if (random_double() < 0.5)
            return p[0].generate();
        else
            return p[1].generate();
    }
}
