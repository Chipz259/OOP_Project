package ui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

public class SettingPanel extends JPanel {
    private JButton buttonBack;
    private JLabel settingTitle, bgmLabel, sfxLabel;
    private GridBagConstraints gbc;
    private JSlider slider, bgmSlider, sfxSlider;
    private Graphics2D g2d;

    public SettingPanel(MainGameFrame parent) {
        settingTitle = new JLabel("Settings");
        buttonBack = new JButton("Back to Menu");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20); // ระยะห่างแต่ละช่อง
        bgmLabel = new JLabel("BGM Volume");
        sfxLabel = new JLabel("SFX Volume");

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
        bgmSlider.addChangeListener(e -> AudioManager.setBgmVolume(bgmSlider.getValue()));
        gbc.gridx = 1; gbc.gridy = 1;
        add(bgmSlider, gbc);

        // Adjust SFX
        sfxLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        gbc.gridx = 0; gbc.gridy = 2;
        add(sfxLabel, gbc);

        sfxSlider = createCustomSlider(0, 100, AudioManager.sfxVolume);
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
        slider.setPreferredSize(new Dimension(400, 50));
        slider.setOpaque(false);

        slider.setUI(new BasicSliderUI(slider) {
            @Override
            public void paintTrack(Graphics g) {
                g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // กำหนดความหนาของหลอดตรงนี้ (ลองปรับเลข 20 ดูนะจ๊ะ)
                g2d.setStroke(new BasicStroke(20.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                // วาดเส้น Track
                super.paintTrack(g2d);
                g2d.dispose();
            }
            @Override
            protected Dimension getThumbSize() {
                return new Dimension(40, 40); // ปรับขนาดปุ่มเลื่อนเป็น 40x40
            }
        });
        return slider;
    }

    protected void paintComponent(Graphics g){
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
