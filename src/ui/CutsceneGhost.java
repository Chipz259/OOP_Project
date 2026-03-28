package ui;

import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.imageio.ImageIO;

public class CutsceneGhost extends JPanel {
    private BufferedImage image;
    private int x = 0, y = 0, count = 0;
    private Random random = new Random();
    private Timer shackTimer;

    public CutsceneGhost(MainGameFrame mainGameFrame, String imgpath, Runnable onFinished) {
        try {
            image = ImageIO.read(getClass().getResource(imgpath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setBackground(Color.BLACK);
        this.setOpaque(false);

        shackTimer = new Timer(30, e -> {
            count++;

            if (count >= 166) {
                shackTimer.stop();
                x = 0;
                y = 0;
                repaint();

                mainGameFrame.closeCutscene();
                if (onFinished != null) onFinished.run();
            } else {
                x = random.nextInt(31) - 15;
                y = random.nextInt(31) - 15;
                repaint();
            }
        });

        shackTimer.start();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int pading = 40;
        int imgW = image.getWidth() + pading;
        int imgH = image.getHeight() + pading;
        int drawX = (getWidth() - imgW) / 2 + x;
        int drawY = (getHeight() - imgH) / 2 + y;

        g.drawImage(image, drawX, drawY, imgW, imgH, this);
    }

}
