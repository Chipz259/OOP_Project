package Minigame.KonKlongPackage;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImagePanel extends JPanel {
    private BufferedImage img;
    private int angle = 0;

    public ImagePanel(String path){
        try {
            img = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setOpaque(false);
        setSize(img.getWidth(), img.getHeight());
    }
    public BufferedImage getImage(){return img;}
    public void rotateImage() {
        angle = (angle + 90) % 360;
        revalidate();
        repaint();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        double cx = getWidth() / 2.0;
        double cy = getHeight() / 2.0;

        g2.rotate(Math.toRadians(angle), cx, cy);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);

        g2.dispose();
    }

}
