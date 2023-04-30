package RTRestOfYourLife.PDF;

import RTRestOfYourLife.HitTable.HitTable;
import RTRestOfYourLife.Point;
import RTRestOfYourLife.Vec3;

public class HitTablePDF implements PDF{
    HitTable objects;
    Point origin;

    public HitTablePDF(HitTable objects, Point origin){
        this.objects = objects;
        this.origin = origin;
    }

    @Override
    public double value(Vec3 direction) {
        return objects.pdf_value(origin,direction);
    }

    @Override
    public Vec3 generate() {
        return objects.random(origin);
    }
}
