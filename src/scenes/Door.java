package scenes;

import java.awt.*;
import java.awt.image.BufferedImage;

import entities.*;

public class Door extends GameObject implements Interactable {

    private int spawnX;
    private int spawnY;
    private String nextSceneId;
    private SceneManager sceneM;

    public Door(String id, int x, int y, int width, int height, String nextSceneId, SceneManager sceneM, BufferedImage arrowImage) {
        super(id, x, y, width, height);
        this.nextSceneId = nextSceneId;
        this.sceneM = sceneM;

        //กำหนดรูปภาพลูกศรให้ตัวแปร sprite
        this.setSprite(arrowImage);

        //กำหนดจุดเกิดของ entities.Player
        this.spawnX = 100;
        this.spawnY = 500;
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


    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g2d) {
        if (getSprite() != null && isVisible()){
            g2d.drawImage(getSprite(), this.getX(), this.getY(), this.getSpawnX(), this.getSpawnY(), null);
        }
    }

    @Override
    public void onInteract(Player p) {
        System.out.println("ผู้เล่นคลิกลูกศร: เปลี่ยนไปฉาก " + this.getNextSceneId());
        if (sceneM != null) {
            sceneM.loadScene(this.getNextSceneId());
            p.setX(this.getSpawnX());
            p.setY(this.getSpawnY());
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
