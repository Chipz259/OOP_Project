import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
public abstract class GameObject {
    private int x, y;
    private int width, height;
    private Rectangle hitbox;
    private BufferedImage sprite;
    private boolean isVisible;
    private String objectId;

    public GameObject(String id, int x, int y, int width, int height){
        this.objectId = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isVisible = true;
        this.hitbox = new Rectangle(x, y, width, height);
    }
    public GameObject(String id, int x, int y, int width, int height, String spriteName){
        this.objectId = id;
        this.x = x;
        this.y = y;
        this.isVisible = true;
        URL spriteURL = this.getClass().getResource(spriteName);
    }
    public int getX() {
        return this.x;
    }
    public void setX(int x) {
        this.x = x;
        this.hitbox.setLocation(this.x, this.y);
    }
    public int getY() {
        return this.y;
    }
    public void setY(int y) {
        this.y = y;
        this.hitbox.setLocation(this.x, this.y);
    }
    public int getWidth() {
        return this.width;
    }
    public void setWidth(int width) {
        this.width = width;
        this.hitbox.setSize(this.width, this.height);
    }
    public int getHeight() {
        return this.height;
    }
    public void setHeight(int height) {
        this.height = height;
        this.hitbox.setSize(this.width, this.height);
    }
    public Rectangle getHitbox() {
        return this.hitbox;
    }
    public BufferedImage getSprite() {
        return this.sprite;
    }
    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }
    public boolean isVisible() {
        return isVisible; //ใส่ไว้ก่อน กัน error
    }
    public void setVisible(boolean visible) {
        isVisible = visible;
    }
    public String getObjectId() {
        return this.objectId;
    }
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public abstract void update();
    public abstract void render(Graphics2D g2d);
}
