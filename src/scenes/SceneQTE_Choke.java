package scenes;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class SceneQTE_Choke extends Scene {
    private int clickCount = 0;
    private int targetClicks = 20;
    private long startTime;
    private int timeLimit = 5000;
    private boolean isQteActive = false;
    private double fadeWhiteAmount = 0;
    private boolean isWinningFade = false;
    private int buttonScale = 100;
    private int fadeAlpha = 0;
    private BufferedImage bgImage;
    public SceneQTE_Choke(String sceneId) {
        super(sceneId);
        try {
            URL bgImgURL = getClass().getResource("/res/pLork.png");
            if (bgImgURL != null) {
                this.bgImage = ImageIO.read(bgImgURL);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void startQTE() {
        this.clickCount = 0;
        this.startTime = System.currentTimeMillis(); // นับเวลาเป็น ms naja
        this.isQteActive = true;
        this.isWinningFade = false;
        this.fadeWhiteAmount = 0;
        this.buttonScale = 100;
    }
    public void registerClick() {
        if (isQteActive) {
            clickCount++;
            buttonScale = 140;
        }
    }
    public void update() {
        if (isQteActive) {
            long passTime = System.currentTimeMillis() - startTime;
            if (passTime > timeLimit) {
                isQteActive = false;
                System.out.println("ระบบ : แพ้ โดนบีบคอตายไปดิ");
            }
            else if (clickCount >= targetClicks) {
                isQteActive = false;
                isWinningFade = true;
                System.out.println("สวดเก่งนี่ รอด");
            }
        }
        if (buttonScale > 100) {
            buttonScale -= 5;
        }
        if (isWinningFade && fadeAlpha < 255) {
            fadeAlpha += 5;
            if (fadeAlpha > 255) fadeAlpha = 255;
        }
        if (isWinningFade) {
            fadeWhiteAmount += (fadeWhiteAmount * 0.1) + 0.01;
            if (fadeWhiteAmount >= 1) {
                fadeWhiteAmount = 1; // จอสว่าง
            }
        }
    }
    public void render(Graphics2D g2d) {
        int renderOffsetX = 0;
        int renderOffsetY = 0;
        if (isQteActive) {
            double progress = (double) clickCount / targetClicks;
            int maxShake = (int) (progress * 20);
            long time = System.currentTimeMillis();
            renderOffsetX = (int) (Math.sin(time * 0.1) * maxShake);
            renderOffsetY = (int) (Math.cos(time * 0.12) * maxShake);
        }
        g2d.translate(renderOffsetX, renderOffsetY);
        if (bgImage != null) {
            g2d.drawImage(bgImage, 0, 0, 1920, 1080, null);
        }
        super.render(g2d);
        g2d.translate(-renderOffsetX, -renderOffsetY);
        if (isQteActive) {
            int centerX = 1920 / 2;
            int centerY =  1080 / 2;
            g2d.setColor(Color.RED);
            g2d.fillRect(centerX - (buttonScale / 2), centerY - (buttonScale / 2), buttonScale, buttonScale);
        }
        if (isWinningFade) {
            g2d.setColor(new Color(255, 255, 255, (int)fadeAlpha));
            g2d.fillRect(0, 0, 1920, 1080);
        }
    }
}
