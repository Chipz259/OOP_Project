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
        if (isVisible() && !isHovered) {
            BufferedImage imageToDraw = (isHovered && hoverSprite != null) ? hoverSprite : getSprite();
            if  (imageToDraw != null) {
                g2d.drawImage(imageToDraw, getX(), getY(), getWidth(), getHeight(), null);
            }
            if (isHovered) {
                g2d.setFont(new Font("Arial", Font.BOLD, 20));
                // เงาดำ
                g2d.setColor(Color.BLACK);
                g2d.drawString(description, getX() + 2, getY() - 8);
                // ตัวหนังสือขาว
                g2d.setColor(Color.WHITE);
                g2d.drawString(description, getX(), getY() + 10);
            }
        }
    }
    @Override
    public void onInteract(Player p) {
        if (isCollected) {
            System.out.println("เก็บของ : " + itemName + " ละจั๊ฟ");
            this.isCollected = true;
            this.setVisible(false);
        }
    }
    @Override
    public void onHover() {
        this.isHovered = true;
    }
    @Override
    public boolean isInteractable() {
        return !isCollected;
    }
}
