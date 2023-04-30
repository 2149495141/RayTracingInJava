package RTRestOfYourLife;

public class Main {
    public static void main(String... args){
        Scene scene = Scene.cornell_box();
        Render render = new Render(scene.camera, scene.background);
        //render.imageRender(initScene.world);
        render.windowRender(scene.world, scene.lights);
    }
}
