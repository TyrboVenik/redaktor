
import java.util.ArrayList;

/**
 * Created by venik on 17.11.16.
 */
public class FigureCircle extends Figure {
    private MyDoublePoint center;
    private int radius;
    {
        center = new MyDoublePoint();
    }
    public FigureCircle(String name){
        if (name == null){
            setName("Line"+Figure.number);
            number++;
        }else
            setName(name);
    }
    public FigureCircle setCenter(MyDoublePoint center){
        this.center.x=center.x;
        this.center.y=center.y;
        setPainted(false);
        return this;
    }
    public FigureCircle setCenter(MyPoint center){
        this.center.x=center.x;
        this.center.y=center.y;
        setPainted(false);
        return this;
    }
    public FigureCircle setRadius(int radius){this.radius=radius; setPainted(false);return this;}
    public FigureCircle setRadius(MyPoint radius){
        this.radius = (int)Math.round(
                Math.sqrt(
                        (center.x-radius.x)*(center.x-radius.x)+(center.y - radius.y)*(center.y - radius.y)
                )
        );
        setPainted(false);
        return this;
    }
    public MyDoublePoint getCenter(){return center;}
    public int getRadius(){return radius;}
    public ArrayList<MyPoint> getFigureMyPoints() {
        ArrayList<MyPoint> res = new ArrayList<MyPoint>();

        MyPoint center = new MyPoint();
        center.x = (int)Math.round(this.center.x);
        center.y = (int)Math.round(this.center.y);

        int x = 0, y = radius, sigma = 0, delta = 2 - 2 * radius;
        while (y >= 0) {
            res.add(new MyPoint(center.x + x, center.y - y));
            res.add(new MyPoint(center.x - x, center.y - y));
            res.add(new MyPoint(center.x - x, center.y + y));
            res.add(new MyPoint(center.x + x, center.y + y));

            sigma = 2 * (delta + y) - 1;
            if (delta < 0 && sigma <= 0) {          //перемещение по горизонтали
                x++;
                delta += x + 1;
            } else if (delta > 0 && sigma > 0) {    //перемещение по вертикали
                y--;
                delta -= y + 1;
            } else {                                //перемещение по диагонали
                x++;
                delta += x - y;
                y--;
            }
        }
        return res;
    }
    public void rotate(int alpha){
        center =  rot(center,alpha);
        setPainted(false);
    }
    public Figure copy(String name){
        FigureCircle f = new FigureCircle(name);
        f.setRadius(this.radius);
        f.setCenter(this.center);
        f.setPainted(false);
        return f;
    }
    public ArrayList<MyPoint> findMinDistance(MyPoint p3){
        MyDoublePoint p4 = new MyDoublePoint();
        ArrayList<MyPoint> arr = new ArrayList<>();
        double dx = (radius*(p3.x-center.x))
                /
                (Math.sqrt(
                        (p3.x - center.x)*(p3.x - center.x)+(p3.y - center.y)*(p3.y - center.y)
                ));

        double dy = (radius*(p3.y-center.y))
                /
                (Math.sqrt(
                        (p3.x - center.x)*(p3.x - center.x)+(p3.y - center.y)*(p3.y - center.y)
                ));

        p4.x = center.x + dx;
        p4.y = center.y + dy;

        MyPoint myPoint = new MyPoint(p4);
        MyPoint myPoint1 = new MyPoint(center);
        if (myPoint.x ==  myPoint1.x && myPoint.y ==  myPoint1.y){
            myPoint.x +=radius;
        }

        arr.add(myPoint);
        return arr;
    }
    public void section(String prefix, MyPoint p1, MyPoint p2){
        FigureRandom fr1 = new FigureRandom(prefix + "1");
        FigureRandom fr2 = new FigureRandom(prefix + "2");

        ArrayList<MyPoint> pointList = getFigureMyPoints();

        for (int i =0;i < pointList.size();i++) {
            if (findPlace(pointList.get(i), p1, p2))
                fr1.add(pointList.get(i));
            else
                fr2.add(pointList.get(i));

        }

        GraphicsMain.figureList.remove(this);
        GraphicsMain.figureList.put(fr1,fr1.getFigureMyPoints());
        GraphicsMain.figureList.put(fr2,fr2.getFigureMyPoints());
    }

    public void drawProjection(String name,MyPoint p1, MyPoint p2){
       MyDoublePoint p = findPerpendicular(new MyDoublePoint(p1),new MyDoublePoint(p2),center);
        double dx = p2.x - p.x;
        double dy = p2.y - p.y;
        double l = Math.sqrt(dx*dx+dy*dy);
        double ddx = radius*dx/l;
        double ddy = radius*dy/l;

        MyDoublePoint point1 = new MyDoublePoint();
        MyDoublePoint point2 = new MyDoublePoint();

        point1.x=p.x+ddx;
        point1.y=p.y+ddy;

        point2.x=p.x-ddx;
        point2.y=p.y-ddy;

        FigureLine line = new FigureLine(name);
        line.setP1(point1);
        line.setP2(point2);
        line.setPainted(false);

        GraphicsMain.figureList.put(line,new ArrayList<>());
    }

    public void change(ArrayList<Integer> arr){

        if (arr.size()>2 && arr.get(0) == 1){
            center = new MyDoublePoint(arr.get(1),arr.get(2));
            setPainted(false);
        }
        if(arr.size()>1 && arr.get(0) == 2){
            radius = arr.get(1);
            setPainted(false);
        }
    }

}
