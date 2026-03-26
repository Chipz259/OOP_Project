package ui;

import java.awt.*;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.*;

public class DiaryUi extends JPanel {
    private static DiaryUi instance;
    private boolean isVisible = false;
    private int currentPage = 0;

    private String[] pages = {
            "วันที่ 26 มีนาคม 2569\nนักศึกษากลุ่มต่อไปนี้ ผมรบกวนย้ายเวลาจองคิวนำเสนอใน Jlearn\nให้อาจารย์หน่อยครับ  VAULT, Phawang, P'J Cafe และ Tower Debug \nพอดีผมติดธุระช่วง 08:45 - 10:15 น. นะครับ",
            "วันที่ 32 ธันกรายน 2568\nอุอิอิอา",
            "วันที่ 0 มีฤศิราคม 0001\nว้ากๆๆๆๆ",
            "เทสๆๆๆ\nเทส12321321321312"
    };

    private Image bookImg;
    private ImageIcon iconLeftNormal, iconLeftHover, iconRightNormal, iconRightHover, iconCloseNormal, iconCloseHover;
    private JButton btnLeft, btnRight, btnClose;

    private Font diaryFont;

    private DiaryUi() {
        try {
            InputStream is = getClass().getResourceAsStream("/res/Font/DSNSM__.TTF");
            if (is != null) {
                diaryFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 28f);
            } else {
                diaryFont = new Font("Arial", Font.PLAIN, 28);
            }

            bookImg = ImageIO.read(getClass().getResource("/res/DiaryBG.png"));

            iconLeftNormal = new ImageIcon(new ImageIcon("src/res/Left_Default.png").getImage().getScaledInstance(35, 76, Image.SCALE_SMOOTH));
            iconLeftHover = new ImageIcon(new ImageIcon("src/res/Left_Hover.png").getImage().getScaledInstance(35, 76, Image.SCALE_SMOOTH));
            iconRightNormal = new ImageIcon(new ImageIcon("src/res/Right_Default.png").getImage().getScaledInstance(35, 76, Image.SCALE_SMOOTH));
            iconRightHover = new ImageIcon(new ImageIcon("src/res/Right_Hover.png").getImage().getScaledInstance(35, 76, Image.SCALE_SMOOTH));
            iconCloseNormal = new ImageIcon(new ImageIcon("src/res/DiaryExitBtn.png").getImage().getScaledInstance(25, 26, Image.SCALE_SMOOTH));
//            iconCloseHover = new ImageIcon(new ImageIcon("src/res/GamePanelHoverBtnSetting.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        } catch (Exception ex) {
            ex.printStackTrace();
            diaryFont = new Font("Arial", Font.PLAIN, 24);
        }

        btnLeft = new JButton(iconLeftNormal);
        setupButtonStyle(btnLeft);
        btnLeft.setRolloverIcon(iconLeftHover);
        btnLeft.addActionListener(e -> prevPage());
        this.add(btnLeft);

        btnRight = new JButton(iconRightNormal);
        setupButtonStyle(btnRight);
        btnRight.setRolloverIcon(iconRightHover);
        btnRight.addActionListener(e -> nextPage());
        this.add(btnRight);

        btnClose = new JButton(iconCloseNormal);
        setupButtonStyle(btnClose);
        btnClose.setRolloverIcon(iconCloseHover);
        btnClose.addActionListener(e -> closeDiary());
        this.add(btnClose);

        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                // ไม่ต้องเขียนอะไร แค่มีไว้เพื่อดัก Event
            }
        });

        this.setLayout(null);
        this.setOpaque(false);
        this.setVisible(false);
    }

    private void setupButtonStyle(JButton btn) {
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void nextPage() {
        if (currentPage < pages.length - 1) {
            currentPage++;
            updateButtonLayout(); // อัปเดตการแสดงผลปุ่ม
            repaint(); // สั่งให้วาดข้อความใหม่ของหน้าที่เลือก
        }
    }

    private void prevPage() {
        if (currentPage > 0) {
            currentPage--;
            updateButtonLayout();
            repaint();
        }
    }

    private void updateButtonLayout() {
        // คำนวณตำแหน่งปุ่มให้สัมพันธ์กับตัวสมุดกึ่งกลางจอ
        int startX = (getWidth() - 800) / 2;
        int startY = (getHeight() - 600) / 2;

        btnLeft.setBounds(startX + 50, startY + 250, 35, 100);
        btnRight.setBounds(startX + 713, startY + 250, 35, 100);
        btnClose.setBounds(startX + 725, startY + 40, 25, 26);

        // ซ่อน/โชว์ตามจำนวนหน้า
        btnLeft.setVisible(currentPage > 0);
        btnRight.setVisible(currentPage < pages.length - 1);
    }

    public static DiaryUi getInstance() {
        if (instance == null) instance = new DiaryUi();
        return instance;
    }

    public void openDiary() {
        isVisible = true;
        currentPage = 0;
        this.setVisible(true);
        updateButtonLayout();
        this.requestFocusInWindow();
        repaint();
    }

    public void closeDiary() {
        isVisible = false;
        this.setVisible(false);
        if (MainGameFrame.getInstance() != null && MainGameFrame.getInstance().getGamePanel() != null) {
            MainGameFrame.getInstance().getGamePanel().requestFocusInWindow();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (!isVisible()) return;

        // 1. Draw Background Dim
        g2d.setColor(new Color(30, 30, 30, 200));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // 2. Draw Book
        int bookWidth = 800;
        int bookHeight = 600;
        int startX = (getWidth() - bookWidth) / 2;
        int startY = (getHeight() - bookHeight) / 2;

        if (bookImg != null) {
            g2d.drawImage(bookImg, startX, startY, bookWidth, bookHeight, null);
        }

        // 3. Draw Text
        g2d.setFont(diaryFont);
        g2d.setColor(Color.BLACK); // สีหมึกปากกา
        int textX = startX + 100;
        int textY = startY + 120;

        String[] lines = pages[currentPage].split("\n");
        for (String line : lines) {
            g2d.drawString(line, textX, textY);
            textY += 45; // ระยะบรรทัด
        }
    }
}
