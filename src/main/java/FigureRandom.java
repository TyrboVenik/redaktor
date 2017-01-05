import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by venik on 24.11.16.
 */

public class FigureRandom extends Figure {
    private ArrayList<MyPoint> pointList;
    public FigureRandom(String name){
        if (name == null){
            setName("random"+ Figure.number);
            Figure.number++;
        }else
            setName(name);
        pointList = new ArrayList<>();
    }
    public FigureRandom(String name,ArrayList<MyPoint> arr){
        if (name == null){
            setName("random"+ Figure.number);
            Figure.number++;
        }else
            setName(name);
        pointList = new ArrayList<>();

        for(int i = 0;i<arr.size();i++) {
            MyPoint p = new MyPoint();
            p.x = arr.get(i).x;
            p.y = arr.get(i).y;
            pointList.add(p);
        }
    }

    public void add(MyPoint point){
        pointList.add(point);
    }
    public ArrayList<MyPoint> getFigureMyPoints() {
        return pointList;
    }
    public void rotate(int alpha){}
    @Override
    public ArrayList<MyPoint> findMinDistance(MyPoint p3) {
        return null;
    }
    public void section(String prefix, MyPoint p1, MyPoint p2){}
    public void drawProjection(String name,MyPoint p1, MyPoint p2){}
}
