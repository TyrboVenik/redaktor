import javafx.util.Pair;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.ArrayList;

/**
 * Created by venik on 15.11.16.
 */
public class FigureBezier extends  Figure {
    private ArrayList<MyDoublePoint> controlMyPoints;
    private float step;
    {
        controlMyPoints = new ArrayList<>();
    }
    public boolean setLast(MyPoint myPoint){
        if(controlMyPoints.size()!=0){
            MyDoublePoint dp = new MyDoublePoint();
            dp.x =  myPoint.x;
            dp.y =  myPoint.y;
            controlMyPoints.set(controlMyPoints.size()-1,dp);
            setPainted(false);
            step = calculateStep();
            return true;
        }
        return false;
    }
    public boolean removeLast(){
        if(controlMyPoints.size()!=0){
            controlMyPoints.remove(controlMyPoints.size()-1);
            setPainted(false);
            step = calculateStep();
            return true;
        }
        return false;
    }
    public FigureBezier(String name){
        if (name == null){
            setName("Bezier"+Figure.number);
            number++;
        }else
            setName(name);

    }
    public  FigureBezier(String name,ArrayList<MyDoublePoint> arr) {
        if (name == null){
            setName("Bezier"+Figure.number);
            number++;
        }else
            setName(name);

        setControlMyPoints(arr);
    }
    public ArrayList<MyDoublePoint> getControlMyPoints(){return controlMyPoints;}
    private float calculateStep(){
        float tmp = 0f;
        for (int i = 0;i<controlMyPoints.size()-1;i++)
            tmp += Math.sqrt(controlMyPoints.get(i).x* controlMyPoints.get(i).x +
                    controlMyPoints.get(i).y* controlMyPoints.get(i).y );
        return 0.7f/tmp;
    }
    public FigureBezier setControlMyPoints(ArrayList<MyDoublePoint> arr){
        controlMyPoints = new ArrayList<>(arr);
        step = calculateStep();
        setPainted(false);
        return  this;
    }
    public FigureBezier addControlMyPoint(MyPoint myPoint){
        MyDoublePoint dp = new MyDoublePoint();
        dp.x = myPoint.x;
        dp.y = myPoint.y;
        controlMyPoints.add(dp);
        setControlMyPoints(controlMyPoints);
        setPainted(false);
        step = calculateStep();
        return this;
    }
    public FigureBezier addControlMyPoint(int x,int y){
        controlMyPoints.add(new MyDoublePoint(x,y));
        setControlMyPoints(controlMyPoints);
        setPainted(false);
        step = calculateStep();
        return this;
    }

    private  int f(int n) {
        return (n <= 1) ? 1 : n * f(n - 1);
    }

    private float getBezierBasis(int i, int n, float t) {
        return (float)((f(n) / (f(i)*f(n - i)))  *  Math.pow(t, i)* Math.pow(1 - t, n - i));
    }

