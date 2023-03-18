package RTNextWeek;

import RTNextWeek.HitTable.*;
import RTNextWeek.Material.*;
import RTNextWeek.Texture.CheckerTexture;
import RTNextWeek.Texture.ImageTexture;
import RTNextWeek.Texture.NoiseTexture;
import RTNextWeek.Texture.Texture;

import static RTNextWeek.HitTable.Quad.box;
import static RTNextWeek.RTUtil.random_double;

public class Scene {
    Camera camera;
    HitTable world;
    Color background;
    int image_width;
    int image_height;

    Scene(Camera camera, HitTable world){
        this.camera = camera;
        this.world = world;
        this.background = null;
    }
    Scene(Camera camera, HitTable world, Color bg_color){
        this.camera = camera;
        this.world = world;
        this.background = bg_color;
    }
    Scene(Camera camera, HitTable world, int width, int height){
        this.camera = camera;
        this.world = world;
        this.image_width = width;
        this.image_height = height;
    }

    public static Scene initScene(){
        Point lookFrom =new Point(0, 0, 0);
        Point lookAt = new Point(0, 0, -1);
        Vec3 vup = new Vec3(0, 1, 0);
        Vec3 lookLength = lookFrom.minus(lookAt);
        double dist_to_focus = lookLength.length();

        Camera camera = new Camera(lookFrom, lookAt, vup, 90,
                0.02, dist_to_focus, 16/9.0);

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
        world.add(new Medium(SphereL, 4, new Color(0.95,0.3,0.3)));

        return new Scene(camera, world);
    }

