package ui;

import system.AudioManager;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

public class SettingPanel extends JPanel {
    private JButton buttonBack;
    private JLabel settingTitle, bgmLabel, sfxLabel;
    private GridBagConstraints gbc;
    private JSlider slider, bgmSlider, sfxSlider;
    private Graphics2D g2d, g2;
    private Image trackRed, trackGray, scorllingImage, bgImage, titleImg, bgmTextImg, sfxTextImg, backBtnImg;
    private BasicSliderUI customUI;

    public SettingPanel(MainGameFrame parent) {
        settingTitle = new JLabel();
        bgmLabel = new JLabel();
        sfxLabel = new JLabel();
        buttonBack = new JButton("Back to Menu");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20); // ระยะห่างแต่ละช่อง
        trackRed = new ImageIcon("src/res/trackRed.png").getImage();
        trackGray = new ImageIcon("src/res/trackGrey.png").getImage();
        scorllingImage = new ImageIcon("src/res/Scrolling.png").getImage();
        bgImage = new ImageIcon("src/res/SettingMenuBG.png").getImage();
        titleImg = new ImageIcon("src/res/SettingTitle.png").getImage();
        bgmTextImg = new ImageIcon("src/res/BGMText.png").getImage();
        sfxTextImg = new ImageIcon("src/res/SFXText.png").getImage();


        setBackground(new Color(232, 94, 94, 200));
        setLayout(new GridBagLayout());
        setOpaque(false);

        // Title: Settings
        settingTitle.setIcon(new ImageIcon(titleImg.getScaledInstance(400, 100, Image.SCALE_SMOOTH)));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; // อยู่แถวแรก ยึด 2 คอลัมน์
        gbc.anchor = GridBagConstraints.CENTER;
        add(settingTitle, gbc);

        // Adjust BGM
        bgmLabel.setIcon(new ImageIcon(bgmTextImg.getScaledInstance(200, 40, Image.SCALE_SMOOTH)));
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; // คอลัมน์ซ้าย แถวที่ 2
        gbc.anchor = GridBagConstraints.EAST; // ชิดขวามาหาหลอด
        add(bgmLabel, gbc);

        bgmSlider = createCustomSlider(0, 100, AudioManager.bgmVolume);
        sfxSlider = createCustomSlider(0, 100, AudioManager.sfxVolume);
        bgmSlider.addChangeListener(e -> {
            AudioManager.setBgmVolume(bgmSlider.getValue());
        });
        gbc.gridx = 1; gbc.gridy = 1; // คอลัมน์ขวา แถวที่ 2
        gbc.anchor = GridBagConstraints.WEST; // ชิดซ้ายมาหาข้อความ
        add(bgmSlider, gbc);

        // Adjust SFX
        sfxLabel.setIcon(new ImageIcon(sfxTextImg.getScaledInstance(200, 40, Image.SCALE_SMOOTH)));
        gbc.gridx = 0; gbc.gridy = 2; // คอลัมน์ซ้าย แถวที่ 3
        gbc.anchor = GridBagConstraints.EAST;
        add(sfxLabel, gbc);

        sfxSlider = createCustomSlider(0, 100, AudioManager.sfxVolume);
        sfxSlider.addChangeListener(e -> {
            AudioManager.setSfxVolume(sfxSlider.getValue());
        });
        gbc.gridx = 1; gbc.gridy = 2; // คอลัมน์ขวา แถวที่ 3
        gbc.anchor = GridBagConstraints.WEST;
        add(sfxSlider, gbc);

        // Button Back
        buttonBack.setFont(new Font("Arial", Font.PLAIN, 40));
        buttonBack.addActionListener(e -> parent.toggleSetting(false));
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; // อยู่แถวล่างสุด ยึด 2 คอลัมน์
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonBack, gbc);
    }

    private JSlider createCustomSlider(int min, int max, int value) {
        slider = new JSlider(min, max, value);
        customUI = new BasicSliderUI(slider) {
            @Override
            public void paintTrack(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // วาดรูปหลอดสีเทา (พื้นหลัง)
                if (trackGray != null) {
                    g2d.drawImage(trackGray, trackRect.x, trackRect.y, trackRect.width, trackRect.height, null);
                }

                // คำนวณตำแหน่ง Thumb เพื่อทำ Clip วาดสีแดง
                int thumbPos = thumbRect.x + (thumbRect.width / 2);

                // วาดรูปหลอดสีแดง (เฉพาะส่วนที่ลากถึง)
                if (trackRed != null) {
                    Shape oldClip = g2d.getClip();
                    // Clip พื้นที่ตั้งแต่จุดเริ่มหลอด จนถึงตัวเลื่อน
                    g2d.setClip(trackRect.x, 0, thumbPos - trackRect.x, slider.getHeight());
                    g2d.drawImage(trackRed, trackRect.x, trackRect.y, trackRect.width, trackRect.height, null);
                    g2d.setClip(oldClip);
                }

                g2d.dispose();
            }

            @Override
            public void paintThumb(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // วาดรูป Scrolling (image_7.png) ลงไปแทนปุ่มเลื่อน
                if (scorllingImage != null) {
                    g2d.drawImage(scorllingImage, thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height, null);
                }

                g2d.dispose();
            }

            @Override
            protected Dimension getThumbSize() {
                // ปรับขนาดพื้นที่ปุ่มเลื่อนให้พอดีกับรูปภาพของคุณ (ลองปรับ 20, 45 ดูนะจ๊ะ)
                return new Dimension(20, 45);
            }
        };

        // 3. ตั้งค่าพื้นฐานและใส่ UI
        slider.setPreferredSize(new Dimension(500, 80));
        slider.setOpaque(false);
        slider.setFocusable(false);
        slider.setUI(customUI);

        return slider;
    }

    protected void paintComponent(Graphics g){
        g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // วาดรูปพื้นหลังให้เต็มพื้นที่ Panel
        if (bgImage != null) {
            // วาดรูปภาพลงไปแทนการวาดสี่เหลี่ยมสีแดงจ้ะ
            g2.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            // กรณีโหลดรูปไม่ขึ้น ให้วาดสีสำรองไว้ก่อน
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
        }

        g2.dispose();
        // ห้ามลบ super.paintComponent เพราะจะทำให้ Component ลูก (Slider, Button) หาย
        super.paintComponent(g);
    }
}
