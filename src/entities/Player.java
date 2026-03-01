package entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Player extends GameObject {
    private Inventory inventory;
    private int targetX;
    private  int targetY;
    private  float speed;
    private  boolean isMoving;
    private  boolean isAlive;
    private BufferedImage[] walkFrames;
    private BufferedImage[] idleFrames;

    private boolean isFacingLeft = false;
    private int spriteCounter = 0;
    private int spriteNum = 0;
    private final int ANIMATION_SPEED = 9;
    public Player(String id, int x, int y, int width, int height) {
        super(id, x, y, width, height, "testPlayer.png");
        loadAnimation();
    }
    private void loadAnimation() {
        try {
            URL walkUrl = getClass().getResource("/res/solWalk.png");
            BufferedImage walkSheet = ImageIO.read(walkUrl);
            walkFrames = new BufferedImage[8];
            for (int i = 0; i < walkFrames.length; i++) {
                walkFrames[i] = walkSheet.getSubimage(i * 100, 0, 100, 100);
            }
            URL idleUrl = getClass().getResource("/res/solIdle.png");
            BufferedImage idleSheet = ImageIO.read(idleUrl);
            idleFrames = new BufferedImage[6];
            for (int i = 0; i < idleFrames.length; i++) {
                idleFrames[i] = idleSheet.getSubimage(i * 100, 0, 100, 100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Inventory getInventory() {
        return inventory; //ใส่ไว้ก่อน กัน error
    }
    public void setInventory(Inventory inventory) {

    }
    public int getTargetX() {
        return 0; //ใส่ไว้ก่อน กัน error
    }
    public void setTargetX(int targetX) {

    }
    public int getTargetY() {
        return 0; //ใส่ไว้ก่อน กัน error
    }
    public void setTargetY(int targetY) {

    }
    public float getSpeed() {
        return 0; //ใส่ไว้ก่อน กัน error
    }
    public void setSpeed(float speed) {

    }
    public boolean isMoving() {
        return this.isMoving;
    }
    public void setMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }
    public boolean isFacingLeft() {
        return this.isFacingLeft;
    }
    public void setFacingLeft(boolean isFacingLeft) {
        this.isFacingLeft = isFacingLeft;
    }
    public boolean isAlive() {
        return true; //ใส่ไว้ก่อน กัน error
    }
    public void setAlive(boolean isAlive) {

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
            g2d.setColor(Color.BLUE);
            g2d.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    }
    public void moveTo(int x, int y) {

    }
    public void moveTo(GameObject target) {

    }
    public void interactWith(Interactable target) {

    }
}
