package src;

import javax.swing.*;
import java.awt.*;

public interface Window {
    public boolean running();
    public void events();
    public void update();
    public void render();
}


//public static void main(String[] args) {
//    JFrame frame = new JFrame("Window");
//    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
//    frame.setBounds((screen.width/2) - 150, (screen.height/2) - 150, 300, 300);
//    frame.setVisible(true);
//}