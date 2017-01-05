import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GraphicsMain extends JFrame {
    private static GraphicsMain app;
    private int oldSize;

    private static BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
    private int imagXoffset = 10;
    private int imagYoffset = 10;
    public static ConcurrentHashMap<Figure, ArrayList<MyPoint>> figureList;

    static {
        figureList = new ConcurrentHashMap<>();
    }

    public GraphicsMain() {
        super("SuperRedaktor");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.gray);

        setSize(820, 620);
        addKeyListener(new KeyBoardAdapter());
        add(new MouseLocation());
        setVisible(true);


        Graphics2D gr2d = (Graphics2D) image.getGraphics();
        gr2d.setColor(Color.white);
        gr2d.fillRect(0, 0, 800, 600);

        getGraphics().drawImage(image, imagXoffset, imagYoffset, null);
    }
    @Override
    public void paint(Graphics g) {
        Graphics2D gr2d = (Graphics2D) image.getGraphics();

        boolean f = false;
        if (oldSize != figureList.size()) {
            f = true;
            oldSize = figureList.size();
        }
        for (Figure key : figureList.keySet()) {
            if (!key.getPaunted() && key.getClass() != FigureGroup.class) {

                f = true;
                figureList.remove(key);
                ArrayList<MyPoint> arr = key.getFigureMyPoints();
                figureList.put(key, arr);
                key.setPainted(true);

            }
        }
        if (f) {
            gr2d.setColor(Color.white);
            gr2d.fillRect(0, 0, 800, 600);
            gr2d.setColor(Color.black);
            for (ArrayList<MyPoint> value : figureList.values()) {
                if (value != null)
                    for (int i = 0; i < value.size(); i++) {
                        putMyPoint(gr2d, value.get(i));
                    }
            }
            g.drawImage(image, imagXoffset, imagYoffset, null);
        }
    }
    public static GraphicsMain getApp() {
        return app;
    }
    public static void setApp(GraphicsMain g) {
        app = g;
    }
    private void putMyPoint(Graphics2D g, MyPoint p) {
        g.drawLine(p.x - imagXoffset, p.y - imagYoffset, p.x - imagXoffset, p.y - imagYoffset);
    }

    public static void saveImage(String fileName) {
        try {
            ImageIO.write(image, "png", new File("src/main/resources/" + fileName + ".png"));

            ConcurrentHashMap<Figure, ArrayList<MyPoint>> newFigureList = new ConcurrentHashMap<>();

            for (Figure key : figureList.keySet()) {
                newFigureList.put(key, new ArrayList<>());
            }

            for (Figure key : newFigureList.keySet()) {
                key.setPainted(false);
            }

            try (FileOutputStream fos = new FileOutputStream("src/main/resources/" + fileName + ".out");
                 ObjectOutputStream oos = new ObjectOutputStream(fos)
            ) {

                oos.writeObject(newFigureList);
                oos.flush();
            }

        } catch (Exception e) {
            Interface.consolePrintln("Cannot save image");
        }
    }

    public static void loadImage(String fileName) {
        try {

            FileInputStream fis = new FileInputStream("src/main/resources/" + fileName + ".out");
            ObjectInputStream oin = new ObjectInputStream(fis);
            figureList = (ConcurrentHashMap<Figure, ArrayList<MyPoint>>) oin.readObject();

            GraphicsMain.getApp().repaint();

        } catch (Exception e) {
            Interface.consolePrintln("Cannot load image");
        }
    }

    public static void delete(String name){
        for (Figure key : figureList.keySet()){
            if ( key.getName().equals( name)) {
                figureList.remove(key);
                getApp().repaint();
            }
        }
    }
    public static void rotate(String name,int alpha){
        for (Figure key : figureList.keySet()){
            if (key.getName().equals( name)) {
                key.rotate(alpha);
                getApp().repaint();
            }
        }
    }
    public static void section(String sourse,String prefix, MyPoint p1, MyPoint p2){
        for (Figure key : figureList.keySet()){
            if (key.getName().equals(sourse)) {
                key.section(prefix,p1,p2);
                getApp().repaint();
                return;
            }
        }
    }
    public static void projection(String sourse,String prefix, MyPoint p1, MyPoint p2){
        for (Figure key : figureList.keySet()){
            if (key.getName().equals(sourse)) {
                key.drawProjection(prefix,p1,p2);
                getApp().repaint();
            }
        }
    }

    public static void copy(String sourse,String copyName){
        for (Figure key : figureList.keySet()){
            if (key.getName().equals(sourse)) {
                Figure f = key.copy(copyName);
                figureList.put(f,new ArrayList<>());
                getApp().repaint();
            }
        }
    }
    public static void showNames(){
        for (Figure key:figureList.keySet()){
            if( key.getClass() == FigureLine.class){
                Interface.consolePrintln ("Line: "+key.getName());
            }
            if( key.getClass() == FigureBezier.class){
                Interface.consolePrintln("Bezier: "+key.getName());
            }
            if( key.getClass() == FigureCircle.class){
                Interface.consolePrintln("Circle: "+key.getName());
            }
            if( key.getClass() == FigureRandom.class){
                Interface.consolePrintln("CuttedFigure: "+key.getName());
            }
            if( key.getClass() == FigureGroup.class){
                Interface.consolePrintln("Group: "+key.getName());
            }
        }
    }
    public static void findClosest(String name, MyPoint point){
        for (Figure key : figureList.keySet()){
            if (key.getName().equals(name)) {
                ArrayList<MyPoint> myPointList = key.findMinDistance(point);
                if (myPointList == null)
                    Interface.consolePrintln("All point on the circle {" + name+"}");
                else {
                    for (MyPoint i: myPointList) {
                        Interface.consolePrint("(" + i.x + "," + i.y + ")");
                        Interface.consolePrintln("");
                    }
                }
            }
        }
    }

    public static void clipping(MyPoint p1,MyPoint p2, MyPoint p3) {
        for (Figure key : figureList.keySet())
            if (!key.hasPointInTriangle(new MyDoublePoint(p1), new MyDoublePoint(p2), new MyDoublePoint(p3)))
                figureList.remove(key);

        getApp().repaint();
    }

    public static void change(String name, ArrayList<Integer> arr){
        for (Figure key : figureList.keySet())
            if (key.getName().equals(name))
                key.change(arr);
        getApp().repaint();
    }

    public static void makeGroup(String groupName, ArrayList<String> nameList) {
        FigureGroup figureGroup = new FigureGroup();
        figureGroup.setName(groupName);
        for (String name: nameList){
            for(Figure figure: figureList.keySet()){
                if(figure.getName().equals(name)){
                    figureGroup.add(figure);
                }
            }
        }
        figureList.put(figureGroup,new ArrayList<>());
    }
}