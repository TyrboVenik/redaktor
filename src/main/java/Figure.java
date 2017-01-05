
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by venik on 08.11.16.
 */
abstract public class Figure implements Serializable{
    public static int number;
    static {
        number = 1;
    }
    private boolean painted;
    private  String name;

    public Figure(){}

    public boolean getPaunted(){return painted;}
    public String getName(){return  name;}

    public void setPainted(boolean painted){this.painted=painted;}
    public void setName(String name){this.name = name;}

    public  MyDoublePoint rot(MyDoublePoint p,int alpha){

        double x = p.x;
        double y = p.y;

        MyDoublePoint pp = new MyDoublePoint();
        pp.x =x* Math.cos(Math.toRadians(alpha)) +  y* Math.sin(Math.toRadians(alpha));
        pp.y = -x* Math.sin(Math.toRadians(alpha)) + y* Math.cos(Math.toRadians(alpha));
        return pp;
    }

    public boolean findPlace(MyPoint p1, MyPoint l1, MyPoint l2){

        int A = l1.y - l2.y;
        int B = l2.x - l1.x;
        int C = l1.x*l2.y-l2.x*l1.y;

        double d = (A*p1.x + B*p1.y+C)/(Math.sqrt(A*A+B*B));

        if(C >= 0){
            if (d>=0)
                return true;
            else
                return false;
        }

        if(C < 0){
            if (d >=0)
                return false;
            else
                return true;
        }

        return false;
    }

    public Figure copy(String name){return null;}

    public boolean hasPointInTriangle(MyDoublePoint p1, MyDoublePoint p2, MyDoublePoint p3) {
        ArrayList<MyPoint> arr = getFigureMyPoints();

        for (MyPoint p:arr)
            if(Figure.placeInTriangle(p1,p2,p3,new MyDoublePoint(p)) != -1)
                return true;

        return false;
    }

    public static MyDoublePoint  findPerpendicular(MyDoublePoint p1,MyDoublePoint p2, MyDoublePoint p3){

        MyDoublePoint p4 = new MyDoublePoint();
        p4.x = 99999;
        p4.y = 99999;
        if(p1.x == p2.x){
            p4.x = p1.x;
            p4.y = p3.y;
            return p4;
        }

        if(p1.y == p2.y){
            p4.x = p3.x;
            p4.y = p1.y;
            return p4;
        }

        double k = (p1.y-p2.y)/(p1.x-p2.x);
        double k1 = -1/k;

        double b = p2.y - k*p2.x;
        double b1 = p3.y - k1*p3.x;

        p4.x = (b-b1)/(k1-k);
        p4.y = p4.x*k + b;

        return p4;
    }

    public static int placeInTriangle(MyDoublePoint p1, MyDoublePoint p2, MyDoublePoint p3,MyDoublePoint p0){
        /*
        (x1 - x0) * (y2 - y1) - (x2 - x1) * (y1 - y0)
        (x2 - x0) * (y3 - y2) - (x3 - x2) * (y2 - y0)
        (x3 - x0) * (y1 - y3) - (x1 - x3) * (y3 - y0)
         */
        double pr1 = (p1.x-p0.x)*(p2.y-p1.y)-(p2.x-p1.x)*(p1.y-p0.y);
        double pr2 = (p2.x-p0.x)*(p3.y-p2.y)-(p3.x-p2.x)*(p2.y-p0.y);
        double pr3 = (p3.x-p0.x)*(p1.y-p3.y)-(p1.x-p3.x)*(p3.y-p0.y);
        if(pr1 ==0 || pr1 ==0 || pr1 ==0){
            return 0;
        }
        if (pr1 > 0 && pr2 > 0 && pr3 > 0 || pr1<0 && pr2 < 0 && pr3 < 0)
            return 1;

        return -1;
    }
    abstract public void drawProjection(String name,MyPoint p1, MyPoint p2);
    abstract public ArrayList<MyPoint> findMinDistance(MyPoint p3);
    abstract public void section(String prefix, MyPoint p1, MyPoint p2);
    abstract public ArrayList<MyPoint> getFigureMyPoints();
    abstract public void rotate(int alpha);
    public void change(ArrayList<Integer> arr){}
}
