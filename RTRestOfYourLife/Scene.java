package RTRestOfYourLife;

import RTRestOfYourLife.Camera;
import RTRestOfYourLife.Color;
import RTRestOfYourLife.HitTable.*;
import RTRestOfYourLife.Material.*;
import RTRestOfYourLife.Point;
import RTRestOfYourLife.Texture.CheckerTexture;
import RTRestOfYourLife.Texture.ImageTexture;
import RTRestOfYourLife.Texture.NoiseTexture;
import RTRestOfYourLife.Texture.Texture;
import RTRestOfYourLife.Vec3;

import static RTRestOfYourLife.HitTable.Quad.box;
import static RTRestOfYourLife.RTUtil.random_double;

public class Scene {
    Camera camera;
    HitTable world;
    HitTable lights;
    Color background;
    record scene(){}

    Scene(Camera camera, HitTable world, HitTable lights){
        this.camera = camera;
        this.world = world;
        this.lights = lights;
        this.background = null;
    }
    Scene(Camera camera, HitTable world, HitTable lights, Color bg_color){
        this.camera = camera;
        this.world = world;
        this.lights = lights;
        this.background = bg_color;
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
        HitTableList lights = new HitTableList();
        Material red   = new Lambertian(new Color(.65, .05, .05));
        Material white = new Lambertian(new Color(.73, .73, .73));
        Material green = new Lambertian(new Color(.12, .45, .15));
        Material light = new DiffuseLight(new Color(0.747f+0.058f, 0.747f+0.258f, 0.747f).multiply(8)
                                    .add(new Color(0.740f+0.287f,0.740f+0.160f,0.740f).multiply(15.6f))
                                    .add(new Color(0.737f+0.642f,0.737f+0.159f,0.737f).multiply(18.4f)));

        world.add(new Quad(new Point(555,0,0), new Vec3(0,555,0), new Vec3(0,0,555), red));
        world.add(new Quad(new Point(0,0,0), new Vec3(0,555,0), new Vec3(0,0,555), green));
        world.add(new Quad(new Point(0,0,0), new Vec3(555,0,0), new Vec3(0,0,555), white));
        world.add(new Quad(new Point(555,555,555), new Vec3(-555,0,0), new Vec3(0,0,-555), white));
        world.add(new Quad(new Point(0,0,555), new Vec3(555,0,0), new Vec3(0,555,0), white));

        lights.add(new Quad(new Point(343, 554, 332), new Vec3(-130,0,0), new Vec3(0,0,-105), light));
        world.add(new Quad(new Point(343, 554, 332), new Vec3(-130,0,0), new Vec3(0,0,-105), light));


        HitTable box1 = box(new Point(0,0,0), new Point(165,330,165), new Metal(new Color(0.8, 0.85, 0.88),0.01));
        box1 = new Rotate(box1, 20, 1);
        box1 = new Translate(box1, new Vec3(265,0,295));
        world.add(box1);

        HitTable box2 = box(new Point(0,0,0), new Point(165,165,165), white);
        box2 = new Rotate(box2, -18, 1);
        box2 = new Translate(box2, new Vec3(130,0,65));
        world.add(box2);

        Material glass = new Dielectric(new Color(1), 1.5);
        HitTable sphere = new Sphere(new Point(190,90+165,190), 90, glass);
        world.add(sphere);
        lights.add(sphere);

        return new Scene(camera, world, lights, new Color(0));
    }


}

