package RTRestOfYourLife;

import RTRestOfYourLife.HitInfo.HitRecord;
import RTRestOfYourLife.HitInfo.Interval;
import RTRestOfYourLife.HitInfo.ScatterInfo;
import RTRestOfYourLife.HitTable.HitTable;
import RTRestOfYourLife.PDF.CosinePDF;
import RTRestOfYourLife.PDF.HitTablePDF;
import RTRestOfYourLife.PDF.MixturePDF;
import RTRestOfYourLife.PDF.PDF;

import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static RTRestOfYourLife.RTUtil.*;

public class Render {
    int image_width;
    int image_height;
    int spp = 10000;
    int depth = 32;
    Color background;
    Camera camera;

    Render(Camera camera, Color bg_color){
        this.camera = camera;
        this.image_width = 1280;
        this.image_height = 720;
        this.background = bg_color;
    }
    Render(Camera camera, int width, int height, Color bg_color){
        this.camera = camera;
        this.image_width = width;
        this.image_height = height;
        this.background = bg_color;
    }

    String writeRGB(Vec3 c, int sampler_per_pixel)
    {
        double r = c.x;
        double g = c.y;
        double b = c.z;

        double scale = 1.0/sampler_per_pixel;
        r = Math.sqrt(scale * r);
        g = Math.sqrt(scale * g);
        b = Math.sqrt(scale * b);

        int R = (int) (clamp(r,0.0,0.999) * 256);
        int G = (int) (clamp(g,0.0,0.999) * 256);
        int B = (int) (clamp(b,0.0,0.999) * 256);

        return R+" "+G+" "+B;
    }

    java.awt.Color writeAwtColor(Vec3 c, int sampler_per_pixel)
    {
        double r = c.x;
        double g = c.y;
        double b = c.z;

        double scale = 1.0/sampler_per_pixel;
        r = Math.sqrt(scale * r);
        g = Math.sqrt(scale * g);
        b = Math.sqrt(scale * b);

        int R = (int)(clamp(r,0.0,0.999) * 256);
        int G = (int)(clamp(g,0.0,0.999) * 256);
        int B = (int)(clamp(b,0.0,0.999) * 256);

        return new java.awt.Color(R, G, B);
    }
    Color skyColor(Ray r){
        Vec3 unit_direction = Vec3.unit_vector(r.dir());

        double t = 0.5*(unit_direction.y+1.0);

        Color colorW = new Color(1.0,1.0,1.0);
        Color colorB = new Color(0.5,0.7,1.0);
        return colorW.multiply(1.0-t).add(colorB.multiply(t));
    }

    public Color getBackground(Ray r) {
        if (background != null)
            return background;
        else
            return skyColor(r);
    }

    Color rayColor(Ray r, HitTable world, HitTable lights, int depth){
        if(depth<=0)
            return new Color(0);

        HitRecord rec = new HitRecord();
        ScatterInfo srec = new ScatterInfo();
        Interval t = new Interval(0.0001, infinity);
        if(!world.hit(r, t, rec))
            return getBackground(r);

        Color emission = rec.mat.emitted(r, rec, rec.p);
        if(!rec.mat.scatter(r, rec, srec))
            return emission;

        if (srec.skip_pdf)
            return srec.attenuation.multiply(rayColor(srec.skip_pdf_ray, world, lights,depth-1));

        PDF light_pdf = new HitTablePDF(lights, rec.p);
        PDF surface_pdf = srec.pdf;
        MixturePDF mixed_pdf = new MixturePDF(light_pdf,surface_pdf);

        Ray scattered = new Ray(rec.p, mixed_pdf.generate(), r.time());
        double pdf = mixed_pdf.value(scattered.dir());

        double scattering_pdf = rec.mat.scattering_pdf(r, rec, scattered);

        Color scatter_color = srec.attenuation.multiply(scattering_pdf)
                            .multiply(rayColor(scattered, world, lights, depth-1))
                            .divide(pdf);

        return emission.add(scatter_color);
    }

    void imageRender(HitTable world, HitTable lights){
        try(FileOutputStream file = new FileOutputStream("OutImage.ppm");
            PrintStream filePrint = new PrintStream(file) ) {

            filePrint.println("P3");
            filePrint.println(image_width+" "+image_height);
            filePrint.println(255);

            for (long y=0; y < image_height; ++y){
                System.out.println("剩余行数:"+y);
                for (long x=0; x < image_width; ++x){
                    Color pixelColor = new Color(0);
                    for (int s=0; s < spp; ++s){
                        double u = (x + random_double()) / (image_width-1);
                        double v = (y + random_double()) / (image_height-1);
                        pixelColor.plus_Equal(rayColor(camera.get_ray(u, v), world, lights, depth));
                    }
                    filePrint.println(writeRGB(pixelColor, spp));
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void windowRender(HitTable world, HitTable lights){
        Color[] pixelColor = new Color[image_height*image_width];
        for (int i = 0; i<image_height*image_width; ++i)
            pixelColor[i] = new Color(0);

        JFrame window = new JFrame("Ray tracing in Java"){
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                for (int s=0; s < spp; ++s){
                    long start_time = System.currentTimeMillis();
                    for (int y=0; y < image_height; ++y){
                        for (int x=0; x < image_width; ++x){
                            double u = (x + random_double()) / (image_width-1);
                            double v = (y + random_double()) / (image_height-1);
                            pixelColor[y*image_width+x].plus_Equal(rayColor(camera.get_ray(u, v), world, lights, depth));
                            g.setColor(writeAwtColor(pixelColor[y*image_width+x], s+1));
                            g.drawRect(x,y,1,1);
                        }
                    }
                    System.out.println("采样数:" + (s+1) + " | " + "耗时:" + (double)(System.currentTimeMillis()-start_time)/1000+"s");
                }
            }
        };
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(image_width, image_height);
        window.setVisible(true);
    }

}
