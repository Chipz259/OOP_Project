package ui;

import system.DialogueLine;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DialogueOverlay {
    private TextAnimator textAnimator;
    private DialogueLine[] dialogueLines;
    private BufferedImage dialogBoxImage;
    private Font customFont;

    //ตั้งค่าเริ่มต้น
    private int leftX = 50, leftY = 150, leftW = 600, leftH = 800;
    private int rightX = 1250, rightY = 150, rightW = 600, rightH = 800;

    public DialogueOverlay(Font font, BufferedImage dialogBoxImage) {
        this.customFont = font;
        this.dialogBoxImage = dialogBoxImage;
        this.textAnimator = new TextAnimator();
    }

    public void setCharacterTransform(int lx,int ly,int lw,int lh, int rx, int ry, int rw, int rh) {
        this.leftX = lx;
        this.leftY = ly;
        this.leftW = lw;
        this.leftH = lh;

        this.rightX = rx;
        this.rightY = ry;
        this.rightW = rw;
        this.rightH = rh;
    }

    public void startDialogue(DialogueLine[] lines, Runnable onComplete) {
        this.dialogueLines = lines;
        String[] textArray = new String[lines.length];
        for (int i = 0; i < lines.length; i++) {
            textArray[i] = lines[i].text;
        }

        textAnimator.startText(textArray, false, true, onComplete);
    }

    public void update() {
        if (textAnimator.isActive()) {
            textAnimator.update();
        }
    }

    public void handleMouseClick() {
        if (textAnimator.isActive()) {
            textAnimator.handleMouseClick();
        }
    }

    public void render(Graphics2D g2d, int screenWidth, int screenHeight) {
        if (!textAnimator.isActive() || dialogueLines == null) return;

        int currentIndex = Math.min(dialogueLines.length - 1, textAnimator.getCurrentLineIndex());
        DialogueLine currenLine = dialogueLines[currentIndex];

        g2d.setColor(new Color(0, 0, 0, 180));
        g2d.fillRect(0, 0, screenWidth, screenHeight);

        int buttonLeftY = screenHeight - leftH, buttonRightY = screenHeight - rightH;

        if (currenLine.leftImage != null) {
            g2d.drawImage(currenLine.leftImage, leftX, buttonLeftY, leftW, leftH, null);
        }

        if (currenLine.rightImage != null) {
            g2d.drawImage(currenLine.rightImage, rightX, buttonRightY, rightW, rightH, null);
        }

        int boxWidth = 1000, boxHeight = 300, boxX = (screenWidth - boxWidth) / 2, boxY = screenHeight - boxHeight - 20;

        if (dialogBoxImage != null) {
            g2d.drawImage(dialogBoxImage, boxX, boxY, boxWidth, boxHeight, null);
        }
        else {
            g2d.setColor(new Color(200, 0, 0, 200));
            g2d.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 30, 30);
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(5));
            g2d.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 30, 30);
        }

        g2d.setFont(customFont.deriveFont(Font.BOLD, 55f));
        g2d.setColor(Color.WHITE);
        //ชื่อตัวละคร
        g2d.drawString(currenLine.speackerName, boxX + 60, boxY + 80);
        //ตัวหนังสือบทพูด
        textAnimator.draw(g2d, customFont, boxX + 60, boxY + 160, screenWidth);
    }

    public boolean isActive() {
        return textAnimator.isActive();
    }
}
