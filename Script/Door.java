import java.awt.*;
public class Door extends GameObject implements Interactable {
    private String nextSceneId;
    private int spawnX;
    private int spawnY;
    public Door(String id, int x, int y, int width, int height, String nextSceneId, int spawnX, int spawnY) {
        super(id, x, y, width, height);
    }
    public String getNextSceneId() {
        return "0"; //ใส่ไว้ก่อน กัน error
    }
    public void setNextSceneId(String nextSceneId) {

    }
    public int getSpawnX() {
        return 0; //ใส่ไว้ก่อน กัน error
    }
    public void setSpawnX(int spawnX) {

    }
    public int getSpawnY() {
        return 0; //ใส่ไว้ก่อน กัน error
    }
    public void setSpawnY(int spawnY) {

    }
    @Override
    public void update() {

    }
    @Override
    public void render(Graphics2D g2d) {

    }
    @Override
    public void onInteract(Player p) {

    }
    @Override
    public void onHover() {

    }
    @Override
    public boolean isInteractable() {
        return true; //ใส่ไว้ก่อน กัน error
    }
}
