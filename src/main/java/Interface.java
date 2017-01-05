import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Writer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * Created by venik on 08.11.16.
 */
public class Interface extends JFrame {
    private JMenuBar menuBar;
    private static JTextArea textArea;

    private static JTextField textField;
    private static JTextField infoTextField;
    public static JTextField coordinateTextField;
    private JPanel panel = new JPanel();
    static {
        textField = new JTextField();
        infoTextField = new JTextField();

        textField.setColumns(28);
        infoTextField.setColumns(28);

        infoTextField.setText("Input command");
        infoTextField.setHorizontalAlignment(JTextField.CENTER);
        infoTextField.setEditable(false);


        textField.setHorizontalAlignment(JTextField.CENTER);
        coordinateTextField = new JTextField(5);
        coordinateTextField.setColumns(7);
        coordinateTextField.setEditable(false);
    }

    public Interface() {
        super("Interface");
        Font font = new Font("Verdana", Font.PLAIN, 11);


        menuBar = new JMenuBar();

        setJMenuBar(menuBar);

        pack();
        createGUI();
    }

    public void createGUI() {
        setAlwaysOnTop(true);

        panel.setLayout(new FlowLayout());

        createButtons();
        createCommanLine();
        createCoordinateText();

        textArea = new JTextArea(10, 25);
        textArea.setVisible(true);

        panel.add(new JScrollPane(textArea));
        getContentPane().add(panel);
        setPreferredSize(new Dimension(325,350));
    }

    private void createCoordinateText(){
        panel.add(coordinateTextField);
    }

    private void createButtons(){
        JButton printLineButton = new JButton("Line");
        JButton printCircleButton = new JButton("Circle");
        JButton printBezierButton = new JButton("Bezier");
        JButton mouseButton = new JButton("Mouse");

        printLineButton.setActionCommand("Line");
        printCircleButton.setActionCommand("Circle");
        printBezierButton.setActionCommand("Bezier");
        mouseButton.setActionCommand("Mouse");

        panel.add(mouseButton,0);
        panel.add(printLineButton,0);
        panel.add(printCircleButton,0);
        panel.add(printBezierButton,0);


        ActionListener lineActionListener = new LineActionListener();
        printLineButton.addActionListener(lineActionListener);

        ActionListener cirlceActionListener = new CircleActionListener();
        printCircleButton.addActionListener(cirlceActionListener);

        ActionListener bezierActionListener = new BezierActionListener();
        printBezierButton.addActionListener(bezierActionListener);

        ActionListener mouseListener = new MouseActionListener();
        mouseButton.addActionListener(mouseListener);
    }

    private void createCommanLine(){
        ActionListener enterActionListener = new EnterActionListener();

        textField.addActionListener(enterActionListener);
        panel.add(infoTextField);
        panel.add(textField);
    }

    public class LineActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) { Functional.currentName = null; Functional.drawLine();}
    }

    public class CircleActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) { Functional.drawCircle();}
    }

    public class BezierActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) { Functional.drawBezier();}
    }

    public class MouseActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            MouseLocation.mouseOn = !MouseLocation.mouseOn;
            if(MouseLocation.mouseOn){
                Interface.consolePrintln("Mouse ON");
                JButton f = (JButton) e.getSource();
                f.setText("Mouse Off");
            }
            else{
                Interface.consolePrintln("Mouse OFF");
                JButton f = (JButton) e.getSource();
                f.setText("Mouse ON");
            }
        }
    }

    public class EnterActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            consolePrintln(textField.getText());
            CommandLine.doAction(textField.getText());
        }

    }

    public static void consolePrint(String str){
        textArea.append(str);
    }

    public static void consolePrintln(String str){
        textArea.append(str);
        textArea.append("\n");
    }
}
