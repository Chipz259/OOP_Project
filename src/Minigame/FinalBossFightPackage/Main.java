package Minigame.FinalBossFightPackage;

import javax.swing.*;
import java.awt.*;

public class Main {
    static void main() {
        System.setProperty("sun.java2d.uiScale", "1.0");
        FinalBossFight fbf = new FinalBossFight();
        JFrame frame = new JFrame("UnlockBox");
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(fbf);
        frame.setVisible(true);
        fbf.requestFocusInWindow();
        Thread t = new Thread(fbf);
        t.start();
    }
}
