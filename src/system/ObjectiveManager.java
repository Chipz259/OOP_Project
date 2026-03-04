package system;

import entities.Inventory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ObjectiveManager {
    private List<Objective> objectives;
    private int currentIndex = 0;
    private Image emptyBoxImg;
    private Image tickedBoxImg;
    private Font objectiveFont;
    private ObjectiveManager objectiveManager;
    private Inventory inventory;
    public ObjectiveManager(Image emptyBoxImg, Image tickedBoxImg) {
        this.objectives = new ArrayList<Objective>();
        this.emptyBoxImg = emptyBoxImg;
        this.tickedBoxImg = tickedBoxImg;
        this.objectiveFont = new Font("Arial", Font.BOLD, 20);
    }
    public void initGame() {
        try {
            Image emptyBox = ImageIO.read(getClass().getResource("/res/uncheck.png"));
            Image tickedBox = ImageIO.read(getClass().getResource("/res/check.png"));

            objectiveManager = new ObjectiveManager(emptyBox, tickedBox);
            setupObjectives();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setupObjectives() {
        objectiveManager.addObjective(new Objective("obj1", "วางดอกไม้จันทร์ที่โลงศพพ่อ"));
        objectiveManager.addObjective(new Objective("obj2", "เข้านอน"));
        objectiveManager.addObjective(new Objective("obj3", "แปะยันต์ที่ประตู"));
        objectiveManager.addObjective(new Objective("obj4", "หาสมุดบันทึกพ่อ"));
        LinkedHashMap<String, String> ritualItems = new LinkedHashMap<>();
        ritualItems.put("knife", "มีดอาคม");
        ritualItems.put("holyWater", "น้ำมนต์"); //ตอนนี้มีแค่อันนี้
        ritualItems.put("rosary", "ลูกประคำ");
        ritualItems.put("parasite", "กาฝากไม้คูณตายพราย");

        objectiveManager.addObjective(new Objective("obj5", "หาของมาทำพิธี", ritualItems));
    }
    public void addObjective(Objective obj) {
        objectives.add(obj);
    }
    public void advanceObjective() {
        if (currentIndex < objectives.size() - 1 ) {
            currentIndex++;
        }
    }
    public void draw(Graphics2D g2d, Inventory inventory) {
        if (objectives.isEmpty() || currentIndex >= objectives.size()) return;

        Objective currentObj = objectives.get(currentIndex);

        int startX = 50;
        int startY = 50;
        int spacingY = 40;
        int boxTextGap = 15;

        g2d.setFont(objectiveFont);
        g2d.setColor(Color.WHITE);

        g2d.drawImage(emptyBoxImg, startX, startY, null);

        int textX = startX + emptyBoxImg.getWidth(null) + boxTextGap;
        int textY = startY + (emptyBoxImg.getHeight(null) / 2) + (g2d.getFontMetrics().getAscent() / 2) - 2;

        g2d.drawString(currentObj.displayText, textX, textY);

        if (currentObj.subItems != null) {
            int currentY = startY + spacingY;
            int subStartX = startX + 30;

            for (Map.Entry<String, String> entry : currentObj.subItems.entrySet()) {
                String itemKey = entry.getKey();     // ชื่อไอเทมไว้เช็คในกระเป๋า (เช่น "HolyWater")
                String subText = entry.getValue();   // ข้อความที่จะโชว์ (เช่น "น้ำมนต์")

                // เช็คว่ามีของในกระเป๋าไหม เพื่อเลือกรูปกล่องให้ถูก
                Image boxToDraw;
                if (inventory.hasItem(itemKey)) {
                    boxToDraw = tickedBoxImg;
                } else {
                    boxToDraw = emptyBoxImg;
                }

                g2d.drawImage(boxToDraw, subStartX, currentY, null);

                int subTextX = subStartX + boxToDraw.getWidth(null) + boxTextGap;
                int subTextY = currentY + (boxToDraw.getHeight(null) / 2) + (g2d.getFontMetrics().getAscent() / 2) - 2;

                g2d.drawString(subText, subTextX, subTextY);

                currentY += spacingY;
            }
        }
    }
}
