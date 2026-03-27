package entities;

import ui.MainGameFrame;
import ui.Tutorial;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
public class Inventory {
    private int selectedSlot = -1;
    private int hoveredSlot = -1;
    private BufferedImage slotSprite;
    private BufferedImage slotHoverSprite;
    private Item[] slots;
    private Tutorial tutorial;
    private Boolean isShowCombine;

    public Inventory(String slotSpritePath, Tutorial tutorial) {
        this.tutorial = tutorial;
        isShowCombine = false;
        slots = new Item[5];
        try {
            URL slotURL = getClass().getResource("/res/" + slotSpritePath);
            if  (slotURL != null) {
                this.slotSprite = ImageIO.read(slotURL);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            URL slotHoverURL = getClass().getResource("/res/slotsHover.png");
            if  (slotHoverURL != null) {
                this.slotHoverSprite = ImageIO.read(slotHoverURL);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Item[] getSlots() {
        return slots;
    }

    public int getSelectedSlot() {
        return selectedSlot;
    }

    public boolean addItem(Item item) {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] == null) {
                slots[i] = item;

                checkSpecialEvents();

                return true;
            }
        }
        return false;
    }
    public void checkSpecialEvents() {
        if (!isShowCombine && hasItem("candle") && hasItem("water")) {
            tutorial.showTutorial("Combine");
            isShowCombine = true;
        }
    }
    public boolean removeItemId(String itemName) {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] != null) {
                if (slots[i].getObjectId().equals(itemName)) {
                    slots[i] = null;
                    return true;
                }
            }
        } return false;
    }
    public boolean hasItem(String itemName) {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] != null) {
                if (slots[i].getObjectId().equals(itemName)) {
                    return true;
                }
            }
        } return false;
    }
    public boolean isItemSelected(String itemName) {
        if (selectedSlot != -1 && slots[selectedSlot] != null) {
            return slots[selectedSlot].getObjectId().equals(itemName);
        }
        return false;
    }
    public void removeSelectedItem() {
        if (selectedSlot != -1) {
            slots[selectedSlot] = null;
            selectedSlot = -1;
        }
    }
    public Item combineItems(Item item1, Item item2) {
        String id1 = item1.getObjectId();
        String id2 = item2.getObjectId();
        if ((id1.equals("candle") && id2.equals("water")) || (id1.equals("water") && id2.equals("candle"))) {
            System.out.println("ระบบ : ทำน้ำมนต์สำมะเร็จเสร็จสิ้น");
            return new Item("holyWater", 0, 0, 100, 100, "น้ำมนต์", "น้ำมนต์กินแล้วตาย", "holyWater.png", "holyWaterHover.png");
        }
        return null;
    }
    public boolean handleClick(int mouseX, int mouseY, int screenWidth, int screenHeight) {
        int slotSize = 100;
        int spaces = 20;
        int totalWidth = (slotSize * slots.length) + (spaces * (slots.length - 1));
        int startX = (screenWidth - totalWidth) / 2;
        int y = screenHeight - 150;

        for (int i = 0; i < slots.length; i++) {
            int x = startX + (i * (slotSize + spaces));
            Rectangle slotHitbox = new Rectangle(x, y, slotSize, slotSize);
            if (slotHitbox.contains(mouseX, mouseY)) {
                processSlotClick(i);
                return true;
            }
        }
        return false;
    }
    public void handleHover(int mouseX, int mouseY, int screenWidth, int screenHeight) {
        int slotSize = 100;
        int spaces = 20;
        int totalWidth = (slotSize * slots.length) + (spaces * (slots.length - 1));
        int startX = (screenWidth - totalWidth) / 2;
        int y = screenHeight - 150;

        hoveredSlot = -1;

        for (int i = 0; i < slots.length; i++) {
            int x = startX + (i * (slotSize + spaces));
            Rectangle slotHitbox = new Rectangle(x, y, slotSize, slotSize);

            if (slotHitbox.contains(mouseX, mouseY)) {
                hoveredSlot = i;
                break;
            }
        }
    }
    public void processSlotClick(int clickedIndex) {
        if (selectedSlot == -1) {
            if (slots[clickedIndex] != null) {
                selectedSlot = clickedIndex;
                System.out.println("ระบบ : เลือกช่อง " + clickedIndex);
            }
        } else {
            if (selectedSlot == clickedIndex) {
                System.out.println("ระบบ : ยกเลิกเลือกช่อง " + clickedIndex);
                selectedSlot = -1;
            } else if (slots[clickedIndex] == null) {
                System.out.println("ระบบ : ย้ายไอเทม");
                slots[clickedIndex] = slots[selectedSlot];
                slots[selectedSlot] = null;
                selectedSlot = -1;
            } else if (slots[clickedIndex] != null) {
                Item item1 = slots[selectedSlot];
                Item item2 = slots[clickedIndex];
                Item result = combineItems(item1, item2);
                if (result != null) {
                    System.out.println("ระบบ : ผสมสำมะเร็จเสร็จสิ้น");
                    slots[clickedIndex] = null;
                    slots[selectedSlot] = result;
                } else {
                    System.out.println("รวมบ่ได้");
                }
                selectedSlot = -1;
            } else {
                selectedSlot = -1; // กดช่องว่าง
            }
        }
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
            if (i == selectedSlot) {
                g2d.drawImage(slotHoverSprite, x, y, slotSize, slotSize, null);
//                g2d.setColor(new Color(255, 0, 0, 100));
//                g2d.fillRect(x, y, slotSize, slotSize);
            }
            if (slots[i] != null) {
                int padding = 15;
                g2d.drawImage(slots[i].getSprite(), x + padding, y + padding, slotSize - (padding * 2), slotSize - (padding * 2), null);
            }
        }
        if (hoveredSlot != -1 && slots[hoveredSlot] != null) {
            String name = slots[hoveredSlot].getItemName();

            g2d.setFont(GamePanel.customFont.deriveFont(Font.BOLD, 20f));
            int textWidth = g2d.getFontMetrics().stringWidth(name);

            int hoverX = startX + (hoveredSlot * (slotSize + spaces));
            int hoverY = screenHeight - 160;

            g2d.setColor(new Color(0, 0, 0, 180));
            g2d.fillRoundRect(hoverX + (slotSize / 2) - (textWidth / 2) - 10, hoverY - 25, textWidth + 20, 30, 10, 10);

            g2d.setColor(Color.WHITE);
            g2d.drawString(name, hoverX + (slotSize / 2) - (textWidth / 2), hoverY - 5);
        }
    }
}
