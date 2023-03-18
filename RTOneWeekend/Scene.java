package RTOneWeekend;

import RTOneWeekend.HitTable.HitTable;
import RTOneWeekend.HitTable.HitTableList;
import RTOneWeekend.HitTable.Sphere;
import RTOneWeekend.Material.Dielectric;
import RTOneWeekend.Material.Lambertian;
import RTOneWeekend.Material.Material;
import RTOneWeekend.Material.Metal;

import static RTOneWeekend.RTUtil.*;

public class Scene {
    Camera camera;
    HitTable world;
    int image_width;
    int image_height;

    Scene(Camera camera, HitTable world){
        this.camera = camera;
        this.world = world;
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
                0.01, dist_to_focus, 16/9.0);

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

    public static Scene finalScene(){
        Point lookFrom =new Point(13, 2, 3);
        Point lookAt = new Point(0, 0, 0);
        Vec3 vup = new Vec3(0, 1, 0);
        Vec3 lookLength = lookFrom.minus(lookAt);
        double dist_to_focus = lookLength.length();

        Camera camera = new Camera(lookFrom, lookAt, vup, 20,
                0.05, dist_to_focus, 16/9.0);


        HitTableList world = new HitTableList();

        Material ground_material = new Lambertian(new Color(0.5, 0.5, 0.5));
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

        return new Scene(camera, world);
    }

}

