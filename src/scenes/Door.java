package scenes;

import java.awt.*;
import java.awt.image.BufferedImage;

import entities.*;
import system.FadeTransition;

public class Door extends GameObject implements Interactable {

    private int spawnX;
    private int spawnY;
    private String nextSceneId;
    private SceneManager sceneM;
    private BufferedImage hoverSpite;
    private boolean isHovered = false;

    public Door(String id, int x, int y, int width, int height, String nextSceneId, SceneManager sceneM, BufferedImage arrowImage, int spawnX, int spawnY) {
        super(id, x, y, width, height);
        this.nextSceneId = nextSceneId;
        this.sceneM = sceneM;

        //กำหนดรูปภาพลูกศรให้ตัวแปร sprite
        this.setSprite(arrowImage);

        //กำหนดจุดเกิดของ entities.Player
        this.spawnX = spawnX;
        this.spawnY = spawnY;
    }

    //Setter กับ Getter
    public String getNextSceneId() {
        return nextSceneId;
    }
    public void setNextSceneId(String nextSceneId) {
        this.nextSceneId = nextSceneId;
    }
    public int getSpawnX() {
        return spawnX;
    }
    public void setSpawnX(int spawnX) {
        this.spawnX = spawnX;
    }
    public int getSpawnY() {
        return spawnY;
    }
    public void setSpawnY(int spawnY) {
        this.spawnY = spawnY;
    }
    public void setHoverSpite(BufferedImage hoverSpite) {
        this.hoverSpite = hoverSpite;
    }
    public void setIsHovered(Boolean isHovered){
        this.isHovered = isHovered;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g2d) {
        if (!isVisible()) return;

        if (isHovered && hoverSpite != null) {
            g2d.drawImage(hoverSpite, getX(), getY(), getWidth(), getHeight(), null);
        }
        else if (getSprite() != null) {
            g2d.drawImage(getSprite(), getX(),  getY(), getWidth(), getHeight(), null);
        }
    }

    @Override
    public void onInteract(Player p) {
        System.out.println("ผู้เล่นคลิกลูกศร: เปลี่ยนไปฉาก " + this.getNextSceneId());
        if (sceneM != null) {
            sceneM.startTransition(this.getNextSceneId(), p, this.getSpawnX(), this.getSpawnY());
        }
    }

    @Override
    public void onHover() {

    }

    @Override
    public boolean isInteractable() {
        return true;
    }
}
