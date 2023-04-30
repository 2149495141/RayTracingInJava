package RTRestOfYourLife.PDF;

import RTRestOfYourLife.Vec3;

import static RTRestOfYourLife.RTUtil.pi;

public interface PDF {
    default double value(Vec3 direction) { return 0; }

    default Vec3 generate() { return new Vec3(0); }
}
