package RTOneWeekend;

public class Main {
    public static void main(String... args){
        Scene finalScene = Scene.finalScene();
        Render render = new Render(finalScene.camera);
        //render.imageRender(initScene.world);
        render.windowRender(finalScene.world);
    }
}
