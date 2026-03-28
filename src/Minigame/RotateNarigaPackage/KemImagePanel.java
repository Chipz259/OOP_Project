package Minigame.RotateNarigaPackage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class KemImagePanel extends JPanel {
    private BufferedImage img;
    private int angle = 0;
    public KemImagePanel(String path, int deg, int angle){
        try {
            img = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int w = img.getWidth(this);
        int h = img.getHeight(this);
        int size = (int) Math.ceil(Math.sqrt(w * w + h * h)) * 2;
        this.setSize(size, size);
        this.setOpaque(false);
        this.angle = angle;
//        this.setBackground(Color.GRAY);
    }
    public void rotate(int angle){
        this.angle = (angle + this.angle) % 360;
        repaint();
    }
    public int getAngle(){return angle;}
    public BufferedImage getImg(){return  img;}
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        int imgW = img.getWidth(this);

        double centerX = getWidth() / 2.0;
        double centerY = getHeight() / 2.0;
        g2.translate(centerX, centerY);

        g2.rotate(Math.toRadians(angle));

        g2.drawImage(img, -imgW / 2, 0,img.getWidth(), img.getHeight(), this);

        g2.dispose();
    }
}
