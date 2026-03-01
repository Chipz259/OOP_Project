package entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.net.URL;
import java.io.IOException;
public class Item extends GameObject implements Interactable {
    private String itemName;
    private String description;
    private boolean isCollected;
    private boolean isHovered;
    private BufferedImage hoverSprite;
    public Item(String id, int x, int y, int width, int height, String name, String desc, String normalSprite, String hoverSpritePath) {
        super(id, x, y, width, height, normalSprite);
        this.itemName = name;
        this.description = desc;
        this.isCollected = false;
        this.isHovered = false;

        try {
            URL hoverURL = getClass().getResource("/res/" + hoverSpritePath);
            if  (hoverURL != null) {
                this.hoverSprite = ImageIO.read(hoverURL);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isCollected() {
        return isCollected;
    }
    public void setCollected(boolean isCollected) {
        this.isCollected = isCollected;
    }
    public void setHovered(boolean hovered) {
        this.isHovered = hovered;
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
