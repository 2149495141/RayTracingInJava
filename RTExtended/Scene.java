package RTExtended;

import RTExtended.HitTable.*;
import RTExtended.Material.*;
import RTExtended.Texture.*;

import static RTExtended.HitTable.Quad.box;
import static RTExtended.RTUtil.random_double;

public class Scene {
    Camera camera;
    HitTable world;

    Scene(Camera camera, HitTable world){
        this.camera = camera;
        this.world = world;
    }

    public static Scene initScene(){
        Point lookFrom =new Point(0, 0, 0);
        Point lookAt = new Point(0, 0, -1);
        Vec3 vup = new Vec3(0, 1, 0);
        Vec3 lookLength = lookFrom.minus(lookAt);
        double dist_to_focus = lookLength.length();

        Camera camera = new Camera(lookFrom, lookAt, vup, 90, 0.02, dist_to_focus, 16/9.0);

        HitTableList world = new HitTableList();

        Material ground_material = new Lambertian(new Color(0.8, 0.8, 0));
        Material center_material = new Lambertian(new Color(/*0.9,0.9,1.0*/0.8, 0.3, 0.3));
        Material right_material = new Metal(new Color(0.8, 0.8, 0.8), 0.2);
        Material left_material = new Dielectric(new Color(1.0, 1.0, 1.0), 1.6);

        Sphere ground = new Sphere(new Point(0, -100.5, -1), 100, ground_material);
        Sphere SphereC = new Sphere(new Point(0.0, 0.0, -1.0), 0.5, center_material);
        Sphere SphereR = new Sphere(new Point(1.0, 0.0, -1.0), 0.5, right_material);
        Sphere SphereL = new Sphere(new Point(-1.0, 0.0, -1.0), 0.5, left_material);

        world.add(ground);
        world.add(SphereC);
        world.add(SphereR);
        world.add(SphereL);

        return new Scene(camera, world);
    }

    public static Scene cube(){
        Point lookFrom =new Point(1.8,1.8,1.8);
        Point lookAt = new Point(0, 0, 0);
        Vec3 vup = new Vec3(0, 1, 0);
        Vec3 lookLength = lookFrom.minus(lookAt);
        double dist_to_focus = lookLength.length();

        Camera camera = new Camera(lookFrom, lookAt, vup, 60, 0.02, dist_to_focus, 16/9.0);

        HitTableList world = new HitTableList();

        Texture checker = new CheckerTexture(0.4, new Color(0.2,0.3,0.1), new Color(0.9,0.9,0.9));
        Material ground_material = new Lambertian(checker);
        Sphere ground = new Sphere(new Point(0, -10000.5, -1), 10000, ground_material);
        Sphere SphereL = new Sphere(new Point(-10000, 7000, -10000), 3000, new DiffuseLight(new Color(15)));

        HitTable box = box(new Point(-0.5, -0.5, -0.5), new Point(0.5, 0.5, 0.5), new Dielectric(new Color(1), 1.45));

        world.add(ground);
        world.add(box);
        world.add(new Volume(box, 4, new Color(0), new Color(0.9,0.3,0.3)));
        world.add(SphereL);

        return new Scene(camera, world);
    }

    public static Scene random_sphere_scene(){
        Point lookFrom =new Point(-13, 2, 5);
        Point lookAt = new Point(0, 0, 0);
        Vec3 vup = new Vec3(0, 1, 0);
        Vec3 lookLength = lookFrom.minus(lookAt);
        double dist_to_focus = lookLength.length();

        Camera camera = new Camera(lookFrom, lookAt, vup, 20, 0.05, dist_to_focus, 16/9.0);


        HitTableList world = new HitTableList();

        Material ground_material = new Lambertian(new Color(0.5));
        Sphere ground = new Sphere(new Point(0, -1000, 0), 1000, ground_material);
        world.add(ground);
        for (int a=-11; a<11; ++a){
            for (int b=-11; b<11; ++b){
                double choose_mat = random_double();
                Point center = new Point(a + 0.9* random_double(), 0.2, b + 0.9*random_double());

                if (center.minus(new Point(4, 0.2, 0)).length() > 0.9) {
                    Material sphere_material;

                    if (choose_mat < 0.8) {
                        // diffuse
                        Color albedo = new Color(Vec3.random());
                        sphere_material = new Lambertian(albedo);
                        world.add(new Sphere(center, 0.2, sphere_material));
                    } else if (choose_mat < 0.95) {
                        // metal
                        Color albedo = new Color(Vec3.random(0.5, 1));
                        double fuzz = random_double(0, 0.5);
                        sphere_material = new Metal(albedo, fuzz);
                        world.add(new Sphere(center, 0.2, sphere_material));
                    } else {
                        // glass
                        sphere_material = new Dielectric(new Color(1),1.6);
                        world.add(new Sphere(center, 0.2, sphere_material));
                    }
                }
            }
        }
        Material material1 = new Dielectric(new Color(1),1.4);
        world.add(new Sphere(new Point(0, 1, 0), 1.0, material1));

        Material material2 = new Specular(new Color(Vec3.random()), 1.4);
        world.add(new Sphere(new Point(-4, 1, 0), 1.0, material2));

        Material material3 = new Metal(new Color(0.7, 0.6, 0.5), 0.0);
        world.add(new Sphere(new Point(4, 1, 0), 1.0, material3));

        Material light = new DiffuseLight(new Color(15));
        world.add(new Sphere(new Point(20, 100, 20), 15.0, light));

        return new Scene(camera, new BVH(world.objects));
    }

