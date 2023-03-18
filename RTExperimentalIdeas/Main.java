package RTExperimentalIdeas;

public class Main {
    public static void main(String... args){
        Scene scene = Scene.reimu_fumo();
        Render render = new Render(scene.camera);
        //render.imageRender(scene.world);
        render.windowRender(scene.world);
    }
}
