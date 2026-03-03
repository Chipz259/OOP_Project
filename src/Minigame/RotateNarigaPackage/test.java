package Minigame.RotateNarigaPackage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class test extends JPanel {
    private Image img;
    private int angle = 0;
    public test(String path, int x, int y, int deg){
        img = new ImageIcon(getClass().getResource(path)).getImage();
        int w = img.getWidth(this);
        int h = img.getHeight(this);
        int size = (int) Math.ceil(Math.sqrt(w * w + h * h)) * 2;
        this.setBounds(x, y, size, size);
        this.addMouseListener(new RotateNarigaHandler(deg));
        this.setOpaque(false);
    }
    public void rotate(int angle){
        this.angle = (angle + this.angle) % 360;
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        int imgW = img.getWidth(this);
        int imgH = img.getHeight(this);

        double centerX = getWidth() / 2.0;
        double centerY = getHeight() / 2.0;

        g2.translate(centerX, centerY);

        g2.rotate(Math.toRadians(angle));

        g2.drawImage(img, -imgW / 2, 0, this);

        g2.dispose();
    }

}
