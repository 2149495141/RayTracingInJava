package RTRestOfYourLife.HitTable;

import RTRestOfYourLife.HitInfo.HitRecord;
import RTRestOfYourLife.HitInfo.Interval;
import RTRestOfYourLife.HitTable.AABB;
import RTRestOfYourLife.HitTable.HitTable;
import RTRestOfYourLife.Point;
import RTRestOfYourLife.Ray;
import RTRestOfYourLife.Vec3;

import java.util.ArrayList;
import java.util.List;

import static RTRestOfYourLife.RTUtil.random_int;

public class HitTableList implements HitTable {
    public List<HitTable> objects;
    AABB box;

    public HitTableList(){
        objects = new ArrayList<>();
        box = new AABB();
    }
    public HitTableList(HitTable object) {
        objects = new ArrayList<>();
        objects.add(object);
        box = new AABB();
    }

    public void add(HitTable object)
    {
        objects.add(object);
        box = new AABB(box, object.bounding_box());
    }

    public void clear()
    {
        objects.clear();
    }

    @Override
    public boolean hit(Ray r, Interval t, HitRecord rec) {
        boolean hit_anything = false;
        for (HitTable object : objects) {
            if (object.hit(r, t, rec)) {
                t = new Interval(t.min(), rec.t);
                hit_anything = true;
            }
        }

        return hit_anything;
    }

    @Override
    public AABB bounding_box() {
        return box;
    }

    @Override
    public double pdf_value(Point o, Vec3 v) {
        double weight = 1.0/objects.size();
        double sum = 0.0;

        for (HitTable object : objects)
            sum += weight * object.pdf_value(o, v);

        return sum;
    }

    @Override
    public Vec3 random(Vec3 o) {
        int size = objects.size();
        return objects.get(random_int(0, size)).random(o);
    }
}
