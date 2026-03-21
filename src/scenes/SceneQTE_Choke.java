package scenes;

import entities.Player;
import system.AudioManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;


public class SceneQTE_Choke extends Scene {
    private SceneManager sceneManager;
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
    private BufferedImage btnImage;
    private boolean hasStarted = false;
    private Player player;

    public SceneQTE_Choke(String sceneId, SceneManager sm, Player p) {
        super(sceneId);
        this.sceneManager = sm;
        this.player = p;
        try {
            URL bgImgURL = getClass().getResource("/res/pLork.png");
            if (bgImgURL != null) {
                this.bgImage = ImageIO.read(bgImgURL);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            URL btnImgURL = getClass().getResource("/res/eButton.png");
            if (btnImgURL != null) {
                this.btnImage = ImageIO.read(btnImgURL);
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
        AudioManager.playMusic("src/res/sound/DonPLork.wav", 0.0f);
    }
    public void registerClick() {
        if (isQteActive) {
            clickCount++;
            buttonScale = 140;
        }
    }
    public void update() {
        if (sceneManager.getFadeTransition() != null && sceneManager.getFadeTransition().isFading()) {
            return;
        }

        if (!hasStarted) {
            startQTE();
            hasStarted = true;
        }
        if (isQteActive) {
            long passTime = System.currentTimeMillis() - startTime;
            if (passTime > timeLimit) {
                isQteActive = false;
                System.out.println("ระบบ : แพ้ โดนบีบคอตายไปดิ");
                AudioManager.stopMusic();
                AudioManager.playSFX("src/res/sound/LosePLork.wav", 0.0f);
            }
            else if (clickCount >= targetClicks) {
                isQteActive = false;
                isWinningFade = true;
                System.out.println("สวดเก่งนี่ รอด");
                AudioManager.stopMusic();
                AudioManager.playSFX("src/res/sound/WinPLork.wav", 0.0f);
            }
        }
        if (buttonScale > 100) {
            buttonScale -= 5;
        }

        if(isWinningFade) {

            fadeWhiteAmount += (fadeWhiteAmount * 0.1) + 0.1;
            if (fadeWhiteAmount >= 1) {
                fadeWhiteAmount = 1;
            }

            if (fadeAlpha < 255) {
                fadeAlpha += 5;

                if (fadeAlpha >= 255) {
                    fadeAlpha = 255;

                    sceneManager.startTransition("scene_6", player, 900, 550);
                }
            }

        }

        if (isWinningFade && fadeAlpha < 255) {
            fadeAlpha += 5;
            if (fadeAlpha > 255) fadeAlpha = 255;
        }
        if (isWinningFade && fadeAlpha >= 255) {
            sceneManager.startTransition("scene_4", player, 1650, 550);
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
            int baseShake = 8;
            int maxShake = baseShake + (int) (progress * 20);
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
        if (isQteActive && btnImage != null) {
            int width = buttonScale;
            int height = buttonScale;
            int centerX = 1550;
            int centerY = 350;
            int drawX = centerX - (width / 2);
            int drawY = centerY - (height / 2);
            g2d.drawImage(btnImage, drawX, drawY, width, height, null);
        }
        if (isWinningFade) {
            g2d.setColor(new Color(255, 255, 255, (int)fadeAlpha));
            g2d.fillRect(0, 0, 1920, 1080);
        }
    }
}