    public static Scene reimu_texture_box(){
        Point lookFrom =new Point(4, 0, 4);
        Point lookAt = new Point(0, 0, 0);
        Vec3 vup = new Vec3(0, 1, 0);
        Vec3 lookLength = lookFrom.minus(lookAt);
        double dist_to_focus = lookLength.length();

        Camera camera = new Camera(lookFrom, lookAt, vup, 40, 0.02, dist_to_focus, 16/9.0);

        HitTableList world = new HitTableList();

        Texture reimu_texture = new ImageTexture("OBJFile/reimu_box/reimu_box.png");
        Mesh3DModel reimu = OBJ.loadToMeshModel("OBJFile/reimu_box/reimu_box.obj", new Metal(reimu_texture, 0.5));
        Sphere ground = new Sphere(new Point(0, -100.5, -1), 100, new Lambertian(new Color(0.8,0.8,0.0)));

        world.add(new BVH(reimu.TrianglesData));
        world.add(ground);

        return new Scene(camera, world);
    }

    public static Scene stanford_dragon(){
        Point lookFrom =new Point(0, 0, 3);
        Point lookAt = new Point(0, 0, 0);
        Vec3 vup = new Vec3(0, 1, 0);
        Vec3 lookLength = lookFrom.minus(lookAt);
        double dist_to_focus = lookLength.length();

        Camera camera = new Camera(lookFrom, lookAt, vup, 30, 0.02, dist_to_focus, 16/9.0);


        HitTableList world = new HitTableList();
        Texture pertext = new NoiseTexture(new Color(0.4, 0.8, 0.6),2);
        Mesh3DModel dragon = OBJ.loadToMeshModel("OBJFile/dragon_low.obj", new Specular(pertext, 1.4));
        dragon.scale(5);
        dragon.translate(0, -0.5,0);

        Texture checker = new CheckerTexture(0.4, new Color(0.2,0.3,0.1), new Color(0.9,0.9,0.9));
        Sphere ground = new Sphere(new Point(0, -100.5, -1), 100, new Lambertian(checker));

        world.add(new BVH(dragon.TrianglesData));
        world.add(ground);

        return new Scene(camera, world);
    }

    public static Scene volume_dragon(){
        Point lookFrom =new Point(0, 0, 3);
        Point lookAt = new Point(0, 0, 0);
        Vec3 vup = new Vec3(0, 1, 0);
        Vec3 lookLength = lookFrom.minus(lookAt);
        double dist_to_focus = lookLength.length();

        Camera camera = new Camera(lookFrom, lookAt, vup, 30, 0.02, dist_to_focus, 16/9.0);

        HitTableList world = new HitTableList();
        Mesh3DModel dragon = OBJ.loadToMeshModel("OBJFile/dragon_low.obj", new Dielectric(new Color(1), 1.5));
        dragon.scale(5);
        dragon.translate(0, -0.5,0);

        Texture checker = new CheckerTexture(0.4, new Color(0.2,0.3,0.1), new Color(0.9,0.9,0.9));
        Sphere ground = new Sphere(new Point(0, -100.5, -1), 100, new Lambertian(checker));

        world.add(new Sphere(new Point(502, 50, 100), 100.0, new DiffuseLight(new Color(50))));
        world.add(new Volume(new BVH(dragon.TrianglesData), 32, new Color(0.8, 0.5, 0.2), new Color(0.2,0.4,0.8)));
        world.add(ground);

        return new Scene(camera, world);
    }

    public static Scene mary(){
        Point lookFrom =new Point(0, 1.2, 8);
        Point lookAt = new Point(0, 1.2, 0);
        Vec3 vup = new Vec3(0, 1, 0);
        Vec3 lookLength = lookFrom.minus(lookAt);
        double dist_to_focus = lookLength.length();

        Camera camera = new Camera(lookFrom, lookAt, vup, 30, 0.02, dist_to_focus, 16/9.0);

        HitTableList world = new HitTableList();

        Texture mary_texture = new ImageTexture("OBJFile/mary/MC003_Kozakura_Mari.png");
        Mesh3DModel mary = OBJ.loadToMeshModel("OBJFile/mary/Marry.obj", new Lambertian(mary_texture));
        mary.translate(0, -0.5, 0);
        Sphere ground = new Sphere(new Point(0, -100.5, -1), 100, new Lambertian(new Color(0.8,0.8,0.0)));

        world.add(new BVH(mary.TrianglesData));
        world.add(ground);

        return new Scene(camera, world);
    }

