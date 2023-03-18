package RTNextWeek.HitTable;

import RTNextWeek.HitInfo.HitRecord;
import RTNextWeek.HitInfo.Interval;
import RTNextWeek.Ray;

import java.util.ArrayList;
import java.util.List;

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
}
