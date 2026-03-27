package system;

import entities.Inventory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ObjectiveManager {
    private static ObjectiveManager instance;

    private List<Objective> objectives;
    private int currentIndex = 0;
    private Image emptyBoxImg;
    private Image tickedBoxImg;
    private Font headerFont;
    private Font questFont;

    private ObjectiveManager() {
        this.objectives = new ArrayList<Objective>();

        try {
            InputStream is = getClass().getResourceAsStream("/res/Font/DSNSM__.TTF");
            Font baseFont = Font.createFont(Font.TRUETYPE_FONT, is);

            this.headerFont = baseFont.deriveFont(Font.BOLD, 40f);
            this.questFont = baseFont.deriveFont(Font.PLAIN, 26f);

        } catch (Exception e) {
            System.err.println("ระบบ: โหลดฟอนต์ใน ObjectiveManager ไม่ได้ ใช้ Arial แทนนะ");
            this.headerFont = new Font("Arial", Font.BOLD, 40);
            this.questFont = new Font("Arial", Font.PLAIN, 26);
        }

        try {
            int boxSize = 40;

            Image rawEmpty = ImageIO.read(getClass().getResource("/res/uncheck.png"));
            Image rawTicked = ImageIO.read(getClass().getResource("/res/check.png"));

            this.emptyBoxImg = rawEmpty.getScaledInstance(boxSize, boxSize, Image.SCALE_SMOOTH);
            this.tickedBoxImg = rawTicked.getScaledInstance(boxSize, boxSize, Image.SCALE_SMOOTH);

        } catch (Exception e) {
            System.err.println("ระบบ: หารูปกล่องภารกิจไม่เจอ!");
        }

        setupObjectives();
    }

    public static ObjectiveManager getInstance() {
        if (instance == null) {
            instance = new ObjectiveManager();
        }
        return instance;
    }

    private void setupObjectives() {
        objectives.add(new Objective("obj1", "วางดอกไม้จันทน์ที่โลงศพพ่อ"));
        objectives.add(new Objective("obj2", "เข้านอน"));
        objectives.add(new Objective("obj3", "แปะยันต์ที่ประตู"));
        objectives.add(new Objective("obj4", "ไปเช็คที่มาของเสียง"));
        objectives.add(new Objective("obj5", "เปิดสมุดบันทึกพ่อ"));

        String[][] ritualItems = {
                {"knife", "มีดอาคม"},
                {"holyWater", "น้ำมนต์"},
                {"rosary", "ลูกประคำ"},
                {"kafak", "กาฝากไม้คูณตายพราย"}
        };

        objectives.add(new Objective("obj6", "หาของมาทำพิธี", ritualItems));
        objectives.add(new Objective("obj7", "เอาชนะสิ่งชั่วร้าย"));
        objectives.add(new Objective("obj8", "ไปหน้าบ้าน"));
    }

    public void advanceObjective() {
        if (currentIndex < objectives.size() - 1 ) {
            currentIndex++;
        }
    }

    public void resetObjective() {
        currentIndex = 0;
    }

    public void draw(Graphics2D g2d, Inventory inventory) {
        if (objectives.isEmpty() || currentIndex >= objectives.size()) return;

        Objective currentObj = objectives.get(currentIndex);

        int startX = 50;
        int startY = 90;
        int spacingY = 40;
        int boxTextGap = 15;

        g2d.setColor(Color.WHITE);
        g2d.setFont(headerFont);
        g2d.drawString("ภารกิจหลัก (Objective)", startX, startY);

        startY += 50;
        g2d.setFont(questFont);

        int boxWidth = 30;
        int boxHeight = 30;

        if (emptyBoxImg != null) {
            g2d.drawImage(emptyBoxImg, startX, startY, null);
            boxWidth = emptyBoxImg.getWidth(null);
            boxHeight = emptyBoxImg.getHeight(null);
        }

        int textX = startX + boxWidth + boxTextGap;
        int textY = startY + (boxHeight / 2) + (g2d.getFontMetrics().getAscent() / 2) - 2;

        g2d.drawString(currentObj.displayText, textX, textY);

        if (currentObj.subItems != null) {
            int currentY = startY + spacingY;
            int subStartX = startX + 30;

            for (int i = 0; i < currentObj.subItems.length; i++) {
                String itemKey = currentObj.subItems[i][0]; // ช่องที่ 0 คือ รหัสไอเทม (เช่น "knife")
                String subText = currentObj.subItems[i][1]; // ช่องที่ 1 คือ ข้อความ (เช่น "มีดอาคม")

                Image boxToDraw = emptyBoxImg;
                if (inventory != null && inventory.hasItem(itemKey)) {
                    boxToDraw = tickedBoxImg;
                }

                int subBoxWidth = 20;
                int subBoxHeight = 20;

                if (boxToDraw != null) {
                    g2d.drawImage(boxToDraw, subStartX, currentY, null);
                    subBoxWidth = boxToDraw.getWidth(null);
                    subBoxHeight = boxToDraw.getHeight(null);
                }

                int subTextX = subStartX + subBoxWidth + boxTextGap;
                int subTextY = currentY + (subBoxHeight / 2) + (g2d.getFontMetrics().getAscent() / 2) - 2;

                g2d.drawString(subText, subTextX, subTextY);
                currentY += spacingY;
            }
        }
    }
}