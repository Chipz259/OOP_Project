package Minigame.FinalBossFightPackage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class changableImagePanel extends JPanel {
    private BufferedImage img;
    private BufferedImage tintedImg;
    private Color unactive = new Color(67, 69, 69, 255);
    private Color active = new Color(71, 248, 248, 255);

    public changableImagePanel(String path){
        try {
            img = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setOpaque(false);
        setTint(unactive);
        this.setPreferredSize(new Dimension(170, 170));
        this.setMaximumSize(new Dimension(170, 170));
        this.setMinimumSize(new Dimension(170, 170));
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(133, 128, 128, 255));
        g2.fillOval(0, 0, getWidth(), getHeight());

        g2.translate(getWidth()/2,getHeight()/2);

        g2.drawImage(tintedImg, -img.getWidth()/2, -img.getHeight()/2, this);
    }
    public void setTint(Color color) {
         tintedImg = new BufferedImage(
                img.getWidth(),
                img.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D tg = tintedImg.createGraphics();
        tg.drawImage(img, 0, 0, null);
        tg.setComposite(AlphaComposite.SrcIn);
        tg.setColor(color);
        tg.fillRect(0, 0, img.getWidth(), img.getHeight());
        tg.dispose();

        repaint();
    }

    public void setUnactive(){
        this.setTint(unactive);
    }
    public void setActive(){
        this.setTint(active);
    }
}
