package RTExtended.HitTable;

import RTExtended.HitInfo.HitRecord;
import RTExtended.HitInfo.Interval;
import RTExtended.Ray;
import RTExtended.Vec3;

import java.util.ArrayList;
import java.util.List;

import static RTExtended.RTUtil.degrees_to_radians;
import static java.lang.Math.*;

public class Mesh3DModel implements HitTable{
    public List<HitTable> TrianglesData;
    AABB box;

    Mesh3DModel(){
        TrianglesData = new ArrayList<>();
        box = new AABB();
    }
    public Mesh3DModel(List<HitTable> data){
        TrianglesData = new ArrayList<>(data);
    }

    public void add(Triangle object)
    {
        TrianglesData.add(object);
        box = new AABB(box, object.bounding_box());
    }

    @Override
    public boolean hit(Ray r, Interval t, HitRecord rec) {
        boolean hit_anything = false;
        for (HitTable object : TrianglesData) {
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

    public void scale(double s){
        box = new AABB();
        for (int i = 0; i < TrianglesData.size(); ++i) {
            if (TrianglesData.get(i) instanceof Triangle tri) {
                Vec3[] new_vertexes = new Vec3[]{
                        tri.vertexes[0].multiply(s),
                        tri.vertexes[1].multiply(s),
                        tri.vertexes[2].multiply(s)
                };
                TrianglesData.set(i, new Triangle(new_vertexes, tri.uvVertex, tri.mat));
                box = new AABB(box, TrianglesData.get(i).bounding_box());
            }
        }
    }

    public void mirror(int axis){
        box = new AABB();
        for (int i = 0; i < TrianglesData.size(); ++i) {
            if (TrianglesData.get(i) instanceof Triangle tri) {
                switch(axis) {
                    case 0 -> {
                        Vec3[] new_vertexes = new Vec3[]{
                                new Vec3(tri.vertexes[0].x *=-1, tri.vertexes[0].y, tri.vertexes[0].z),
                                new Vec3(tri.vertexes[1].x *=-1, tri.vertexes[1].y, tri.vertexes[1].z),
                                new Vec3(tri.vertexes[2].x *=-1, tri.vertexes[2].y, tri.vertexes[2].z),
                        };
                        TrianglesData.set(i, new Triangle(new_vertexes, tri.uvVertex, tri.mat));
                        box = new AABB(box, TrianglesData.get(i).bounding_box());
                    }

                    case 1 -> {
                        Vec3[] new_vertexes = new Vec3[]{
                                new Vec3(tri.vertexes[0].x, tri.vertexes[0].y *=-1, tri.vertexes[0].z),
                                new Vec3(tri.vertexes[1].x, tri.vertexes[1].y *=-1, tri.vertexes[1].z),
                                new Vec3(tri.vertexes[2].x, tri.vertexes[2].y *=-1, tri.vertexes[2].z),
                        };
                        TrianglesData.set(i, new Triangle(new_vertexes, tri.uvVertex, tri.mat));
                        box = new AABB(box, TrianglesData.get(i).bounding_box());
                    }

                    case 2 -> {
                        Vec3[] new_vertexes = new Vec3[]{
                                new Vec3(tri.vertexes[0].x, tri.vertexes[0].y, tri.vertexes[0].z *=-1),
                                new Vec3(tri.vertexes[1].x, tri.vertexes[1].y, tri.vertexes[1].z *=-1),
                                new Vec3(tri.vertexes[2].x, tri.vertexes[2].y, tri.vertexes[2].z *=-1),
                        };
                        TrianglesData.set(i, new Triangle(new_vertexes, tri.uvVertex, tri.mat));
                        box = new AABB(box, TrianglesData.get(i).bounding_box());
                    }
                }
            }
        }
    }

    public void translate(double x, double y, double z){
        box = new AABB();
        Vec3 move_vec = new Vec3(x,y,z);
        for (int i = 0; i < TrianglesData.size(); ++i) {
            if (TrianglesData.get(i) instanceof Triangle tri) {
                ((Triangle) TrianglesData.get(i)).vertexes[0] = tri.vertexes[0].add(move_vec);
                ((Triangle) TrianglesData.get(i)).vertexes[1] = tri.vertexes[1].add(move_vec);
                ((Triangle) TrianglesData.get(i)).vertexes[2] = tri.vertexes[2].add(move_vec);
                box = new AABB(box, TrianglesData.get(i).bounding_box());
            }
        }
    }

    public void rotate(double angle, int axis){
        box =  new AABB();

        double radians = degrees_to_radians(angle);

        double sin_theta = sin(radians);
        double cos_theta = cos(radians);

        //先取模型的最大顶点（离原点最远）
        Vec3 max_p = new Vec3(box.x.max(), box.y.max(), box.z.max());
        //然后取模型最小顶点（离原点最近）
        Vec3 min_p = new Vec3(box.x.min(), box.y.min(), box.z.min());
        //把最远点减去最近点（求两点之间的距离），得出模型的大小
        Vec3 model_distance_size = max_p.minus(min_p);
        //把模型大小除以2，得出模型的中点
        Vec3 model_mid = model_distance_size.divide(2);
        //几乎所有旋转公式都是绕原点来旋转顶点，所以需要把模型中心先移动到原点
        for (int i = 0; i < TrianglesData.size(); ++i) {
            if (TrianglesData.get(i) instanceof Triangle tri) {
                Vec3[] new_vertexes = new Vec3[]{
                        new Vec3(tri.vertexes[0].x, tri.vertexes[0].y, tri.vertexes[0].z),
                        new Vec3(tri.vertexes[1].x, tri.vertexes[1].y, tri.vertexes[1].z),
                        new Vec3(tri.vertexes[2].x, tri.vertexes[2].y, tri.vertexes[2].z),
                };
                //遍历顶点，把它们都减去（最小点+模型中点）或（最大点-模型中点）
                tri.vertexes[0] = tri.vertexes[0].minus(min_p.add(model_mid));
                tri.vertexes[1] = tri.vertexes[1].minus(min_p.add(model_mid));
                tri.vertexes[2] = tri.vertexes[2].minus(min_p.add(model_mid));
                //然后使用旋转公式，计算哪个顶点该移到哪个位置
                switch (axis){
                    case 0 -> {
                        new_vertexes[0].y = tri.vertexes[0].y * cos_theta - tri.vertexes[0].z * sin_theta;
                        new_vertexes[0].z = tri.vertexes[0].z * cos_theta + tri.vertexes[0].y * sin_theta;

                        new_vertexes[1].y = tri.vertexes[1].y * cos_theta - tri.vertexes[1].z * sin_theta;
                        new_vertexes[1].z = tri.vertexes[1].z * cos_theta + tri.vertexes[1].y * sin_theta;

                        new_vertexes[2].y = tri.vertexes[2].y * cos_theta - tri.vertexes[2].z * sin_theta;
                        new_vertexes[2].z = tri.vertexes[2].z * cos_theta + tri.vertexes[2].y * sin_theta;
                    }

                    case 1 -> {
                        new_vertexes[0].x = tri.vertexes[0].x * cos_theta - tri.vertexes[0].z * sin_theta;
                        new_vertexes[0].z = tri.vertexes[0].z * cos_theta + tri.vertexes[0].x * sin_theta;

                        new_vertexes[1].x = tri.vertexes[1].x * cos_theta - tri.vertexes[1].z * sin_theta;
                        new_vertexes[1].z = tri.vertexes[1].z * cos_theta + tri.vertexes[1].x * sin_theta;

                        new_vertexes[2].x = tri.vertexes[2].x * cos_theta - tri.vertexes[2].z * sin_theta;
                        new_vertexes[2].z = tri.vertexes[2].z * cos_theta + tri.vertexes[2].x * sin_theta;
                    }

                    case 2 -> {
                        new_vertexes[0].x = tri.vertexes[0].x * cos_theta - tri.vertexes[0].y * sin_theta;
                        new_vertexes[0].y = tri.vertexes[0].y * cos_theta + tri.vertexes[0].x * sin_theta;

                        new_vertexes[1].x = tri.vertexes[1].x * cos_theta - tri.vertexes[1].y * sin_theta;
                        new_vertexes[1].y = tri.vertexes[1].y * cos_theta + tri.vertexes[1].x * sin_theta;

                        new_vertexes[2].x = tri.vertexes[2].x * cos_theta - tri.vertexes[2].y * sin_theta;
                        new_vertexes[2].y = tri.vertexes[2].y * cos_theta + tri.vertexes[2].x * sin_theta;
                    }
                }
                //最后把模型移回原来的位置，遍历顶点都加上（最小点+模型中点）或（最大点-模型中点）
                new_vertexes[0] = new_vertexes[0].add(min_p.add(model_mid));
                new_vertexes[1] = new_vertexes[1].add(min_p.add(model_mid));
                new_vertexes[2] = new_vertexes[2].add(min_p.add(model_mid));

                TrianglesData.set(i, new Triangle(new_vertexes, tri.uvVertex, tri.mat));
                box = new AABB(box, TrianglesData.get(i).bounding_box());
            }
        }
    }
}
