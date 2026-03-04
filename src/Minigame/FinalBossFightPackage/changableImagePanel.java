package Minigame.FinalBossFightPackage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class changableImagePanel extends JPanel {
    private BufferedImage img;
    private BufferedImage tintedImg;
    public changableImagePanel(String path){
        try {
            img = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setOpaque(false);
        System.out.println(img.getColorModel().hasAlpha());
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        if (tintedImg != null) {
            g2.drawImage(tintedImg, 0, 0, this);
        } else {
            g2.drawImage(img, 0, 0, this);
        }
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
}
