package ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.imageio.ImageIO;

public class CutsceneEndCredit extends JPanel {
    private BufferedImage image;

    public CutsceneEndCredit(String imgpath) {
        try {
            image = ImageIO.read(getClass().getResource(imgpath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setBackground(Color.BLACK);
        this.setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
