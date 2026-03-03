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
    private Image trackRed, trackGray, bgImage;

    public SettingPanel(MainGameFrame parent) {
        settingTitle = new JLabel("Settings");
        buttonBack = new JButton("Back to Menu");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20); // ระยะห่างแต่ละช่อง
        bgmLabel = new JLabel("BGM Volume");
        sfxLabel = new JLabel("SFX Volume");
        trackRed = new ImageIcon("src/res/").getImage();
        trackGray = new ImageIcon("src/res").getImage();
        bgImage = new ImageIcon("src/res/SettingMenuBG.png").getImage();

        setBackground(new Color(232, 94, 94, 200));
        setLayout(new GridBagLayout());
        setOpaque(false);

        // Title: Settings
        settingTitle.setFont(new Font("Arial", Font.BOLD, 80));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(settingTitle, gbc);

        // Adjust BGM
        bgmLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        add(bgmLabel, gbc);

        bgmSlider = createCustomSlider(0, 100, AudioManager.bgmVolume);
        sfxSlider = createCustomSlider(0, 100, AudioManager.sfxVolume);
        bgmSlider.addChangeListener(e -> {
            AudioManager.setBgmVolume(bgmSlider.getValue());
        });
        gbc.gridx = 1; gbc.gridy = 1;
        add(bgmSlider, gbc);

        // Adjust SFX
        sfxLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        sfxSlider.addChangeListener(e -> {
            AudioManager.setSfxVolume(sfxSlider.getValue());
        });
        gbc.gridx = 0; gbc.gridy = 2;
        add(sfxLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        add(sfxSlider, gbc);

        // Button Back
        buttonBack.setFont(new Font("Arial", Font.PLAIN, 40));
        buttonBack.addActionListener(e -> parent.toggleSetting(false));
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(buttonBack, gbc);
    }

    private JSlider createCustomSlider(int min, int max, int value) {
        slider = new JSlider(min, max, value);
        slider.setPreferredSize(new Dimension(500, 80));
        slider.setOpaque(false);
        slider.setFocusable(false);

        slider.setUI(new BasicSliderUI(slider) {
            @Override
            public void paintTrack(Graphics g) {
                g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // 1. วาดหลอดสีเทาเป็นพื้นหลังทั้งหมดก่อน
                g2d.drawImage(trackGray, trackRect.x, trackRect.y, trackRect.width, trackRect.height, null);

                // 2. คำนวณตำแหน่งจุดตัด (ตำแหน่งกึ่งกลางของตัวปรับเสียง)
                int thumbPos = thumbRect.x + (thumbRect.width / 2);

                // 3. ใช้เทคนิค Clip เพื่อวาดรูปสีแดงแค่ส่วนที่ลากถึง
                // เราจะจำกัดพื้นที่วาดให้เริ่มจากจุดซ้ายสุดของหลอด ไปจนถึงตำแหน่งตัวปรับ
                g2d.setClip(trackRect.x, 0, thumbPos - trackRect.x, getHeight());

                // 4. วาดรูปหลอดสีแดงทับลงไป (มันจะปรากฏแค่ในพื้นที่ Clip ที่เราตั้งไว้)
                g2d.drawImage(trackRed, trackRect.x, trackRect.y, trackRect.width, trackRect.height, null);

                g2d.dispose();
            }
            @Override
            protected Dimension getThumbSize() {
                return new Dimension(45, 45); // ปรับขนาดปุ่มเลื่อน
            }
        });
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
