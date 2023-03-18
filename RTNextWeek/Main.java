package RTNextWeek;

public class Main {
    public static void main(String... args){
        Scene scene = Scene.final_scene();
        Render render = new Render(scene.camera, scene.background);
        //render.imageRender(initScene.world);
        render.windowRender(scene.world);
    }
}
