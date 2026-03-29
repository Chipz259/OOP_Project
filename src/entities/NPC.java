package entities;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

import system.FontManager;

public class NPC extends GameObject implements Interactable{
    private ui.DialogueOverlay overlay;
    private system.DialogueLine[] dialogueScript;
    private BufferedImage[] idleFrames;
    private boolean isFacingLeft = false;
    private int spriteCounter = 0, spriteNum = 0;
    private final int ANIMATION_SPEED = 5;
    private int dLeftX = 50, dLeftY = 0, dLeftW = 700, dLeftH = 950;
    private int dRightX = 1200, dRightY = 0, dRightW = 700, dRightH = 950;
    private boolean isHovered = false;
    private String npcName;
    private Runnable onDialogueFinished;

    public NPC(String id,String npcName, int x, int y, int with, int height, String idleSpritePath, int frameCount, int frameWidth, int frameHeight) {
        super(id, x, y, with, height);
        this.npcName = npcName;
        loadAnimation(idleSpritePath, frameCount, frameWidth, frameHeight);
    }

    public void setDialogTransform(int lx, int ly, int lw, int lh, int rx, int ry, int rw, int rh) {
        this.dLeftX = lx; this.dLeftY = ly; this.dLeftW = lw; this.dLeftH = lh;
        this.dRightX = rx; this.dRightY = ry; this.dRightW = rw; this.dRightH = rh;
    }

    public void setVNDialogue(system.DialogueLine[] script, ui.DialogueOverlay overlay) {
        this.dialogueScript = script;
        this.overlay = overlay;
    }

    public void setVNDialogue(system.DialogueLine[] script, ui.DialogueOverlay overlay, Runnable onDialogueFinished) {
        this.dialogueScript = script;
        this.overlay = overlay;
        this.onDialogueFinished = onDialogueFinished;
    }

    private void loadAnimation(String idlePath, int frameCount, int frameWidth, int frameHeight) {
        try {
            URL idleUrl = getClass().getResource(idlePath);
            if (idleUrl != null) {
                BufferedImage idleSheet = ImageIO.read(idleUrl);
                idleFrames = new BufferedImage[frameCount];

                for (int i = 0; i < idleFrames.length; i++) {
                    // ตัดรูปภาพตามขนาดที่ระบุเข้ามา
                    idleFrames[i] = idleSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
                }
            } else {
                System.err.println("ระบบ: หาไฟล์รูป NPC ไม่เจอ -> " + idlePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        spriteCounter++;
        if (spriteCounter > ANIMATION_SPEED) {
            spriteNum++;

            if (idleFrames != null && spriteNum >= idleFrames.length) {
                spriteNum = 0;
            }
            spriteCounter = 0;
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        if (idleFrames != null && spriteNum < idleFrames.length) {
            if (isFacingLeft) {
                g2d.drawImage(idleFrames[spriteNum], getX() + getWidth(), getY(), -getWidth(), getHeight(), null);
            }
            else {
                g2d.drawImage(idleFrames[spriteNum], getX(), getY(), getWidth(), getHeight(), null);
            }
        }
        if (isHovered == true) {
            if (FontManager.customFont != null) {
                g2d.setFont(FontManager.customFont.deriveFont(Font.PLAIN, 25f));

                FontMetrics fm = g2d.getFontMetrics();
                String displayName = npcName;
                int textWidth = fm.stringWidth(displayName);

                int padding = 10;
                int boxW = textWidth + (padding * 2);
                int boxH = 35;
                int boxX = getX() + (getWidth() / 2) - (boxW / 2);
                int boxY = getY() - 45;

                g2d.setColor(new Color(0, 0, 0, 180));
                g2d.fillRoundRect(boxX, boxY, boxW, boxH, 10, 10);

                g2d.setColor(Color.WHITE);
                g2d.drawString(displayName, boxX + padding, boxY + 26);
            }
        }
    }

    @Override
    public void onInteract(Player p) {
        if (overlay != null && dialogueScript != null && !overlay.isActive()) {

            overlay.setCharacterTransform(dLeftX, dLeftY, dLeftW, dLeftH, dRightX, dRightY, dRightW, dRightH);
            overlay.startDialogue(dialogueScript, onDialogueFinished);
        }
        else {
            System.out.println("NPC ตัวนี้ไม่มีบทพูด");
        }
    }

    @Override
    public boolean isInteractable() {
        return  true;
    }

    @Override
    public void onHover(){
        this.isHovered = true;
    }
    public void setHovered(boolean hovered) {
        this.isHovered = hovered;
    }
}
