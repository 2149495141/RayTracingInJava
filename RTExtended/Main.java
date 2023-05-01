package RTExtended;

public class Main {
    public static void main(String... args){
        Scene scene = Scene.volume_dragon() ;
        Render render = new Render(scene.camera);
        //render.imageRender(scene.world);
        render.windowRender(scene.world);
        //render.windowRenderBySPP(scene.world);
    }
}
