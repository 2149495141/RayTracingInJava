package RTExperimentalIdeas.Texture;

import RTExperimentalIdeas.Color;
import RTExperimentalIdeas.Point;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static RTExperimentalIdeas.RTUtil.clamp;

public class ImageTexture implements Texture {
    BufferedImage image;
    int image_width, image_height;
    public ImageTexture(String fileName){
        try {
            image = ImageIO.read(new File(fileName));
            image_width = image.getWidth()-1;
            image_height = image.getHeight()-1;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    int[] getPixel(int x, int y){
        int hex_color = image.getRGB(x, y);
        int r = (hex_color >> 16) & 0xff;
        int g = (hex_color >> 8) & 0xff;
        int b = (hex_color) & 0xff;

        /* 另一种方法是使用颜色空间工具类来获取每个通道的颜色 */
//        Object data = image.getRaster().getDataElements(x, y, null);
//        int r = image.getColorModel().getRed(data);
//        int g = image.getColorModel().getGreen(data);
//        int b = image.getColorModel().getBlue(data);
        return new int[]{r, g, b};
    }

    @Override
    public Color value(double u, double v, Point p) {
        if (image.getHeight() <= 0) return new Color(0,1,1);

        u = clamp(u, 0, 1);
        v = 1.0 - clamp(v, 0.0, 1.0);;

        int x = (int) (u*image_width);
        int y = (int) (v*image_height);
        int[] pixel = getPixel(x, y);

        double color_scale = 1.0 / 255.0;
        return new Color(color_scale*pixel[0], color_scale*pixel[1], color_scale*pixel[2]);
    }
}
