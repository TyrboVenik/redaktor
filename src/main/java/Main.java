import javax.swing.*;

/**
 * Created by venik on 10.11.16.
 */
public class Main {
    public static void main(String args[])throws Exception {

        /*Main main1 = new Main();
        Thread mainWindow = new Thread(main1::startMainWindow);
        mainWindow.start();

        Thread.sleep(1_000);

        Main main2 = new Main();
        Thread commandWindow = new Thread(main2::startCommandWindow);
        commandWindow.start();

        mainWindow.join();
        commandWindow.join();*/
        startCommandWindow();
        startMainWindow();
    }

    private static void startCommandWindow(){
        Interface frame = new Interface();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void startMainWindow(){

        GraphicsMain graphicsMain = new GraphicsMain();

        GraphicsMain.setApp(graphicsMain);
    }
}
