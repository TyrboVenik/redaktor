import java.io.Serializable;

/**
 * Created by venik on 24.11.16.
 */
public class MyDoublePoint implements Serializable {
    public double x;
    public double y;

    public MyDoublePoint(){}

    public MyDoublePoint(int x1,int y1){
        x=x1;
        y=y1;
    }

    public MyDoublePoint(MyPoint point){
        x=point.x;
        y=point.y;
    }
}
