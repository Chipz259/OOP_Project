package Minigame.RotateNarigaPackage;

import javax.swing.*;
import java.awt.*;

public class RotateNariga {
    private JPanel bg, test;
    private JFrame mainFrame;
    public  RotateNariga(){
        mainFrame = new JFrame();
        test = new test("Image/kem.png", 200, 200, 30);
        bg = new JPanel();

        bg.setBackground(new Color(127,127,127,255));
        bg.setLayout(null);
        ((JComponent) mainFrame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(40,240,40,240));
//        mainFrame.setUndecorated(true);
        mainFrame.setResizable(false);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(mainFrame);

        bg.add(test);
        mainFrame.add(bg);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }
}
