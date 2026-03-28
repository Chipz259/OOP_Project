package entities;

import ui.Tutorial;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Player extends GameObject {
    private Inventory inventory;
    private  boolean isMoving;
    private BufferedImage[] walkFrames;
    private BufferedImage[] idleFrames;

    private boolean isFacingLeft = false;
    private int spriteCounter = 0;
    private int spriteNum = 0;
    private final int ANIMATION_SPEED = 4;
    public Player(String id, int x, int y, int width, int height, Tutorial tutorial) {
        super(id, x, y, width, height);
        inventory = new Inventory("slots.png", tutorial);
        loadAnimation();
    }
    private void loadAnimation() {
        try {
            URL walkUrl = getClass().getResource("/res/pWalk.png");
            BufferedImage walkSheet = ImageIO.read(walkUrl);
            walkFrames = new BufferedImage[12];
            for (int i = 0; i < walkFrames.length; i++) {
                walkFrames[i] = walkSheet.getSubimage(i * 622, 0, 622, 1299);;
            }
            URL idleUrl = getClass().getResource("/res/pIdle.png");
            BufferedImage idleSheet = ImageIO.read(idleUrl);
            idleFrames = new BufferedImage[12];
            for (int i = 0; i < idleFrames.length; i++) {   
                idleFrames[i] = idleSheet.getSubimage(i * 622, 0, 622, 1299);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void interactWith(Interactable target) {
        if (target != null && target.isInteractable()) {
            target.onInteract(this);
        }
    }
    public Inventory getInventory() {
        return this.inventory;
    }
    public boolean isMoving() {
        return this.isMoving;
    }
    public void setMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }
    public void setFacingLeft(boolean isFacingLeft) {
        this.isFacingLeft = isFacingLeft;
    }
    @Override
    public void update() {
        spriteCounter++;
        if (spriteCounter > ANIMATION_SPEED) {
            spriteNum++;
            if (isMoving()) {
                if (spriteNum >= walkFrames.length) {
                    spriteNum = 0;
                }
            } else {
                if (spriteNum >= idleFrames.length) {
                    spriteNum = 0;
                }
            }
            spriteCounter = 0;
        }
    }
    public void render(Graphics2D g2d) {
        if (isMoving() && walkFrames != null && spriteNum < walkFrames.length) {
            if (isFacingLeft) {
                g2d.drawImage(walkFrames[spriteNum], getX() + getWidth(), getY(), -getWidth(), getHeight(), null);
            } else {
                g2d.drawImage(walkFrames[spriteNum], getX(), getY(), getWidth(), getHeight(), null);
            }
        }
        else if (!isMoving() && idleFrames != null && spriteNum < idleFrames.length) {
            if (isFacingLeft) {
                g2d.drawImage(idleFrames[spriteNum], getX() + getWidth(), getY(), -getWidth(), getHeight(), null);
            } else {
                g2d.drawImage(idleFrames[spriteNum], getX(), getY(), getWidth(), getHeight(), null);
            }
        }
        else if (getSprite() != null) {
            g2d.drawImage(getSprite(), getX(), getY(), getWidth(), getHeight(), null);
        } else {
            if (isFacingLeft) {
                g2d.drawImage(walkFrames[spriteNum], getX() + getWidth(), getY(), -getWidth(), getHeight(), null);
            } else {
                g2d.drawImage(walkFrames[spriteNum], getX(), getY(), getWidth(), getHeight(), null);
            }
        }
    }
}
