package RTRestOfYourLife.HitInfo;

import RTRestOfYourLife.Color;
import RTRestOfYourLife.PDF.PDF;
import RTRestOfYourLife.Point;
import RTRestOfYourLife.Ray;
import RTRestOfYourLife.Vec3;

public class ScatterInfo {
    public PDF pdf;
    public boolean skip_pdf;
    public Ray skip_pdf_ray;
    public Color attenuation;

    public ScatterInfo(){
        skip_pdf_ray = new Ray(new Point(0), new Vec3(0), 0);
        attenuation = new Color(0);
        skip_pdf = false;
        pdf = new PDF() {};
    }
}
