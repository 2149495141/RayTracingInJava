package RTOneWeekend.HitTable;

import RTOneWeekend.Ray;
import RTOneWeekend.HitInfo.HitRecord;
import RTOneWeekend.HitInfo.Interval;

import java.util.ArrayList;

public class HitTableList implements HitTable {
    public ArrayList<HitTable> objects;

    public HitTableList(){
        objects = new ArrayList<>();
    }

    public void add(HitTable object)
    {
        objects.add(object);
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
}
