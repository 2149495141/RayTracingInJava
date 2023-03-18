package RTExperimentalIdeas.HitTable;

import RTExperimentalIdeas.HitInfo.HitRecord;
import RTExperimentalIdeas.HitInfo.Interval;
import RTExperimentalIdeas.Ray;

import java.util.Comparator;
import java.util.List;

import static RTExperimentalIdeas.RTUtil.random_int;

public class BVH implements HitTable{
    HitTable left, right, object;
    AABB box;

    public BVH(List<HitTable> objects){
        box = new AABB();
        int axis = random_int(0,2);
        int object_span = objects.size();

        if (object_span == 1)
        {
            object = objects.get(0);
            box = object.bounding_box();
        }else if (object_span == 2)
        {
            if (box_less_than(objects.get(0), objects.get(1), axis)) {
                left = objects.get(0);
                right = objects.get(1);
            } else {
                left = objects.get(1);
                right = objects.get(0);
            }
            box = new AABB(left.bounding_box(), right.bounding_box());
        }else
        {
            //排序
            objects.sort((a, b) -> {
                if(box_less_than(a, b, axis)) // if a < b
                    return 1;  //后移一位
                else if (box_greater_than(a, b, axis)) // if a > b
                    return -1; //前移一位
                else
                    return 0; //位置不变
            });

            int mid = object_span/2;
            left = new BVH(objects.subList(0, mid));
            right = new BVH(objects.subList(mid, object_span));
            box = new AABB(left.bounding_box(), right.bounding_box());
        }
    }

    boolean box_less_than(HitTable a, HitTable b, int axis_index) {
        return a.bounding_box().axis(axis_index).min() < b.bounding_box().axis(axis_index).min();
    }

    boolean box_greater_than(HitTable a, HitTable b, int axis_index) {
        return a.bounding_box().axis(axis_index).min() > b.bounding_box().axis(axis_index).min();
    }

    @Override
    public boolean hit(Ray r, Interval t, HitRecord rec) {
        if (object != null)
            return object.hit(r, t, rec);

        if (!box.hit(r, t))
            return false;

        rec.t = t.max();
        boolean hit_left = left.hit(r, new Interval(t.min(), rec.t), rec);
        boolean hit_right = right.hit(r, new Interval(t.min(), rec.t), rec);

        return hit_left || hit_right;
    }

    @Override
    public AABB bounding_box() {
        return box;
    }
}
