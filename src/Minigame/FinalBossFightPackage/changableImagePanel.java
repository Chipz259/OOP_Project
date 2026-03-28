package Minigame.FinalBossFightPackage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class changableImagePanel extends JPanel {
    private BufferedImage active, unactive, img;
    private boolean isTarget = false;

    public changableImagePanel(String unactivePath, String activePath){
        try {
            unactive = ImageIO.read(getClass().getResource(unactivePath));
            active = ImageIO.read(getClass().getResource(activePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setOpaque(false);
        setUnactive();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        if(!isTarget){
            int offset = 20;
            g2.drawImage(img,offset, offset, getWidth() - (offset*2), getHeight() - (offset*2), this);
        } else {
            g2.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
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
    public void setTarget(boolean b){
        isTarget = b;
        repaint();
    }
}
