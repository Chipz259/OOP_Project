package scenes;

import javax.swing.JFrame;
import entities.GamePanel;

public class TestScene {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "1.0");

        // 1. สร้างกรอบหน้าต่าง Windows
        JFrame window = new JFrame("My Adventure Game");
        window.setUndecorated(true);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false); // ห้ามย่อขยายหน้าจอ

        // 2. เรียก GamePanel เข้ามา
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        // 3. จัดหน้าจอให้พอดีและแสดงผล
        window.pack();
        window.setLocationRelativeTo(null); // เด้งหน้าต่างขึ้นมาตรงกลางจอ
        window.setVisible(true);

        // 4. สั่งให้ Game Loop (Thread) เริ่มทำงาน
        gamePanel.startGameThread();
    }
}
