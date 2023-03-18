package RTOneWeekend.HitInfo;

import RTOneWeekend.Color;
import RTOneWeekend.Point;
import RTOneWeekend.Ray;
import RTOneWeekend.Vec3;

public class Radiance {  //使用class是为了模拟指针，使参数可以往回传
    public Ray scattered;
    public Color attenuation;

    public Radiance(){
        scattered = new Ray(new Point(0), new Vec3(0));
        attenuation = new Color(0);
    }
}
