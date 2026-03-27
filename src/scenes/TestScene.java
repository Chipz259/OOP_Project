package scenes;

import entities.GamePanel;
import system.FadeTransition; // 🌟 1. อย่าลืม import คลาสฟิล์มดำเข้ามาด้วยนะครับ

import javax.swing.*;
import java.awt.*;

public class TestScene {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "1.0");
        JFrame window = new JFrame();

        window.setUndecorated(true);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ===================================ฟ=======
        // 🌟 2. จำลองการสร้างแผ่น Fade เหมือนใน MainGameFrame
        // ==========================================
        FadeTransition fadeTransition = new FadeTransition();
        // ดึงขนาดจอคอมปัจจุบันมาตั้งค่าให้ฟิล์มดำคลุมมิด
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        fadeTransition.setBounds(0, 0, (int)screenSize.getWidth(), (int)screenSize.getHeight());

        // 🌟 3. ส่งแผ่น Fade เข้าไปให้ GamePanel รู้จัก
        GamePanel gamePanel = new GamePanel(null, fadeTransition);
        gamePanel.setBounds(0, 0, (int)screenSize.getWidth(), (int)screenSize.getHeight());

        // 🌟 4. ต้องใช้ JLayeredPane เพื่อซ้อนฟิล์มดำไว้ "บนสุด" (เหนือ GamePanel)
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(screenSize);

        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER);   // เกมอยู่ชั้นล่าง
        layeredPane.add(fadeTransition, JLayeredPane.DRAG_LAYER); // ฟิล์มดำอยู่ชั้นบนสุด

        // เอา LayeredPane ไปแปะในหน้าต่าง
        window.setContentPane(layeredPane);
        // ==========================================


        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        //เอาไว้ทดสอบฉากที่กำหนด
        if (gamePanel.sceneManager != null) {

            // สั่งโหลดฉาก 15 ทันที
            gamePanel.sceneManager.loadScene("scene_17");

            // 💡 (แถม) ถ้าอยากให้พระเอกไปยืนตรงจุดใดจุดหนึ่งของจอเพื่อเทสต์ไอเทมใกล้ๆ
            // เอาคอมเมนต์ออกแล้วแก้เลขพิกัดได้เลยครับ
            // gamePanel.mainPlayer.setX(960); // ยืนกลางจอ (แกน X)
            // gamePanel.mainPlayer.setY(550); // ยืนกลางจอ (แกน Y)
        }

        // บังคับให้หน้าต่างรับค่าคีย์บอร์ด (ไม่งั้นตัวละครจะเดินไม่ได้เวลาเทสต์)
        gamePanel.requestFocusInWindow();
        gamePanel.startGameThread();
    }
}