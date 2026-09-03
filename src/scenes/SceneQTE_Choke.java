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
    private int targetClicks = 20; // กดกี่ครั้ง
    private long startTime;
    private int timeLimit = 5000;
    private boolean isQteActive = false;
    private double fadeWhiteAmount = 0;
    private boolean isWinningFade = false;
    private int buttonScale = 200;
    private int fadeAlpha = 0;
    private BufferedImage bgImage;
    private BufferedImage btnImage;
    private BufferedImage btnPressImage;
    private boolean hasStarted = false;
    private Player player;

    // --- ตัวแปรใหม่ที่เพิ่มเข้ามา ---
    private long lastClickTime = 0;
    private int clickCooldown = 120; // ดีเลย์กันกดค้าง (มิลลิวินาที) - ยิ่งมาก ยิ่งต้องกดเว้นจังหวะ
    private long lastDecayTime = 0;
    private int decayInterval = 400; // เวลาที่แต้มจะลดลง 1 แต้ม (มิลลิวินาที) - 300ms คือลดประมาณ 3 แต้มต่อวินาที
    // ----------------------------

    public SceneQTE_Choke(String sceneId, SceneManager sm, Player p) {
        super(sceneId);
        this.sceneManager = sm;
        this.player = p;
        try {
            URL bgImgURL = getClass().getResource("/res/Element/pLork.png");
            if (bgImgURL != null) {
                this.bgImage = ImageIO.read(bgImgURL);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            URL btnImgURL = getClass().getResource("/res/Element/eButton.png");
            if (btnImgURL != null) {
                this.btnImage = ImageIO.read(btnImgURL);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            URL btnPressImgURL = getClass().getResource("/res/Element/eButtonPress.PNG");
            if (btnPressImgURL != null) {
                this.btnPressImage = ImageIO.read(btnPressImgURL);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        AudioManager.preloadSFX("/res/sound/DonPLork.wav");
    }

    public void startQTE() {
        this.clickCount = 0;
        this.startTime = System.currentTimeMillis();

        // --- เซ็ตเวลาเริ่มต้นระบบใหม่ ---
        this.lastClickTime = this.startTime;
        this.lastDecayTime = this.startTime;
        // -----------------------------

        this.isQteActive = true;
        this.isWinningFade = false;
        this.fadeWhiteAmount = 0;
        this.buttonScale = 200;
        this.fadeAlpha = 0;
        AudioManager.playPreloadedSFX("/res/sound/DonPLork.wav", -5.0f);
    }

    public void registerClick() {
        if (isQteActive) {
            long currentTime = System.currentTimeMillis();

            // --- เช็คดีเลย์ ป้องกันการกดค้างหรือโปรแกรม Auto Clicker ---
            if (currentTime - lastClickTime >= clickCooldown) {
                clickCount++;
                timeLimit += 500;
                buttonScale = 240;
                lastClickTime = currentTime; // อัปเดตเวลาการกดล่าสุด
            }
        }
    }

    public void update() {
        if (sceneManager.getFadeTransition() != null && sceneManager.getFadeTransition().isFading()) {
            hasStarted = false;
            return;
        }

        if (!hasStarted) {
            startQTE();
            hasStarted = true;
        }

        if (isQteActive) {
            long currentTime = System.currentTimeMillis();
            long passTime = currentTime - startTime;

            // --- ระบบลดแต้ม (Decay) เมื่อเวลาผ่านไปตามรอบที่กำหนด ---
            if (currentTime - lastDecayTime >= decayInterval) {
                if (clickCount > 0) {
                    clickCount--; // ลดแต้มลง 1
                }
                lastDecayTime = currentTime; // เริ่มนับเวลารอบการลดแต้มใหม่
            }
            // --------------------------------------------------

            if (passTime > timeLimit) {
                isQteActive = false;

                sceneManager.getGamePanel().triggerDeath();
                System.out.println("ระบบ : แพ้ โดนบีบคอตายไปดิ");
                AudioManager.stopMusic();
                AudioManager.playSFX("/res/sound/LosePLork.wav", 0.0f);
            }
            else if (clickCount >= targetClicks) {
                isQteActive = false;
                isWinningFade = true;
                System.out.println("สวดเก่งนี่ รอด");
                AudioManager.stopMusic();
                AudioManager.playSFX("/res/sound/WinPLork.wav", 5.0f);
            }
        }

        if (buttonScale > 200) {
            buttonScale -= 5;
            if (buttonScale <= 200) {
                buttonScale = 200;
            }
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

                    // หากมีเรื่อง Objective Manager แบบรอบที่แล้ว อย่าลืมแทรกตรงนี้นะครับ
                    sceneManager.startTransition("scene_14", player, 900, 550);
                }
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
        if (isQteActive) {
            int width = buttonScale;
            int height = buttonScale;
            int centerX = 1550;
            int centerY = 350;
            int drawX = centerX - (width / 2);
            int drawY = centerY - (height / 2);
            if (buttonScale > 200) {
                if (btnPressImage != null) {
                    g2d.drawImage(btnPressImage, drawX, drawY, width, height, null);
                }
            }
            else {
                if (btnImage != null) {
                    g2d.drawImage(btnImage, drawX, drawY, width, height, null);
                }
            }
        }
        if (isWinningFade) {
            g2d.setColor(new Color(255, 255, 255, (int)fadeAlpha));
            g2d.fillRect(0, 0, 1920, 1080);
        }
    }
}