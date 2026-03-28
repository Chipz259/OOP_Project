package ui;

import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class CutsceneEnd extends JPanel {
    private BufferedImage[] image;
    private int currentIndex = 0, wait = 0;
    private float alpha = 1.0f; //(1.0 = ทึบ 100%, 0.0 = ใสแจ๋ว 0%)
    private Timer timer;
    private boolean isFading = false;
    private Runnable onFinished;

    public CutsceneEnd(String[] imgpath, Runnable onFinished) {
        this.onFinished = onFinished;
        image = new BufferedImage[imgpath.length];
        for (int i = 0; i < imgpath.length; i++) {
            try {
                image[i] = ImageIO.read(getClass().getResource(imgpath[i]));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.setBackground(Color.BLACK);
        this.setOpaque(true);

        timer = new Timer(16, e -> updateCutscene());
        timer.start();
    }

    private void updateCutscene() {
        if (!isFading) {
               wait++;
               //ตั้งเวลาโชว์รูป: รูปสุดท้ายโชว์ 3 วินาที (187 รอบ), รูปอื่นๆ โชว์ 1 วินาที (62 รอบ)
               int targetTicks = (currentIndex == image.length - 1) ? 187 : 93;

               if (wait >= targetTicks) {
                   wait = 0;
                   if (currentIndex == image.length - 1) {
                       timer.stop();
                       if (onFinished != null) onFinished.run();
                   }
                   else {
                       isFading = true;
                   }
               }
        }
        else {
            //ลดความทึบแสงลงทีละ 0.016 (ใช้เวลา Fade รูปละประมาณ 1 วินาที)
            alpha -= 0.016f;

            if (alpha <= 0.0f) {
                alpha = 1.0f;
                currentIndex++;
                isFading = false;
            }
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g;

        if (currentIndex < image.length) {

            //วาดรูปคิวถัดไป แปะไว้เป็นฉากหลังสุดก่อน (เตรียมโผล่ขึ้นมาตอนเฟด)
            if (isFading && currentIndex + 1 < image.length) {
                BufferedImage nextImg = image[currentIndex + 1];
                if (nextImg != null) {
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                    g2d.drawImage(nextImg, 0, 0, getWidth(), getHeight(), this);
                }
            }

            BufferedImage currentImg = image[currentIndex];
            if (currentImg != null) {
                float safeAlpha = Math.max(0.0f, Math.min(1.0f, alpha));
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, safeAlpha));
                g2d.drawImage(currentImg, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
