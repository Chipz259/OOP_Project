package entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
public class Inventory {
    private BufferedImage slotSprite;
    private ArrayList<Item> itemlist;
    private int maxCapacity;
    private Item[] slots;

    public Inventory(String slotSpritePath) {
        slots = new Item[5];
        try {
            URL slotURL = getClass().getResource("/res/" + slotSpritePath);
            if  (slotURL != null) {
                this.slotSprite = ImageIO.read(slotURL);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Item> getItemList() {
        return itemlist; //ใส่ไว้ก่อน กัน error
    }

    public void setItemList(ArrayList<Item> itemList) {

    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public boolean addItem(Item item) {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] == null) {
                slots[i] = item;
                return true;
            }
        }
        return false;
    }
    public Item[] getSlots() {
        return slots;
    }
    public void removeItem(Item item) {

    }
    public boolean hasItem(String itemName) {
        return true; //ใส่ไว้ก่อน กัน error
    }
    public Item combineItems(Item item1, Item item2) {
        return item1; //ใส่ไว้ก่อน กัน error
    }
    public void render(Graphics2D g2d, int screenWidth, int screenHeight) {
        int slotSize = 100;
        int spaces = 20;
        int totalWidth = (slotSize * slots.length) + (spaces * (slots.length - 1));
        int startX = (screenWidth -  totalWidth) / 2;
        int y = screenHeight - 150;
        for (int i = 0; i < slots.length; i++) {
            int x = startX + (i * (slotSize + spaces));
            if  (slotSprite != null) {
                g2d.drawImage(slotSprite, x, y, slotSize, slotSize, null);
            }
            if (slots[i] != null) {
                int padding = 15;
                g2d.drawImage(slots[i].getSprite(), x + padding, y + padding, slotSize - (padding * 2), slotSize - (padding * 2), null);
            }
        }
    }
}
