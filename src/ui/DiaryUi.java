package ui;

import scenes.SceneManager;
import system.FontManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.*;

public class DiaryUi extends JPanel {
    private static DiaryUi instance;
    private boolean isVisible = false;
    private int currentPage = 0;
    private BufferedImage[] bookImage;
    private ImageIcon iconLeftNormal, iconLeftHover, iconRightNormal, iconRightHover, iconCloseNormal, iconCloseHover;
    private JButton btnLeft, btnRight, btnClose;
    private boolean isFirstTime = false;

    private DiaryUi() {
        bookImage = new BufferedImage[3];

        try {
            bookImage[0] = ImageIO.read(getClass().getResource("/res/DiaryBG1.png"));
            bookImage[1] = ImageIO.read(getClass().getResource("/res/DiaryBG2.png"));
            bookImage[2] = ImageIO.read(getClass().getResource("/res/DiaryBG3.png"));

            iconLeftNormal = new ImageIcon(new ImageIcon("src/res/Left_Default.png").getImage().getScaledInstance(60, 132, Image.SCALE_SMOOTH));
            iconLeftHover = new ImageIcon(new ImageIcon("src/res/Left_Hover.png").getImage().getScaledInstance(60, 132, Image.SCALE_SMOOTH));
            iconRightNormal = new ImageIcon(new ImageIcon("src/res/Right_Default.png").getImage().getScaledInstance(60, 132, Image.SCALE_SMOOTH));
            iconRightHover = new ImageIcon(new ImageIcon("src/res/Right_Hover.png").getImage().getScaledInstance(60, 132, Image.SCALE_SMOOTH));
            iconCloseNormal = new ImageIcon(new ImageIcon("src/res/DiaryExitBtn.png").getImage().getScaledInstance(35, 36, Image.SCALE_SMOOTH));
        } catch (Exception ex) {
            System.out.println("ระบบ Diary : โหลดไม่ขึ้น");
            ex.printStackTrace();
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
            public void mousePressed(java.awt.event.MouseEvent e) {}
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
        if (currentPage < bookImage.length - 1) {
            currentPage++;
            updateButtonLayout();
            repaint();
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
        int startX = getWidth() - 1600;
        int startY = getHeight() - 906;

        btnLeft.setBounds(startX, startY + 300, iconLeftNormal.getIconWidth(), iconLeftNormal.getIconHeight());
        btnRight.setBounds(startX + 1220, startY + 300, iconRightNormal.getIconWidth(), iconRightNormal.getIconHeight());
        btnClose.setBounds(startX + 1080, startY + 28, iconCloseNormal.getIconWidth(), iconCloseNormal.getIconHeight());

        btnLeft.setVisible(currentPage > 0);
        btnRight.setVisible(currentPage < bookImage.length - 1);
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
            scenes.SceneManager sm = MainGameFrame.getInstance().getGamePanel().sceneManager;
            if (sm != null && sm.getOverlay() != null && isFirstTime == false) {
                isFirstTime = true;
                system.DialogueLine[] afterDiaryScript = {
                        new system.DialogueLine("ตุลย์", " ! ! !", null, null),
                        new system.DialogueLine("ตุลย์", "ที่ทุกคนแปลกไปเพราะงี้เองหรอ", null, null),
                        new system.DialogueLine("ตุลย์", "ตอนนี้คงต้องเริ่มหาของมาทำพิธีแล้วล่ะ", null, null)
                };

                sm.getOverlay().setCharacterTransform(50, 0, 706, 941, 1200, 0, 706, 941);
                sm.getOverlay().startDialogue(afterDiaryScript, () -> {
                });
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (!isVisible()) return;

        g2d.setColor(new Color(30, 30, 30, 200));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        if (bookImage != null && bookImage[currentPage] != null) {
            BufferedImage img = bookImage[currentPage];

            int newW = (int) (getWidth() * 0.75);
            int newH = (int) (newW * ((double) img.getHeight() / img.getWidth()));

            if (newH > getHeight() * 0.75) {
                newH = (int) (getHeight() * 0.75);
                newW = (int) (newH * ((double) img.getWidth() / img.getHeight()));
            }

            int x = (getWidth() - newW) / 2;
            int y = (getHeight() - newH) / 2;

            g2d.drawImage(img, x, y, newW, newH, null);
        }
    }
}
