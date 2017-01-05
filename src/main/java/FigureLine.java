import java.util.ArrayList;

/**
 * Created by venik on 08.11.16.
 */
public class FigureLine extends Figure {

    private MyDoublePoint p1;
    private MyDoublePoint p2;

    {
        p1=new MyDoublePoint();
        p2=new MyDoublePoint();
    }
    public MyDoublePoint getP1(){return p1;}
    public MyDoublePoint getP2(){return p2;}

    public FigureLine(String name) {
        if (name == null){
            setName("Line"+Figure.number);
            number++;
        }else
            setName(name);

    }
    public FigureLine(String name, MyPoint p1,MyPoint p2){
        if (name == null){
            setName("Line"+Figure.number);
            number++;
        }else
            setName(name);
        this.p1.x = p1.x;
        this.p1.y = p1.y;
        this.p2.x = p2.x;
        this.p2.y = p2.y;
    }
    public FigureLine(String name,int x1,int y1,int x2,int y2){
        if (name == null){
            setName("Line"+Figure.number);
            number++;
        }else
            setName(name);
        this.p1 = new MyDoublePoint(x1,y1);
        this.p2 = new MyDoublePoint(x2,y2);
    }
    public FigureLine setP1(MyDoublePoint p1){
        this.p1 = p1;

        setPainted(false);
        return this;
    }
    public FigureLine setP2(MyDoublePoint p2){
        this.p2 =p2;

        setPainted(false);
        return this;
    }
    public FigureLine setP1(MyPoint p1){
        this.p1.x = p1.x;
        this.p1.y = p1.y;

        setPainted(false);
        return this;
    }
    public FigureLine setP2(MyPoint p2){
        this.p2.x = p2.x;
        this.p2.y = p2.y;

        setPainted(false);
        return this;
    }
    public ArrayList<MyPoint> getFigureMyPoints(){
        ArrayList<MyPoint> res = new ArrayList<MyPoint>();
        MyPoint p1 = new MyPoint();

        p1.x = (int)Math.round(this.p1.x);
        p1.y = (int)Math.round(this.p1.y);
        MyPoint p2 = new MyPoint();
        p2.x = (int)Math.round(this.p2.x);
        p2.y = (int)Math.round(this.p2.y);

        if ((p1.x == p2.x) && (p1.y == p2.y)) {
           res.add(p1);
            return res; 
        }
        int A = p2.y - p1.y;
        int B = p1.x - p2.x;
        int sign;
        if (Math.abs(A) > Math.abs(B)) sign = 1;
            else sign = -1;

        int signa, signb;
        if (A < 0) signa = -1;
            else signa = 1;
        if (B < 0) signb = -1;
            else signb = 1;
        int f = 0;
        res.add(new MyPoint(p1.x,p1.y));
        int x = p1.x, y = p1.y;
        if (sign == -1) {
            do {
                f += A*signa;
                if (f > 0) {
                    f -= B*signb;
                    y+=signa;
                }
                x-=signb;
                res.add(new MyPoint(x,y));

                } while (x != p2.x || y != p2.y);
        } else {
            do {
                f += B*signb;
                if (f > 0) {
                    f -= A*signa;
                    x-=signb;
                }
                y+=signa;
                res.add(new MyPoint(x,y));
            } while (x != p2.x || y != p2.y);
        }

        return  res;
    }

    public void rotate(int alpha){
       p1 =  rot(p1,alpha);
        p2 =  rot(p2,alpha);
        setPainted(false);
    }

    public void section(String prefix, MyPoint p1, MyPoint p2){
        System.out.println(prefix);
        FigureRandom fr1 = new FigureRandom(prefix + "1");
        FigureRandom fr2 = new FigureRandom(prefix + "2");

        ArrayList<MyPoint> pointList = getFigureMyPoints();

        int i=0;
        boolean tmp = findPlace(pointList.get(0),p1,p2);
        while (i<pointList.size() && tmp == findPlace(pointList.get(i),p1,p2)){
            fr1.add(pointList.get(i));
            i++;
        }

        while (i<pointList.size()){
            fr2.add(pointList.get(i));
            i++;
        }

        GraphicsMain.figureList.remove(this);
        GraphicsMain.figureList.put(fr1,fr1.getFigureMyPoints());
        GraphicsMain.figureList.put(fr2,fr2.getFigureMyPoints());
    }

    public Figure copy(String name){
        FigureLine f = new FigureLine(name);
        f.setP1(this.p1);
        f.setP2(this.p2);
        f.setPainted(false);
        return f;
    }

    public ArrayList<MyPoint> findMinDistance(MyPoint p3){
        MyDoublePoint p4 = findPerpendicular(p1,p2,new MyDoublePoint(p3));
        ArrayList<MyPoint> arr = new ArrayList<>();
        if(
                p1.x < p4.x && p2.x < p4.x ||
                p1.x > p4.x && p2.x > p4.x ||
                p1.y < p4.y && p2.y < p4.y ||
                p1.y > p4.y && p2.y > p4.y
                ){
            double d1 = Math.sqrt((p3.x - p1.x)*(p3.x - p1.x) + (p3.y - p1.y)*(p3.y - p1.y));
            double d2 = Math.sqrt((p3.x - p2.x)*(p3.x - p2.x) + (p3.y - p2.y)*(p3.y - p2.y));

            if (d1<d2) {
                arr.add(new MyPoint(p1));
                return arr;
            }
            else {
                arr.add(new MyPoint(p2));
                return arr;
            }
        }else{
            arr.add(new MyPoint(p4));
            return arr;
        }

    }

    public void drawProjection(String name,MyPoint p1, MyPoint p2){
        MyDoublePoint point1 = findPerpendicular(new MyDoublePoint(p1), new MyDoublePoint(p2), this.p1);
        MyDoublePoint point2 = findPerpendicular(new MyDoublePoint(p1), new MyDoublePoint(p2), this.p2);

        FigureLine line = new FigureLine(name);
        line.setP1(point1);
        line.setP2(point2);
        line.setPainted(false);
        GraphicsMain.figureList.put(line,new ArrayList<>());
    }
    public void change(ArrayList<Integer> arr){
        if (arr.size()<3)
            return;
        if (arr.get(0) == 1){
            p1 = new MyDoublePoint(arr.get(1),arr.get(2));
            setPainted(false);
        }
        if(arr.get(0) == 2){
            p2 = new MyDoublePoint(arr.get(1),arr.get(2));
            setPainted(false);
        }
    }

}