    public static Scene random_sphere_scene(){
        Point lookFrom =new Point(13, 2, 3);
        Point lookAt = new Point(0, 0, 0);
        Vec3 vup = new Vec3(0, 1, 0);
        Vec3 lookLength = lookFrom.minus(lookAt);
        double dist_to_focus = lookLength.length();

        Camera camera = new Camera(lookFrom, lookAt, vup, 20,
                0.05, dist_to_focus, 16/9.0);


        HitTableList world = new HitTableList();

        Texture checker = new CheckerTexture(0.32, new Color(0.2,0.3,0.1), new Color(0.9,0.9,0.9));
        Material ground_material = new Lambertian(checker);
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
                        world.add(new Sphere(center,0.2, sphere_material));
                    } else if (choose_mat < 0.95) {
                        // metal
                        Color albedo = new Color(Vec3.random(0.5, 1));
                        double fuzz = random_double(0, 0.5);
                        sphere_material = new Metal(albedo, fuzz);
                        world.add(new Sphere(center, 0.2, sphere_material));
                    } else {
                        // glass
                        sphere_material = new Dielectric(new Color(1),1.5);
                        world.add(new Sphere(center, 0.2, sphere_material));
                    }
                }
            }
        }
        Material material1 = new Dielectric(new Color(1), 1.5);
        world.add(new Sphere(new Point(0, 1, 0), 1.0, material1));
        //world.add(new Medium(new Sphere(new Point(0, 1, 0), 0.8, material1), 2, new Color(0.98,0.3,0.3)));

        Material material2 = new Lambertian(new Color(Vec3.random()));
        world.add(new Sphere(new Point(-4, 1, 0), 1.0, material2));

        Material material3 = new Metal(new Color(0.7, 0.6, 0.5), 0.0);
        world.add(new Sphere(new Point(4, 1, 0), 1.0, material3));


        return new Scene(camera, new HitTableList(new BVH(world.objects)));
    }

    public static Scene moving_sphere_scene(){
        Point lookFrom =new Point(13, 2, 3);
        Point lookAt = new Point(0, 0, 0);
        Vec3 vup = new Vec3(0, 1, 0);
        Vec3 lookLength = lookFrom.minus(lookAt);
        double dist_to_focus = lookLength.length();

        Camera camera = new Camera(lookFrom, lookAt, vup, 20,
                0.05, dist_to_focus, 16/9.0);


        HitTableList world = new HitTableList();

        Texture checker = new CheckerTexture(0.32, new Color(0.2,0.3,0.1), new Color(0.9,0.9,0.9));
        Material ground_material = new Lambertian(checker);
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
                        Point center2 = center.add(new Vec3(0, random_double(0,0.4), 0));
                        world.add(new MoveSphere(center, center2,0.2, sphere_material));
                    } else if (choose_mat < 0.95) {
                        // metal
                        Color albedo = new Color(Vec3.random(0.5, 1));
                        double fuzz = random_double(0, 0.5);
                        sphere_material = new Metal(albedo, fuzz);
                        world.add(new Sphere(center, 0.2, sphere_material));
                    } else {
                        // glass
                        sphere_material = new Dielectric(new Color(1),1.5);
                        world.add(new Sphere(center, 0.2, sphere_material));
                    }
                }
            }
        }
        Material material1 = new Dielectric(new Color(1), 1.5);
        world.add(new Sphere(new Point(0, 1, 0), 1.0, material1));

        Material material2 = new Lambertian(new Color(Vec3.random()));
        world.add(new Sphere(new Point(-4, 1, 0), 1.0, material2));

        Material material3 = new Metal(new Color(0.7, 0.6, 0.5), 0.0);
        world.add(new Sphere(new Point(4, 1, 0), 1.0, material3));


        return new Scene(camera, new HitTableList(new BVH(world.objects)));
    }

    public static Scene two_big_sphere(){
        Point lookFrom =new Point(13, 2, 3);
        Point lookAt = new Point(0, 0, 0);
        Vec3 vup = new Vec3(0, 1, 0);
        Vec3 lookLength = lookFrom.minus(lookAt);
        double dist_to_focus = lookLength.length();

        Camera camera = new Camera(lookFrom, lookAt, vup, 20,
                0.05, dist_to_focus, 16/9.0);

        HitTableList world = new HitTableList();

        Texture checker = new CheckerTexture(0.8, new Color(0.2,0.3,0.1), new Color(0.9,0.9,0.9));
        world.add(new Sphere(new Point(0, -10, 0), 10, new Lambertian(checker)));
        world.add(new Sphere(new Point(0, 10, 0), 10, new Lambertian(checker)));

        return new Scene(camera, world);
    }

    public static Scene earth(){
        Point lookFrom =new Point(0, 0, 12);
        Point lookAt = new Point(0, 0, 0);
        Vec3 vup = new Vec3(0, 1, 0);
        Vec3 lookLength = lookFrom.minus(lookAt);
        double dist_to_focus = lookLength.length();

        Camera camera = new Camera(lookFrom, lookAt, vup, 20,
                0.05, dist_to_focus, 16/9.0);

        Texture earth_texture = new ImageTexture("RTNextWeek/TextureImages/earthmap.jpg");
        Material earth_surface = new Lambertian(earth_texture);
        Sphere globe = new Sphere(new Point(0,0,0), 2, earth_surface);

        return new Scene(camera, new HitTableList(globe));
    }

    public static Scene two_perlin_spheres(){
        Point lookFrom =new Point(13, 2, 3);
        Point lookAt = new Point(0, 0, 0);
        Vec3 vup = new Vec3(0, 1, 0);
        Vec3 lookLength = lookFrom.minus(lookAt);
        double dist_to_focus = lookLength.length();

        Camera camera = new Camera(lookFrom, lookAt, vup, 20,
                0.05, dist_to_focus, 16/9.0);

        HitTableList world = new HitTableList();
        Texture pertext = new NoiseTexture(3);
        world.add(new Sphere(new Point(0,-1000,0), 1000, new Lambertian(pertext)));
        world.add(new Sphere(new Point(0, 2, 0), 2, new Lambertian(pertext)));

        return new Scene(camera, world);
    }

    public static Scene quads(){
        Point lookFrom =new Point(0, 0, 9);
        Point lookAt = new Point(0, 0, 0);
        Vec3 vup = new Vec3(0, 1, 0);
        Vec3 lookLength = lookFrom.minus(lookAt);
        double dist_to_focus = lookLength.length();

        Camera camera = new Camera(lookFrom, lookAt, vup, 80,
                0.05, dist_to_focus, 16/9.0);

        HitTableList world = new HitTableList();
        Material left_red = new Lambertian(new Color(1,.2,.2));
        Material back_green = new Lambertian(new Color(.2,1,.2));
        Material right_blue = new Lambertian(new Color(.2,.2,1));
        Material upper_orange = new Lambertian(new Color(1,.5,0));
        Material lower_teal = new Lambertian(new Color(.2,.8,.8));

        world.add(new Quad(new Point(-3,-2,5), new Vec3(0,0,-4), new Vec3(0,4,0), left_red));
        world.add(new Quad(new Point(-2,-2,0), new Vec3(4,0,0), new Vec3(0,4,0), back_green));
        world.add(new Quad(new Point(3,-2,1), new Vec3(0,0,4), new Vec3(0,4,0), right_blue));
        world.add(new Quad(new Point(-2,3,1), new Vec3(4,0,0), new Vec3(0,0,4), upper_orange));
        world.add(new Quad(new Point(-2,-3,5), new Vec3(4,0,0), new Vec3(0,0,-4), lower_teal));

        return new Scene(camera, world);
    }

    public static Scene simple_light(){
        Point lookFrom =new Point(26, 3, 6);
        Point lookAt = new Point(0, 2, 0);
        Vec3 vup = new Vec3(0, 1, 0);
        Vec3 lookLength = lookFrom.minus(lookAt);
        double dist_to_focus = lookLength.length();

        Camera camera = new Camera(lookFrom, lookAt, vup, 20,
                0.05, dist_to_focus, 16/9.0);

        HitTableList world = new HitTableList();
        Texture pertext = new NoiseTexture(4);
        world.add(new Sphere(new Point(0,-1000,0), 1000, new Lambertian(pertext)));
        world.add(new Sphere(new Point(0,2,0), 2, new Lambertian(pertext)));

        Material difflight = new DiffuseLight(new Color(4,4,4));
        world.add(new Sphere(new Point(0,7,0), 2, difflight));
        world.add(new Quad(new Point(3,1,-2), new Vec3(2,0,0), new Vec3(0,2,0), difflight));

        return new Scene(camera, world, new Color(0));
    }

    public static Scene cornell_box(){
        Point lookFrom =new Point(278, 278, -800);
        Point lookAt = new Point(278, 278, 0);
        Vec3 vup = new Vec3(0, 1, 0);
        Vec3 lookLength = lookFrom.minus(lookAt);
        double dist_to_focus = lookLength.length();

        Camera camera = new Camera(lookFrom, lookAt, vup, 40,
                0.05, dist_to_focus, 16/9.0);

        HitTableList world = new HitTableList();
        Material red   = new Lambertian(new Color(.65, .05, .05));
        Material white = new Lambertian(new Color(.73, .73, .73));
        Material green = new Lambertian(new Color(.12, .45, .15));
        Material light = new DiffuseLight(new Color(0.747f+0.058f, 0.747f+0.258f, 0.747f).multiply(8)
                                    .add(new Color(0.740f+0.287f,0.740f+0.160f,0.740f).multiply(15.6f))
                                    .add(new Color(0.737f+0.642f,0.737f+0.159f,0.737f).multiply(18.4f)));

        world.add(new Quad(new Point(555,0,0), new Vec3(0,555,0), new Vec3(0,0,555), red));
        world.add(new Quad(new Point(0,0,0), new Vec3(0,555,0), new Vec3(0,0,555), green));
        world.add(new Quad(new Point(343, 554, 332), new Vec3(-130,0,0), new Vec3(0,0,-105), light));
        world.add(new Quad(new Point(0,0,0), new Vec3(555,0,0), new Vec3(0,0,555), white));
        world.add(new Quad(new Point(555,555,555), new Vec3(-555,0,0), new Vec3(0,0,-555), white));
        world.add(new Quad(new Point(0,0,555), new Vec3(555,0,0), new Vec3(0,555,0), white));

        HitTable box1 = box(new Point(0,0,0), new Point(165,330,165), new Metal(new Color(.73, .73, .73),0.01));
        box1 = new Rotate(box1, 20, 1);
        box1 = new Translate(box1, new Vec3(265,0,295));
        world.add(box1);

        HitTable box2 = box(new Point(0,0,0), new Point(165,165,165), white);
        box2 = new Rotate(box2, -18, 1);
        box2 = new Translate(box2, new Vec3(130,0,65));
        world.add(box2);

        return new Scene(camera, world, new Color(0));
    }

    public static Scene volume_box(){
        Point lookFrom =new Point(278, 278, -800);
        Point lookAt = new Point(278, 278, 0);
        Vec3 vup = new Vec3(0, 1, 0);
        Vec3 lookLength = lookFrom.minus(lookAt);
        double dist_to_focus = lookLength.length();

        Camera camera = new Camera(lookFrom, lookAt, vup, 40,
                0.05, dist_to_focus, 16/9.0);

        HitTableList world = new HitTableList();
        Material red   = new Lambertian(new Color(.65, .05, .05));
        Material white = new Lambertian(new Color(.73, .73, .73));
        Material green = new Lambertian(new Color(.12, .45, .15));
        Material light = new DiffuseLight(new Color(15, 15, 15));

        world.add(new Quad(new Point(555,0,0), new Vec3(0,555,0), new Vec3(0,0,555), green));
        world.add(new Quad(new Point(0,0,0), new Vec3(0,555,0), new Vec3(0,0,555), red));
        world.add(new Quad(new Point(113,554,127), new Vec3(330,0,0), new Vec3(0,0,305), light));
        world.add(new Quad(new Point(0,0,0), new Vec3(555,0,0), new Vec3(0,0,555), white));
        world.add(new Quad(new Point(555,555,555), new Vec3(-555,0,0), new Vec3(0,0,-555), white));
        world.add(new Quad(new Point(0,0,555), new Vec3(555,0,0), new Vec3(0,555,0), white));

        HitTable box1 = box(new Point(0,0,0), new Point(165,330,165), new Dielectric(new Color(1),1.33));
        box1 = new Rotate(box1, 15, 1);
        box1 = new Translate(box1, new Vec3(265,0,295));
        world.add(box1);

        HitTable box2 = box(new Point(0,0,0), new Point(165,165,165), new Dielectric(new Color(1), 1.55));
        box2 = new Rotate(box2, -18, 1);
        box2 = new Translate(box2, new Vec3(130,0,65));
        //world.add(box2);

        world.add(new Medium(box1, 0.01, new Color(0.3,0.7,0.9)));
        world.add(new Medium(box2, 0.01, new Color(0.95,0.5,0.5)));

        return new Scene(camera, world, new Color(0));
    }

    public static Scene final_scene(){
        Point lookFrom =new Point(478, 278, -600);
        Point lookAt = new Point(278, 278, 0);
        Vec3 vup = new Vec3(0, 1, 0);
        Vec3 lookLength = lookFrom.minus(lookAt);
        double dist_to_focus = lookLength.length();

        Camera camera = new Camera(lookFrom, lookAt, vup, 40, 0.05, dist_to_focus, 16/9.0);


        HitTableList world = new HitTableList();
        HitTableList boxes1 = new HitTableList();
        Material ground = new Lambertian(new Color(0.48, 0.83, 0.53));

        int boxes_per_side = 20;
        for (int i = 0; i < boxes_per_side; i++) {
            for (int j = 0; j < boxes_per_side; j++) {
                double w = 100.0;
                double x0 = -1000.0 + i*w;
                double z0 = -1000.0 + j*w;
                double y0 = 0.0;
                double x1 = x0 + w;
                double y1 = random_double(1,101);
                double z1 = z0 + w;

                boxes1.add(box(new Point(x0,y0,z0), new Point(x1,y1,z1), ground));
            }
        }



        world.add(new BVH(boxes1.objects));

        Material light = new DiffuseLight(new Color(7, 7, 7));
        world.add(new Quad(new Point(123,554,147), new Vec3(300,0,0), new Vec3(0,0,265), light));

        Point center1 = new Point(400, 400, 200);
        Point center2 = center1.add(new Vec3(30,0,0));
        Material moving_sphere_material = new Lambertian(new Color(0.7, 0.3, 0.1));
        world.add(new MoveSphere(center1, center2, 50, moving_sphere_material));

        world.add(new Sphere(new Point(260, 150, 45), 50, new Dielectric(new Color(1),1.5)));
        world.add(new Sphere(new Point(0, 150, 145), 50, new Metal(new Color(0.8, 0.8, 0.9), 0.5)));

        Sphere boundary = new Sphere(new Point(360,150,145), 70, new Dielectric(new Color(1), 1.5));
        world.add(boundary);
        world.add(new Medium(boundary, 0.05, new Color(0.2, 0.7, 0.9)));
        boundary = new Sphere(new Point(0,0,0), 5000, new Dielectric(new Color(1), 1.5));
        world.add(new Medium(boundary, .0001, new Color(1,1,1)));

        Material emat = new Lambertian(new ImageTexture("RTNextWeek/TextureImages/earthmap.jpg"));
        world.add(new Sphere(new Point(400,200,400), 100, emat));
        Texture pertext = new NoiseTexture(0.1);
        world.add(new Sphere(new Point(220,280,300), 80, new Lambertian(pertext)));

        HitTableList boxes2 = new HitTableList();
        Material white = new Lambertian(Color.random());
        int ns = 1000;
        for (int j = 0; j < ns; j++) {
            boxes2.add(new Sphere(Point.random(0,165), 10, new Lambertian(Color.random())));
        }

        world.add(new Translate(
                new Rotate(new BVH(boxes2.objects), 15, 2),
                new Vec3(-100,270,395)
        ));

        return new Scene(camera, new BVH(world.objects), new Color(0));
    }
}

