import java.awt.*;
import java.awt.image.BufferedImage;
public abstract class GameObject {
    private int x, y;
    private int width, height;
    private Rectangle hitbox;
    private BufferedImage sprite;
    private boolean isVisible;
    private String objectId;
    public GameObject(String id, int x, int y, int width, int height){

    }
    public int getX() {
        return 0; //ใส่ไว้ก่อน กัน error
    }
    public void setX(int x) {

    }
    public int getY() {
        return 0; //ใส่ไว้ก่อน กัน error
    }
    public void setY(int y) {

    }
    public int getWidth() {
        return 0; //ใส่ไว้ก่อน กัน error
    }
    public void setWidth(int width) {

    }
    public int getHeight() {
        return 0; //ใส่ไว้ก่อน กัน error
    }
    public void setHeight(int height) {

    }
    public Rectangle getHitbox() {
        return hitbox; //ใส่ไว้ก่อน กัน error
    }
    public BufferedImage getSprite() {
        return sprite; //ใส่ไว้ก่อน กัน error
    }
    public void setSprite(BufferedImage sprite) {

    }
    public boolean isVisible() {
        return true; //ใส่ไว้ก่อน กัน error
    }
    public void setVisible(boolean visible) {

    }
    public String getObjectId() {
        return "0"; //ใส่ไว้ก่อน กัน error
    }
    public void setObjectId(String objectId) {

    }
    public abstract void update();
    public abstract void render(Graphics2D g2d);
}
