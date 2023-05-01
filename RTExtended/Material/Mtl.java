package RTExtended.Material;

import RTExtended.Texture.ImageTexture;

public class Mtl {
    ImageTexture image;
    Material mat;

    public Mtl(String mtlFileName, Material m){
        String imageFilePath = "";
        image = new ImageTexture(imageFilePath);
        mat = m;
    }
}
