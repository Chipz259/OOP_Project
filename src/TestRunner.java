import entities.GamePanel;

import javax.swing.*;
public class TestRunner {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "1.0");

        JFrame window = new JFrame();
        window.setUndecorated(true);

        window.setExtendedState(JFrame.MAXIMIZED_BOTH);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}