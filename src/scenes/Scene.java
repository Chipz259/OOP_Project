package scenes;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.*;

public class Scene {
    private String sceneId;
    private BufferedImage backgroundImage;
    private ArrayList<GameObject> objectsInScene;

    public Scene(String sceneId) {
        this.sceneId = sceneId;
        this.objectsInScene = new ArrayList<>();
    }

    public String getSceneId() {
        return sceneId;
    }

    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }
    public void setBackgroundImage(BufferedImage bg) {
        this.backgroundImage = bg;
    }
    public ArrayList<GameObject> getObjectsInScene() {
        return objectsInScene;
    }

    public void addGameObject(GameObject obj) {
        if (obj != null) {
            objectsInScene.add(obj);
        }
    }

    public void removeGameObject(GameObject obj) {
        if (obj != null) {
            objectsInScene.remove(obj);
        }
    }

    public void update() {
        for (int i = 0; i < objectsInScene.size(); i++){
            GameObject obj = objectsInScene.get(i);
            obj.update();
        }
    }

    public void render(Graphics2D g2d) {
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, 1920, 1080, null);
        }

        for (GameObject obj : objectsInScene){
            if (obj.isVisible()){
                obj.render(g2d);
            }
        }
    }
}
