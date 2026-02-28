import java.awt.*;
public class Player extends GameObject {
    private Inventory inventory;
    private int targetX;
    private  int targetY;
    private  float speed;
    private  boolean isMoving;
    private  boolean isAlive;
    public Player(String id, int x, int y, int width, int height) {
        super(id, x, y, width, height, "testPlayer.png");
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
        return true; //ใส่ไว้ก่อน กัน error
    }
    public void setMoving(boolean isMoving) {

    }
    public boolean isAlive() {
        return true; //ใส่ไว้ก่อน กัน error
    }
    public void setAlive(boolean isAlive) {

    }
    @Override
    public void update() {

    }
    public void render(Graphics2D g2d) {
        if (getSprite() != null) {
            g2d.drawImage(getSprite(), getX(), getY(), getWidth(), getWidth(), null);
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
