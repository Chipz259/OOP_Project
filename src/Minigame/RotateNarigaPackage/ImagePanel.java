package Minigame.RotateNarigaPackage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImagePanel extends JPanel {
    private BufferedImage img;
    public ImagePanel(String path){
        try {
            img = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.setOpaque(false);
    }
    public BufferedImage getImg() {return img;}
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        int centerX = getWidth() / 2;
        int centerY= getHeight() / 2;
        g2.translate(centerX, centerY);

        g2.drawImage(img, -img.getWidth() / 2, -img.getHeight() / 2, this);
    }
}
