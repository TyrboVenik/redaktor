import org.junit.Test;

/**
 * Created by venik on 24.11.16.
 */
public class lol {
    @Test
    public void findClosest()throws  Exception{
        System.out.println("coming");
        FigureLine f = new FigureLine("e", 0,0,200,200);
        MyDoublePoint d =f.findPerpendicular(f.getP1(),f.getP2(),new MyDoublePoint(200,0));
        System.out.println(d.x+" "+d.y );
    }

    @Test
    public void findTri()throws  Exception{

        System.out.println(
        Figure.placeInTriangle(new MyDoublePoint(0,0),new MyDoublePoint(0,200),new MyDoublePoint(200,0),new MyDoublePoint(50,500))
        );

    }

    @Test
    public void findPer()throws  Exception{
        MyDoublePoint p = Figure.findPerpendicular(new MyDoublePoint(0,200),new MyDoublePoint(200,0),new MyDoublePoint(100,0));
        System.out.println(
                p.x + " " + p.y
        );

    }
}
