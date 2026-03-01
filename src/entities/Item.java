package entities;

import java.awt.*;
public class Item extends GameObject implements Interactable {
    private String itemName;
    private String description;
    private boolean isCollected;
    public Item(String id, int x, int y, int width, int height, String name, String desc) {
        super(id, x, y, width, height);
    }
    public String getItemName() {
        return "0"; //ใส่ไว้ก่อน กัน error
    }
    public void setItemName(String itemName) {

    }
    public String getDescription() {
        return "0"; //ใส่ไว้ก่อน กัน error
    }
    public void setDescription(String description) {

    }
    public boolean isCollected() {
        return true; //ใส่ไว้ก่อน กัน error
    }
    public void setCollected(boolean isCollected) {

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
