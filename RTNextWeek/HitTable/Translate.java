package RTNextWeek.HitTable;

import RTNextWeek.HitInfo.HitRecord;
import RTNextWeek.HitInfo.Interval;
import RTNextWeek.Ray;
import RTNextWeek.Vec3;

public class Translate implements HitTable{
    HitTable object;
    Vec3 offset;
    AABB box;

    public Translate(HitTable o, Vec3 displacement){
        object = o;
        offset = displacement;
        box = object.bounding_box().add_offset(displacement);
    }

    @Override
    public boolean hit(Ray r, Interval t, HitRecord rec) {
        Ray offset_r = new Ray(r.orig().minus(offset), r.dir(), r.time());

        if (!object.hit(offset_r, t, rec))
            return false;

        rec.p.plus_Equal(offset);

        return true;
    }

    @Override
    public AABB bounding_box() {
        return box;
    }
}
