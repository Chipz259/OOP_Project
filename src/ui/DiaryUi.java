package ui;

import java.awt.*;
import javax.imageio.ImageIO;

public class DiaryUi {
    private static DiaryUi instance;

    private boolean isVisible = false;
    private int currentPage = 0;

    private String[] pages = {
            "วันที่ 32 ธันกรายน 2568\nอุอิอิอา",
            "วันที่ 0 มีฤศิราคม 0001\nว้ากๆๆๆๆ"
    };

    private Image bookImg;
    private Image arrowPrevImg, arrowPrevHoverImg;
    private Image arrowNextImg, arrowNextHoverImg;
    private Image closeBtnImg, closeBtnHoverImg;

    private Font diaryFont;
    private Rectangle rightRect, leftRect, closeRect;

    private boolean isNextHovered = false;
    private boolean isPrevHovered = false;
    private boolean isCloseHovered = false;

    private DiaryUi() {
        try {
            diaryFont = new Font("Arial", Font.PLAIN, 28);

            bookImg = ImageIO.read(getClass().getResource("/res/diary_bg.png"));

            arrowNextImg = ImageIO.read(getClass().getResource("/res/Right_Default.png"));
            arrowPrevImg = ImageIO.read(getClass().getResource("/res/Left_Default.png"));
            closeBtnImg = ImageIO.read(getClass().getResource("/res/SettingBtnBack01.png"));

            arrowNextHoverImg = ImageIO.read(getClass().getResource("/res/Right_Hover.png"));
            arrowPrevHoverImg = ImageIO.read(getClass().getResource("/res/Left_Hover.png"));
            closeBtnHoverImg = ImageIO.read(getClass().getResource("/res/SettingBtnBack02.png"));

            bookImg = bookImg.getScaledInstance(800, 600, Image.SCALE_SMOOTH);

            arrowNextImg = arrowNextImg.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            arrowNextHoverImg = arrowNextHoverImg.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

            arrowPrevImg = arrowPrevImg.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            arrowPrevHoverImg = arrowPrevHoverImg.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

            closeBtnImg = closeBtnImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            closeBtnHoverImg = closeBtnHoverImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static DiaryUi getInstance() {
        if (instance == null) {
            instance = new DiaryUi();
        }
        return instance;
    }

    public void openDiary() {
        isVisible = true;
        currentPage = 0;

        isNextHovered = false;
        isPrevHovered = false;
        isCloseHovered = false;
    }

    public void closeDiary() {
        isVisible = false;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public boolean handleClick(int mouseX, int mouseY) {
        if (isVisible == false) {
            return false;
        }
        if (closeRect != null && closeRect.contains(mouseX, mouseY)) {
            closeDiary();
            return true;
        }
        if (rightRect != null && rightRect.contains(mouseX, mouseY) && currentPage < pages.length - 1) {
            currentPage++;
            return true;
        }
        if (leftRect != null && leftRect.contains(mouseX, mouseY) && currentPage > 0) {
            currentPage--;
            return true;
        }
        return true;
    }
    public void handleMouseMove(int mouseX, int mouseY) {
        // หยุดการทำงาน
        if (isVisible == false) return;

        isNextHovered = false;
        isPrevHovered = false;
        isCloseHovered = false;

        if (closeRect != null && closeRect.contains(mouseX, mouseY)) {
            isCloseHovered = true;
        }
        if (rightRect != null && rightRect.contains(mouseX, mouseY)) {
            isNextHovered = true;
        }
        if (leftRect != null && leftRect.contains(mouseX, mouseY)) {
            isPrevHovered = true;
        }
    }

    public void draw(Graphics g2d, int screenWidth, int screenHeight) {
        //หยุดการทำงาน
        if (isVisible == false) return;

        // พื้นหลังสีดำโปร่งแสง
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(0, 0, screenWidth, screenHeight);

        int bookWidth = 800;
        int bookHeight = 600;
        int startX = (screenWidth - bookWidth) / 2;
        int startY = (screenHeight - bookHeight) / 2;

        if (bookImg != null) {
            g2d.drawImage(bookImg, startX, startY, null);
        }

        // วาดข้อความ
        g2d.setFont(diaryFont);
        g2d.setColor(Color.BLACK);
        int textX = startX + 100;
        int textY = startY + 100;

        String[] lines = pages[currentPage].split("\n");
        for (int i = 0; i < lines.length; i++) {
            g2d.drawString(lines[i], textX, textY);
            textY += 40;
        }

        int btnY = startY + bookHeight - 80;

        // วาดปุ่มย้อนกลับ
        if (currentPage > 0) {
            leftRect = new Rectangle(startX + 50, btnY, 50, 50);
            if (isPrevHovered == true) {
                g2d.drawImage(arrowPrevHoverImg, leftRect.x, leftRect.y, null);
            } else {
                g2d.drawImage(arrowPrevImg, leftRect.x, leftRect.y, null);
            }
        } else {
            leftRect = null;
        }

        // วาดปุ่มถัดไป
        if (currentPage < pages.length - 1) {
            rightRect = new Rectangle(startX + bookWidth - 100, btnY, 50, 50);
            if (isNextHovered == true) {
                g2d.drawImage(arrowNextHoverImg, rightRect.x, rightRect.y, null);
            } else {
                g2d.drawImage(arrowNextImg, rightRect.x, rightRect.y, null);
            }
        } else {
            rightRect = null;
        }

        // วาดปุ่มปิด
        closeRect = new Rectangle(startX + bookWidth - 60, startY + 20, 40, 40);
        if (isCloseHovered == true) {
            g2d.drawImage(closeBtnHoverImg, closeRect.x, closeRect.y, null);
        } else {
            g2d.drawImage(closeBtnImg, closeRect.x, closeRect.y, null);
        }
    }
}
