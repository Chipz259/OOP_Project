package entities;

import system.FontManager;

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
            if (hoverURL != null) {
                this.hoverSprite = ImageIO.read(hoverURL);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getItemName() {
        return itemName;
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
        if (isVisible() && !isCollected) {
            BufferedImage imageToDraw = (isHovered && hoverSprite != null) ? hoverSprite : getSprite();
            if (imageToDraw != null) {
                g2d.drawImage(imageToDraw, getX(), getY(), getWidth(), getHeight(), null);
            }
            if (isHovered) {
                if (FontManager.customFont != null) {
                    g2d.setFont(FontManager.customFont.deriveFont(Font.PLAIN, 25));
                    FontMetrics fm = g2d.getFontMetrics();
                    int textWidth = fm.stringWidth(itemName);

                    int padding = 10;
                    int boxW = textWidth + (padding * 2);
                    int boxH = 35;
                    int boxX = getX() + (getWidth() / 2) - (boxW / 2);
                    int boxY = getY() - 45;

                    g2d.setColor(new Color(0, 0, 0, 180));
                    g2d.fillRoundRect(boxX, boxY, boxW, boxH, 10, 10);

                    g2d.setColor(Color.WHITE);
                    g2d.drawString(itemName, boxX + padding, boxY + 26);
                }
            }
        }
    }

    @Override
    public void onInteract(Player p) {
        if (!isCollected) {
            boolean success = p.getInventory().addItem(this);
            if (success) {
                System.out.println("เก็บของ : " + itemName + " ละจั๊ฟ");
                this.isCollected = true;
                this.setVisible(false);
            } else {
                System.out.println("เป๋าเต็มละเพื่อน");
            }
        }
    }

    public void changeImage(int x, int y, int width, int height, String newDefaultImg, String newHoverImg) {
        try {
            URL defaulUrl = getClass().getResource("/res/" + newDefaultImg);
            URL hoverUrl = getClass().getResource("/res/" + newHoverImg);

            if (defaulUrl != null) {
                BufferedImage image = ImageIO.read(defaulUrl);
                setSprite(image);
            }

            if (hoverUrl != null) {
                this.hoverSprite = ImageIO.read(hoverUrl);
            }

            setX(x);
            setY(y);
            setWidth(width);
            setHeight(height);

        } catch (Exception e) {
            System.out.println("โหลดภาพไม่ได้จ้าาาา");
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
