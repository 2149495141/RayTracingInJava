package RTExperimentalIdeas.Material;

import RTExperimentalIdeas.Color;
import RTExperimentalIdeas.Point;
import RTExperimentalIdeas.Texture.ImageTexture;
import RTExperimentalIdeas.Texture.Texture;

public class Mtl {
    ImageTexture image;
    Material mat;

    public Mtl(String mtlFileName, Material m){
        String imageFilePath = "";
        image = new ImageTexture(imageFilePath);
        mat = m;
    }
}
