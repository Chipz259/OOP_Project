package Minigame.UnlockBoxPackage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class SlotJPanel extends JPanel {
    private BufferedImage img;
    private int nowSlot = 0;
    private int targetY = 0;
    private int height;
    public SlotJPanel(String path) {
        try {
            img = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setOpaque(false);
        setSize(img.getWidth(), img.getHeight() / 3);
        height = img.getHeight() / 3;
    }
    public void updateSlot(int num){
        nowSlot = (nowSlot + num) % 3;
        if (nowSlot < 0){
            nowSlot = (nowSlot + 3) % 3;
        }
        targetY = nowSlot * height;
        repaint();
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(0, -targetY);
        g2d.drawImage(img, 0, 0, this);
        g2d.dispose();
    }
}
