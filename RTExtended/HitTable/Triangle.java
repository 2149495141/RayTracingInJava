package RTExtended.HitTable;

import RTExtended.HitInfo.HitRecord;
import RTExtended.HitInfo.Interval;
import RTExtended.Point;
import RTExtended.Ray;
import RTExtended.Vec2;
import RTExtended.Vec3;
import RTExtended.Material.Material;

import static RTExtended.RTUtil.infinity;
import static java.lang.Math.*;

public class Triangle implements HitTable{
    public Vec3[] vertexes;
    public Vec2[] uvVertex;
    Vec3 e1, e2, normal;
    Material mat;
    AABB box;

    public Triangle(Vec3[] vertexes, Material material){
        this.vertexes = vertexes;
        mat = material;
        e1 = vertexes[1].minus(vertexes[0]); //求三角形边1
        e2 = vertexes[2].minus(vertexes[0]); //求三角形边2
        normal = Vec3.unit_vector(e1.cross(e2)); //计算法线
    }
    public Triangle(Vec3[] vertexes, Vec2[] uvData, Material m){
        this.vertexes = vertexes;
        this.uvVertex = uvData;
        mat = m;
        e1 = vertexes[1].minus(vertexes[0]); //求三角形边1
        e2 = vertexes[2].minus(vertexes[0]); //求三角形边2
        normal = Vec3.unit_vector(e1.cross(e2)); //计算法线

    }
    public Triangle(Vec3 v1, Vec3 v2, Vec3 v3, Vec2[] uvData, Material m){
        this.vertexes = new Vec3[]{v1, v2, v3};
        this.uvVertex = uvData;
        mat = m;
        e1 = vertexes[1].minus(vertexes[0]); //求三角形边1
        e2 = vertexes[2].minus(vertexes[0]); //求三角形边2
        normal = Vec3.unit_vector(e1.cross(e2)); //计算法线

    }


    private Vec2 calculateObjTextureUV(double u, double v){
        //obj纹理坐标本质上就是一个在二维图像上的小三角形，但顶点值的范围被限制在[0,1]之间
        //只要对uv顶点与原三角形uv值做一遍重心坐标插值就能求出对应的纹理采样uv坐标
        return  (uvVertex[2].multiply(u))
                .add(uvVertex[1].multiply(v))
                .add(uvVertex[0].multiply((1-u-v)));
    }

    private void convertToObjUV(HitRecord rec){
        if (uvVertex != null){
            Vec2 uv = calculateObjTextureUV(rec.u, rec.v);
            rec.u = uv.x();
            rec.v = uv.y();
        }
    }

    @Override
    public boolean hit(Ray r, Interval it, HitRecord rec) {
        if(abs(normal.dot(r.dir())) < 1e-8)
            return false;

        Vec3 s0  = r.orig().minus(vertexes[0]); //计算出平面点
        Vec3 s1 = r.dir().cross(e2);
        Vec3 s2 = s0.cross(e1);

        double s1e1 = s1.dot(e1);
        double t = s2.dot(e2) / s1e1;

        if (t < it.min()||t > it.max())
            return false;

        double b1 = s1.dot(s0) / s1e1; //S dot S1 * 1/S1E1
        if (b1 > 0) {
            double b2 = s2.dot(r.dir()) / s1e1; //D dot S2 * 1/S1E1
            if (b2 > 0) {
                if ((1-b1-b2) > 0) {  //当b1,b2,(1-b1-b2)都是非负数时，则光线与三角形内有交点
                    rec.t = t;
                    rec.u = b2;
                    rec.v = b1;
                    this.convertToObjUV(rec);
                    rec.p = r.at(t);
                    rec.set_face_normal(r, normal);
                    rec.mat = mat;

                    return true;
                } else return false;
            } else return false;
        }
        return false;

        /*对存储更友好的方法,来源于:“Fast, Minimum Storage Ray-Triangle Intersection”*/
//        final double epsilon = 1e-8;
//		if(Math.abs(normal.dot(r.dir())) < epsilon)
//			return false;
//
//		Vec3 pVec = r.dir().cross(e2);
//		double det = e1.dot(pVec);
//		if (det > -epsilon && det < epsilon)
//			return false;
//
//		double invDet = 1 / det;
//		Vec3 tVec = r.orig().minus(vertexes[0]);
//		double u = tVec.dot(pVec)*invDet;
//		if (u < 0 || u > 1 )
//			return false;
//
//		Vec3 qVec = tVec.cross(e1);
//		double v = r.dir().dot(qVec)*invDet;
//		if (v < 0 || u+v > 1)
//			return false;
//
//		double t = e2.dot(qVec)*invDet;
//		if(t < it.min()||t > it.max()) //确保取最近交点t，防止自遮挡和物体之间的遮挡错误
//			return false;
//
//		rec.t = t;
//		rec.u = u;
//		rec.v = v;
//        convertToObjUV(rec);
//		rec.set_face_normal(r,normal);
//		rec.mat = mat;
//		rec.p = r.at(t);
//
//		return true;
    }

    @Override
    public AABB bounding_box() {
        Point min = new Point(infinity, infinity, infinity);
        Point max = new Point(-infinity, -infinity, -infinity);

        for (int i = 0; i < 3; ++i) {
            min = new Point(min(min.x, vertexes[i].x),
                            min(min.y, vertexes[i].y),
                            min(min.z, vertexes[i].z));
            max = new Point(max(max.x, vertexes[i].x),
                            max(max.y, vertexes[i].y),
                            max(max.z, vertexes[i].z));
        }

        return new AABB(min.minus(1e-8), max.add(1e-8));
    }
}
