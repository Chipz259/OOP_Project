package ui;

import java.awt.*;

import system.AudioManager;
import system.FontManager;

public class TextAnimator {
    private String[] storyLines;
    private boolean isAuto, canSkip;
    private Runnable onComplete;

    private int currentLineIndex = 0, charIndex = 0, waitCounter = 0;
    private String currentDisplay = "";
    private boolean isLineFinished = false, isActive = false;

    public void startText(String[] lines, boolean auto, boolean canSkip, Runnable onComplete) {
        this.storyLines = lines;
        this.isAuto = auto;
        this.canSkip = canSkip;
        this.onComplete = onComplete;

        this.currentLineIndex = 0;
        this.charIndex = 0;
        this.currentDisplay = "";
        this.waitCounter = 0;
        this.isLineFinished = false;
        this.isActive = true;
    }

    public void update() {
        if (!isActive || storyLines == null || currentLineIndex == storyLines.length) {
            if (isActive) finishText();
            return;
        }

        String fullLine = storyLines[currentLineIndex];

        if (charIndex < fullLine.length()) {
            if (charIndex == 0) {
                AudioManager.playSFX("src/res/sound/SoundEffectCutsceen.wav", 0.0f);
            }

            currentDisplay += fullLine.charAt(charIndex);
            charIndex++;
            isLineFinished = false;
        }
        else {
            isLineFinished = true;
            AudioManager.stopSFX("src/res/sound/SoundEffectCutsceen.wav");
            if (isAuto) {
                waitCounter++;
                if (waitCounter >= 40) {
                    nextLine();
                }
            }
        }
    }

    public void handleMouseClick() {
        if (!isActive || storyLines == null || currentLineIndex >= storyLines.length) return;

        if (!isLineFinished) {
            if (canSkip) {
                currentDisplay = storyLines[currentLineIndex];
                charIndex = storyLines[currentLineIndex].length();
                isLineFinished = true;
                waitCounter = 0;
            }
        }
        else {
            if (!isAuto) {
                nextLine();
            }
        }
    }

    private void nextLine() {
        waitCounter = 0;
        charIndex = 0;
        currentDisplay = "";
        currentLineIndex++;
        isLineFinished = false;
    }

    private void finishText() {
        isActive = false;
        if (onComplete != null) {
            onComplete.run();
        }
    }

    public void draw(Graphics2D g2d, Font font, int x, int y, int screeWidth) {
        if (!isActive) return;

        if (font != null) {
            g2d.setFont(font.deriveFont(Font.PLAIN, 50f));
        }
        g2d.setColor(Color.WHITE);

        FontMetrics fm = g2d.getFontMetrics();
        int drawX;
        if (x == -1) {
            drawX = (screeWidth - fm.stringWidth(currentDisplay)) / 2;
        }
        else {
            drawX = x;
        }

        g2d.drawString(currentDisplay, drawX, y);
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isLineFinished() {
        return isLineFinished;
    }

    public String getCurrentDisplay() {
        return currentDisplay;
    }

    public int getCurrentLineIndex() {
        return currentLineIndex;
    }
}
