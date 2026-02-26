import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
public class Scene {
    private String sceneId;
    private BufferedImage backgroundImage;
    private ArrayList<GameObject> objectsInScene;
    public Scene(String sceneId, BufferedImage bg) {

    }
    public String getSceneId() {
        return "0"; //ใส่ไว้ก่อน กัน error
    }
    public void setSceneId(String sceneId) {

    }
    public BufferedImage getBackgroundImage() {
        return backgroundImage; //ใส่ไว้ก่อน กัน error
    }
    public void setBackgroundImage(BufferedImage bg) {

    }
    public ArrayList<GameObject> getObjectsInScene() {
        return objectsInScene; //ใส่ไว้ก่อน กัน error
    }
    public void setObjectsInScene(ArrayList<GameObject> objectsInScene) {

    }
    public void addGameObject(GameObject obj) {

    }
    public void removeGameObject(GameObject obj) {

    }
    public void update() {

    }
    public void render(Graphics2D g2d) {

    }
}
