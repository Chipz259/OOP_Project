package Minigame.JigsawPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class JigsawPartHandler extends MouseAdapter {
    private Point offset;
    @Override
    public void mouseDragged(MouseEvent e) {
        JigsawParts panel = (JigsawParts) e.getSource();
        if (panel.isReachTarget()) return;

        BufferedImage img = panel.getImg();
        if (img == null) return;

        int pixel = img.getRGB(offset.x, offset.y);
        int alpha = (pixel >> 24) & 0xff;
        if (alpha > 10) {
            int newX = panel.getX() + e.getX() - offset.x;
            int newY = panel.getY() + e.getY() - offset.y;

            Container parent = panel.getParent();
            if (parent != null) {
                int maxX = parent.getWidth() - panel.getWidth();
                int maxY = parent.getHeight() - panel.getHeight();
                newX = Math.max(0, Math.min(newX, maxX));
                newY = Math.max(0, Math.min(newY, maxY));
            }
            panel.setLocation(newX, newY);
            panel.repaint();

            //คำนวณระยะห่างรอบเดียวตอนที่ลาก (ไม่ต้องประกาศ int ซ้ำ)
            int dX = Math.abs(panel.getX() - panel.getTargetX());
            int dY = Math.abs(panel.getY() - panel.getTargetY());
            int distance = (int) Math.sqrt(dX * dX + dY * dY);

            // ถ้าระยะห่างน้อยกว่า 15 Pixel ให้ "ดูด" เข้าที่
            if (distance < 15) {
                panel.setLocation(panel.getTargetX(), panel.getTargetY());
                panel.repaint();

                //เรียกคำสั่งได้ตรงๆ เลย เพราะ panel เป็น JigsawParts แล้ว
                panel.setReachTartget(true);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        offset = e.getPoint();
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        JigsawParts jp = (JigsawParts) e.getSource();
        System.out.println(jp.getX());
        System.out.println(jp.getY());
        System.out.println("--------------------");
    }

}