    public static Scene reimu_fumo(){
        Point lookFrom =new Point(0, 0.8, -8);
        Point lookAt = new Point(0, 0.8, 0);
        Vec3 vup = new Vec3(0, 1, 0);
        Vec3 lookLength = lookFrom.minus(lookAt);
        double dist_to_focus = lookLength.length();

        Camera camera = new Camera(lookFrom, lookAt, vup, 30,
                0.02, dist_to_focus, 16/9.0);

        HitTableList world = new HitTableList();

        Texture reimu_texture = new ImageTexture("OBJFile/reimu/reimu_low_Material_u1_v1_baseColor.png");
        Mesh3DModel reimu = OBJ.loadToMeshModel("OBJFile/reimu/reimu.obj", new Lambertian(reimu_texture));
        reimu.scale(0.1);
        reimu.rotate(-30, 1);
        reimu.translate(-0.5, -0.5, 0);

        Sphere light = new Sphere(new Point(0, 1000, -500), 100.0, new DiffuseLight(new Color(30)));
        Sphere ground = new Sphere(new Point(0, -1000.5, -1), 1000, new Lambertian(new Color(0.8,0.8,0.8)));

        world.add(new BVH(reimu.TrianglesData));
        world.add(light);
        world.add(ground);

        return new Scene(camera, world);
    }

    public static Scene fumo_meeting(){
        Point lookFrom =new Point(0, 1, -10);
        Point lookAt = new Point(0, 0.8, 0);
        Vec3 vup = new Vec3(0, 1, 0);
        Vec3 lookLength = lookFrom.minus(lookAt);
        double dist_to_focus = lookLength.length();

        Camera camera = new Camera(lookFrom, lookAt, vup, 30,
                0.02, dist_to_focus, 16/9.0);

        HitTableList world = new HitTableList();

        Texture reimu_texture = new ImageTexture("OBJFile/reimu/reimu_low_Material_u1_v1_baseColor.png");
        Mesh3DModel reimu = OBJ.loadToMeshModel("OBJFile/reimu/reimu.obj", new Lambertian(reimu_texture));
        reimu.scale(0.1);
        reimu.rotate(-30, 1);
        reimu.translate(-0.5, -0.5, 0);

        Texture cirno_texture = new ImageTexture("OBJFile/cirno/cirno_low_Material_u1_v1_baseColor.jpeg");
        Mesh3DModel cirno = OBJ.loadToMeshModel("OBJFile/cirno/cirno.obj", new Lambertian(cirno_texture));
        cirno.scale(0.09);
        cirno.rotate(80,1);
        cirno.translate(-1.7, -0.5, 0.8);

        Texture sakuya_texture = new ImageTexture("OBJFile/sakuya/sakuya_low_Material_u1_v1_baseColor.png");
        Mesh3DModel sakuya = OBJ.loadToMeshModel("OBJFile/sakuya/sakuya.obj", new Lambertian(sakuya_texture));
        sakuya.scale(0.08);
        sakuya.rotate(120,1);
        sakuya.translate(2.3, 0.3, 0.5);

        Texture yuyuko_texture = new ImageTexture("OBJFile/yuyuko/yuyu_low_Material_u1_v1_baseColor.jpeg");
        Mesh3DModel yuyuko = OBJ.loadToMeshModel("OBJFile/yuyuko/yuyuko.obj", new Lambertian(yuyuko_texture));
        yuyuko.scale(0.09);
        yuyuko.rotate(30,1);
        yuyuko.translate(-3.5, -0.55, 0);

        Texture shion_texture = new ImageTexture("OBJFile/shion_yorigami/Shion_low_Material_u1_v1_baseColor.png");
        Mesh3DModel shion = OBJ.loadToMeshModel("OBJFile/shion_yorigami/shion.obj", new Lambertian(shion_texture));
        shion.scale(0.085);
        shion.rotate(220,1);
        shion.rotate(-15, 2);
        shion.translate(3.8, 0.2, -0.5);

        Sphere light = new Sphere(new Point(0, 1000, -500), 100.0, new DiffuseLight(new Color(30)));
        Sphere ground = new Sphere(new Point(0, -1000.5, -1), 1000, new Lambertian(new Color(0.8,0.8,0.8)));

        world.add(new BVH(reimu.TrianglesData));
        world.add(new BVH(cirno.TrianglesData));
        world.add(new BVH(sakuya.TrianglesData));
        world.add(new BVH(yuyuko.TrianglesData));
        world.add(new BVH(shion.TrianglesData));
        world.add(ground);
        world.add(light);


        return new Scene(camera, world);
    }
}

