import java.util.ArrayList;

/**
 * Created by venik on 09.12.16.
 */
public class FigureGroup extends  Figure{
    private ArrayList<Figure> figures;

    public FigureGroup (){
        figures = new ArrayList<>();
    }

    public void add(Figure figure){
        figures.add(figure);
    }

    @Override
    public void drawProjection(String name, MyPoint p1, MyPoint p2) {
        for (Figure figure:figures)
            figure.drawProjection(name, p1, p2);
    }

    @Override
    public ArrayList<MyPoint> findMinDistance(MyPoint p3) {
        ArrayList<MyPoint> arr = new ArrayList<>();

        for(Figure figure:figures){
            ArrayList<MyPoint> newArr = figure.findMinDistance(p3);
            for (MyPoint myPoint:newArr){
                if (arr.size() == 0 && myPoint.distanseTo(p3) < arr.get(0).distanseTo(p3)){
                    arr.clear();
                    arr.add(myPoint);
                }

                if (arr.size() != 0 && myPoint.distanseTo(p3) == arr.get(0).distanseTo(p3)){
                    arr.add(myPoint);
                }
            }
        }
        return arr;
    }

    @Override
    public void section(String prefix, MyPoint p1, MyPoint p2) {
        for(Figure figure: figures)
            figure.section(prefix, p1, p2);
    }

    @Override
    public ArrayList<MyPoint> getFigureMyPoints() {
        return null;
    }

    @Override
    public void rotate(int alpha) {
        for (Figure figure:figures)
            figure.rotate(alpha);
    }

    public ArrayList<Figure> getFigures() {
        return figures;
    }

    public void setFigures(ArrayList<Figure> figures) {
        this.figures = figures;
    }
}
