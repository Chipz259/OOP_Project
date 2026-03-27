//package Minigame.UnlockBoxPackage;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class Main {
//    public static void main(String[] args) {
//        System.setProperty("sun.java2d.uiScale", "1.0");
//        UnlockBox ub = new UnlockBox();
//        JFrame frame = new JFrame("UnlockBox");
//
//        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
//        gd.setFullScreenWindow(frame);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.add(ub);
//        frame.setVisible(true);
//        Thread t = new Thread(ub);
//        t.start();
//    }
//}
