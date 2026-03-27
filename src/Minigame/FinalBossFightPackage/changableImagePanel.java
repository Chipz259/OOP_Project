package Minigame.FinalBossFightPackage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class changableImagePanel extends JPanel {
    private BufferedImage active, unactive, img;
    private BufferedImage tintedImg;

    public changableImagePanel(String unactivePath, String activePath){
        try {
            unactive = ImageIO.read(getClass().getResource(unactivePath));
            active = ImageIO.read(getClass().getResource(activePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setOpaque(false);
        setUnactive();
        this.setPreferredSize(new Dimension(170, 170));
        this.setMaximumSize(new Dimension(170, 170));
        this.setMinimumSize(new Dimension(170, 170));
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.drawImage(img, 0, 0, getWidth(),getHeight(),this);
        g2.dispose();
    }

    public void setUnactive(){
        img = unactive;
        repaint();
    }
    public void setActive(){
        img = active;
        repaint();
    }
    public BufferedImage getImage(){
        return img;
    }
}
