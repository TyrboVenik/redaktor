
import java.awt.event.*;

/**
 * Created by venik on 11.11.16.
 */
public class KeyBoardAdapter implements KeyListener {

    private boolean ctrlClamped;
    public static boolean enter;
    {
        ctrlClamped = false;
        enter = false;
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 27)
                if (Functional.currentPainting!=null)
                    Functional.currentPainting.interrupt();

        if (e.getKeyCode() == 17)
            ctrlClamped = true;

        if (e.getKeyCode() == 10)
            enter = true;

        if (e.getKeyCode() == 90 && ctrlClamped == true){
            GraphicsMain.figureList.remove(GraphicsMain.figureList.size()-1);
            GraphicsMain.getApp().repaint();
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 17)
            ctrlClamped = false;
    }

    public void keyTyped(KeyEvent e) {}

}
