package entities;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class NPC extends GameObject implements Interactable{
    private ui.DialogueOverlay overlay;
    private system.DialogueLine[] dialogueScript;
    private BufferedImage[] idleFrames;
    private boolean isFacingLeft = false;
    private int spriteCounter = 0, spriteNum = 0;
    private final int ANIMATION_SPEED = 5;
    private int dLeftX = 50, dLeftY = 0, dLeftW = 700, dLeftH = 950;
    private int dRightX = 1200, dRightY = 0, dRightW = 700, dRightH = 950;

    public NPC(String id, int x, int y, int with, int height, String idleSpritePath, int frameCount, int frameWidth, int frameHeight) {
        super(id, x, y, with, height);
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

    public boolean isFacingLeft() {
        return this.isFacingLeft;
    }

    public void setFacingLeft(boolean isFacingLeft) {
        this.isFacingLeft = isFacingLeft;
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
    }

    @Override
    public void onInteract(Player p) {
        if (overlay != null && dialogueScript != null && !overlay.isActive()) {

            overlay.setCharacterTransform(dLeftX, dLeftY, dLeftW, dLeftH, dRightX, dRightY, dRightW, dRightH);

            overlay.startDialogue(dialogueScript, () -> {
                System.out.println("ระบบ: คุยกันจบแล้ว");
            });
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
    }
}
