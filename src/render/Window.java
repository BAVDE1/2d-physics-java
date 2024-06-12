package src.render;

import javax.swing.*;
import java.awt.*;

public class Window {
    public boolean initiated = false;
    public boolean open = false;
    public JFrame frame;
    public int width;
    public int height;

    public void initWindow(String windowName, int ScreenWidth, int ScreenHeight) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        initWindow(windowName, (screen.width/2) - (ScreenWidth/2), (screen.height/2) - (ScreenHeight/2), ScreenWidth, ScreenHeight);
    }

    public void initWindow(String windowName, int posX, int posY, int ScreenWidth, int ScreenHeight) {
        if (!initiated) {
            width = ScreenWidth;
            height = ScreenHeight;

            JFrame newFrame = new JFrame(windowName);
            newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            newFrame.setBounds(posX, posY, width, height);
            frame = newFrame;

            initiated = true;
        }
    }

    public void open() {
        if (initiated) {
            frame.setVisible(true);
            open = true;
        }
    }
}


//public static void main(String[] args) {
//    JFrame frame = new JFrame("Window");
//    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
//    frame.setBounds((screen.width/2) - 150, (screen.height/2) - 150, 300, 300);
//    frame.setVisible(true);
//}