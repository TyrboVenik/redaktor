
import java.util.ArrayList;

/**
 * Created by venik on 09.11.16.
 */
public class Functional {
    public static Thread currentPainting;
    public static String currentName;
    static {
        currentPainting = null;
    }

    public static MyPoint getMyPoint(){
        MyPoint MyPoint;
        if((MyPoint = CommandLine.getMyPoint())!=null){
            return MyPoint;
        }
        if (MouseLocation.clicked){
            MouseLocation.clicked = false;
            return new MyPoint(MouseLocation.xTouchPosition,MouseLocation.yTouchPosition);
        }
        return MyPoint;
    }

    public static MyPoint getValue(MyPoint centre){
        int r;
        if((r = CommandLine.getValue()) != -1){
            MyPoint MyPoint = new MyPoint(centre.x,centre.y+r);
            return MyPoint;
        }
        return null;
    }

    public static void drawLine(){
        Functional f = new Functional();
        if (currentPainting!= null)
            currentPainting.interrupt();
        currentPainting = new Thread(f::functionLine);
        MouseLocation.clicked = false;
        CommandLine.integers.clear();
        currentPainting .start();
    }


    public static void drawCircle(){
        Functional f = new Functional();
        if (currentPainting!= null)
            currentPainting.interrupt();
        currentPainting = new Thread(f::functionCircle);
        MouseLocation.clicked = false;
        CommandLine.integers.clear();
        currentPainting .start();
    }

    public static void drawBezier(){
        Functional f = new Functional();
        if (currentPainting!= null)
            currentPainting.interrupt();
        currentPainting = new Thread(f::functionBezier);
        MouseLocation.clicked = false;
        CommandLine.integers.clear();
        currentPainting .start();
    }

    public  void functionBezier() {
        MyPoint newMyPoint;
        try{
            while((newMyPoint = getMyPoint()) == null){
                Thread.sleep(40);
            }
        }catch (Exception e) {return;}

        MyPoint MyPoint1 = newMyPoint;


        FigureBezier bezier = new FigureBezier(currentName);

        bezier.addControlMyPoint(MyPoint1);
        GraphicsMain.figureList.put(bezier,new ArrayList<>());

        try{
            KeyBoardAdapter.enter = false;
            while(KeyBoardAdapter.enter == false) {
                //MyPoint MyPoint = new MyPoint(MouseLocation.xPosition, MouseLocation.yPosition);
                //bezier.addControlMyPoint(MyPoint);

                MyPoint myPoint = null;
                while ((newMyPoint = getMyPoint()) == null && KeyBoardAdapter.enter == false) {

                    if (myPoint == null && MouseLocation.mouseOn){
                        myPoint = new MyPoint(MouseLocation.xPosition, MouseLocation.yPosition);
                        bezier.addControlMyPoint(myPoint);
                    }

                    if (myPoint!= null && !myPoint.equals(new MyPoint(MouseLocation.xPosition, MouseLocation.yPosition)) && MouseLocation.xPosition>0 && MouseLocation.yPosition>0) {
                        myPoint = new MyPoint(MouseLocation.xPosition, MouseLocation.yPosition);
                        bezier.setLast(myPoint);
                        GraphicsMain.getApp().repaint();
                    }
                    Thread.sleep(40);
                }

                if (myPoint != null && KeyBoardAdapter.enter == true)
                    bezier.removeLast();

                if (myPoint == null && newMyPoint != null)
                    bezier.addControlMyPoint(newMyPoint);


                 GraphicsMain.getApp().repaint();
            }
        }catch (Exception e){
            System.out.println(e.toString());
            GraphicsMain.figureList.remove(bezier);
            GraphicsMain.getApp().repaint();
            return;
        }

    }

    public  void functionLine() {
        MyPoint newMyPoint;
        try{
            while((newMyPoint = getMyPoint()) == null){
                Thread.sleep(40);
            }
        }catch (Exception e) {return;}

        MyPoint MyPoint1 = newMyPoint;
        MyPoint MyPoint2 = new MyPoint(newMyPoint);

        FigureLine line = new FigureLine(currentName);

        line.setP1(MyPoint1);
        line.setP2(MyPoint2);
        GraphicsMain.figureList.put(line,new ArrayList<>());

        try{
            while((newMyPoint = getMyPoint()) == null){
                if(!MyPoint2.equals(new MyPoint(MouseLocation.xPosition,MouseLocation.yPosition))) {
                    if(Thread.interrupted()) {
                        GraphicsMain.figureList.remove(line);
                        GraphicsMain.getApp().repaint();
                        return;
                    }
                    MyPoint2 = new MyPoint(MouseLocation.xPosition,MouseLocation.yPosition);
                    line.setP2(MyPoint2);
                    GraphicsMain.getApp().repaint();
                }

                Thread.sleep(40);

            }
            line.setP2(newMyPoint);
            GraphicsMain.getApp().repaint();
        }catch (Exception e){
            GraphicsMain.figureList.remove(line);
            GraphicsMain.getApp().repaint();
            return;
        }

    }

    public  void functionCircle() {
        MyPoint newMyPoint;
        try{
            while((newMyPoint = getMyPoint()) == null){
                Thread.sleep(40);
            }
        }catch (Exception e) {return;}

        MyPoint MyPoint1 = newMyPoint;
        MyPoint MyPoint2 = new MyPoint(newMyPoint);

        FigureCircle circle = new FigureCircle(currentName);

        circle.setCenter(MyPoint1);
        circle.setRadius(0);
        GraphicsMain.figureList.put(circle,new ArrayList<>());
        try{
            while((newMyPoint = getMyPoint()) == null && (newMyPoint =getValue(MyPoint1)) == null){
                if(!MyPoint2.equals(new MyPoint(MouseLocation.xPosition,MouseLocation.yPosition))) {
                    if(MouseLocation.xPosition<0 || MouseLocation.yPosition<0){
                        continue;
                    }else {
                        MyPoint2 = new MyPoint(MouseLocation.xPosition,MouseLocation.yPosition);
                        circle.setRadius(MyPoint2);
                        GraphicsMain.getApp().repaint();
                    }
                }
                Thread.sleep(40);
            }
            circle.setRadius(newMyPoint);
            GraphicsMain.getApp().repaint();
        }catch (Exception e){
            GraphicsMain.figureList.remove(circle);
            GraphicsMain.getApp().repaint();
            return;
        }

    }

}
