import java.io.Serializable;

/**
 * Created by venik on 23.11.16.
 */
public class MyPoint implements Serializable {
    private static final long serialVersionUID = 1L;

    public boolean visible;
    public int x;
    public int y;
    {
        visible = true;
    }
    public MyPoint(){x=0;y=0;}
    public MyPoint(int x,int y){this.x=x;this.y=y;}
    public MyPoint(MyDoublePoint p){this.x=(int)Math.round(p.x);this.y=(int)Math.round(p.y);}
    public int getY(){return y; }
    public int getX(){return x; }
    public MyPoint(MyPoint p){x=p.x;y=p.y; }

    public static double distanse(MyPoint p1, MyPoint p2){
        return Math.sqrt(
                (p1.x-p2.x)*(p1.x-p2.x) + (p1.y-p2.y)*(p1.y-p2.y)
        );
    }

    public double distanseTo(MyPoint p1){
        return Math.sqrt(
                (p1.x-x)*(p1.x-x) + (p1.y-y)*(p1.y-y)
        );
    }
}