import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by venik on 08.11.16.
 */
public class MouseLocation  extends JPanel {

        public volatile static int xPosition;
        public volatile static int yPosition;

        public volatile static int xTouchPosition;
        public volatile static int yTouchPosition;

        public volatile static boolean waiting;
        public volatile static boolean clicked;

        public volatile static boolean mouseOn;

        static {
            mouseOn = true;
            waiting = false;
            clicked = false;
        }

        public MouseLocation() {
            addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    xTouchPosition = e.getX();
                    yTouchPosition = e.getY();
                    clicked = true;
                }
            });
            addMouseMotionListener(new MouseMotionAdapter(){
                @Override
                public void mouseMoved(MouseEvent e) {
                    xPosition = e.getX();
                    yPosition = e.getY();
                    Interface.coordinateTextField.setText("X:"+ xPosition+ "Y:"+yPosition);
                }
            });
        }
}