    public ArrayList<MyPoint> getFigureMyPoints() {

        ArrayList<MyPoint> res = new ArrayList<>();

        ArrayList<MyPoint> controlMyPoints = new ArrayList<>();
        for (int i = 0; i<this.controlMyPoints.size();i++){
            MyPoint p = new MyPoint(this.controlMyPoints.get(i));
            controlMyPoints.add(p);
        }

        for (float t = 0; t < 1 + step; t += step) {
            if (t > 1) {
                t = 1;
            }

            float x = 0f;
            float y = 0f;
            for (int i = 0; i < controlMyPoints.size(); i++) {
                float b = getBezierBasis(i, controlMyPoints.size() - 1, t);

                x = x + controlMyPoints.get(i).x * b;
                y = y + controlMyPoints.get(i).y * b;
            }
            MyPoint MyPoint = new MyPoint(Math.round(x),Math.round(y));

            if (!(res.size() != 0 &&  res.get(res.size()-1).equals(MyPoint)))
                res.add(MyPoint);
        }

        return res;
    }
    public void rotate(int alpha){
        for (int  i=0; i<controlMyPoints.size();i++)
            controlMyPoints.set(i, rot(controlMyPoints.get(i),alpha));

        setPainted(false);
    }
    public Figure copy(String name){
        ArrayList<MyDoublePoint> arr = new ArrayList<>();
        for (int i = 0;i<controlMyPoints.size();i++){
            arr.add(controlMyPoints.get(i));
        }

        FigureBezier f = new FigureBezier(name,arr);

        f.setPainted(false);
        return f;
    }
    public ArrayList<MyPoint> findMinDistance(MyPoint p3){
        ArrayList<MyPoint> arr = new ArrayList<>();
        ArrayList<MyPoint> fp = getFigureMyPoints();
        double minDistanse = MyPoint.distanse(p3,fp.get(0));
        for (MyPoint i:fp){
            double distanse = MyPoint.distanse(p3,i);
                if(distanse == minDistanse)
                    arr.add(i);
                if(distanse < minDistanse){
                    arr.clear();
                    arr.add(i);
                    minDistanse = distanse;
                }
        }

        return arr;
    }

    public void section(String prefix, MyPoint p1, MyPoint p2){

        int index = 1;

        ArrayList<MyPoint> pointList = getFigureMyPoints();
        FigureRandom fr = new FigureRandom(prefix + index);

        boolean tmp = findPlace(pointList.get(0), p1, p2);

        for (int i =0;i < pointList.size();i++) {
            boolean place = findPlace(pointList.get(i), p1, p2);

            if (place == tmp)
                fr.add(pointList.get(i));
            else {
                tmp = place;
                GraphicsMain.figureList.put(fr,fr.getFigureMyPoints());
                index++;
                fr = new FigureRandom(prefix + index);
                fr.add(pointList.get(i));
            }
        }
        GraphicsMain.figureList.put(fr,fr.getFigureMyPoints());
        GraphicsMain.figureList.remove(this);

    }

    public void drawProjection(String name,MyPoint p1, MyPoint p2){

        MyDoublePoint minPointX;
        MyDoublePoint minPointY;
        MyDoublePoint maxPointX;
        MyDoublePoint maxPointY;

        MyDoublePoint pp1 = new MyDoublePoint(p1);
        MyDoublePoint pp2 = new MyDoublePoint(p2);


        ArrayList<MyPoint> arr = getFigureMyPoints();
        minPointX = findPerpendicular(pp1,pp2,new MyDoublePoint(arr.get(0)));
        minPointY = findPerpendicular(pp1,pp2,new MyDoublePoint(arr.get(0)));
        maxPointX = findPerpendicular(pp1,pp2,new MyDoublePoint(arr.get(0)));
        maxPointY = findPerpendicular(pp1,pp2,new MyDoublePoint(arr.get(0)));


        for (MyPoint point:arr){
            MyDoublePoint p = findPerpendicular(pp1,pp2,new MyDoublePoint(point));

            if(p.x<minPointX.x)
                minPointX = p;
            if(p.y<minPointY.y)
                minPointY = p;
            if(p.x>maxPointX.x)
                maxPointX = p;
            if(p.y > maxPointY.y)
                maxPointY = p;

        }

        FigureLine line = new FigureLine(name);
        line.setPainted(false);

        if(maxPointX.x == minPointX.x){
            line.setP1(minPointY);
            line.setP2(maxPointY);
            GraphicsMain.figureList.put(line,new ArrayList<>());
            return;
        }

        line.setP1(minPointX);
        line.setP2(maxPointX);
        GraphicsMain.figureList.put(line,new ArrayList<>());
    }

    public void change(ArrayList<Integer> arr){
        if (arr.size()<3)
            return;
        int index = arr.get(0);
        if (index > controlMyPoints.size()){
            return;
        }

        controlMyPoints.set(index-1,new MyDoublePoint(arr.get(1),arr.get(2)));
        setPainted(false);

    }
}
