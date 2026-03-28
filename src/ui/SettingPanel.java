package ui;

import system.AudioManager;
import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.event.*;

public class SettingPanel extends JPanel {
    private JButton buttonBack, buttonReturn;
    private JLabel settingTitle, bgmLabel, sfxLabel;
    private GridBagConstraints gbc;
    private JSlider slider, bgmSlider, sfxSlider;
    private Graphics2D g2d, g2;
    private Image trackRed, trackGray, scorllingImage, bgImage, titleImg, bgmTextImg, sfxTextImg, backNormalBtnImg, backHoverBtnImg, returnNormalBtnImg, returnHoverBtnImg;
    private BasicSliderUI customUI;
    private ImageIcon normalIconBack, hoverIconBack, normalIconReturn, hoverIconReturn;
    private MouseAdapter blocker;

    public SettingPanel(MainGameFrame parent) {
        settingTitle = new JLabel();
        bgmLabel = new JLabel();
        sfxLabel = new JLabel();
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        trackRed = new ImageIcon("src/res/SettingTrackRed.png").getImage();
        trackGray = new ImageIcon("src/res/SettingTrackGrey.png").getImage();
        scorllingImage = new ImageIcon("src/res/SettingScrolling.png").getImage();
        bgImage = new ImageIcon("src/res/SettingMenuBG.png").getImage();
        titleImg = new ImageIcon("src/res/SettingTitle.png").getImage();
        bgmTextImg = new ImageIcon("src/res/SettingBGMText.png").getImage();
        sfxTextImg = new ImageIcon("src/res/SettingSFXText.png").getImage();
        backNormalBtnImg = new ImageIcon("src/res/SettingBtnBack01.png").getImage();
        backHoverBtnImg = new ImageIcon("src/res/SettingBtnBack02.png").getImage();
        returnNormalBtnImg = new ImageIcon("src/res/SettingBtnReturn01.png").getImage();
        returnHoverBtnImg = new ImageIcon("src/res/SettingBtnReturn02.png").getImage();

        setBackground(new Color(232, 94, 94, 200));
        setLayout(new GridBagLayout());
        setOpaque(false);

        // Title: Settings
        settingTitle.setIcon(new ImageIcon(titleImg.getScaledInstance(400, 100, Image.SCALE_SMOOTH)));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(settingTitle, gbc);

        // Adjust BGM
        bgmLabel.setIcon(new ImageIcon(bgmTextImg.getScaledInstance(303, 62, Image.SCALE_SMOOTH)));
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(bgmLabel, gbc);

        bgmSlider = createCustomSlider(0, 100, AudioManager.bgmVolume);
        sfxSlider = createCustomSlider(0, 100, AudioManager.sfxVolume);
        bgmSlider.addChangeListener(e -> {
            AudioManager.setBgmVolume(bgmSlider.getValue());
        });
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(bgmSlider, gbc);

        // Adjust SFX
        sfxLabel.setIcon(new ImageIcon(sfxTextImg.getScaledInstance(295, 62, Image.SCALE_SMOOTH)));
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(sfxLabel, gbc);

        sfxSlider = createCustomSlider(0, 100, AudioManager.sfxVolume);
        sfxSlider.addChangeListener(e -> {
            AudioManager.setSfxVolume(sfxSlider.getValue());
        });
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(sfxSlider, gbc);

        // Button Back
        normalIconBack = new ImageIcon(backNormalBtnImg.getScaledInstance(135, 61, Image.SCALE_SMOOTH));
        hoverIconBack = new ImageIcon(backHoverBtnImg.getScaledInstance(135, 61, Image.SCALE_SMOOTH));
        buttonBack = new JButton(normalIconBack);
        buttonBack.setRolloverIcon(hoverIconBack);
        buttonBack.setBorderPainted(false);
        buttonBack.setContentAreaFilled(false);
        buttonBack.setFocusable(false);
        buttonBack.setOpaque(false);

        buttonBack.setText("");
        buttonBack.addActionListener(e -> parent.toggleSetting(false, false));
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.insets = new Insets(80, 20, 0, 20);
        gbc.anchor = GridBagConstraints.SOUTH;

        // Button Return
        normalIconReturn = new ImageIcon(returnNormalBtnImg.getScaledInstance(192, 61, Image.SCALE_SMOOTH));
        hoverIconReturn = new ImageIcon(returnHoverBtnImg.getScaledInstance(192, 61, Image.SCALE_SMOOTH));
        buttonReturn = new JButton(normalIconReturn);
        buttonReturn.setRolloverIcon(hoverIconReturn);
        buttonReturn.setBorderPainted(false);
        buttonReturn.setContentAreaFilled(false);
        buttonReturn.setFocusable(false);
        buttonReturn.setOpaque(false);

        buttonReturn.setText("");
        buttonReturn.addActionListener(e -> {
            parent.toggleSetting(false, false);
            parent.setIsStartGame(true);
            parent.returnToMainMenu();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0)); // เว้นระยะห่าง 30
        buttonPanel.setOpaque(false);
        buttonPanel.add(buttonBack);
        buttonPanel.add(buttonReturn);
        add(buttonPanel, gbc);


        blocker = new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { e.consume(); }
            @Override public void mousePressed(MouseEvent e) { e.consume(); }
            @Override public void mouseReleased(MouseEvent e) { e.consume(); }
            @Override public void mouseEntered(MouseEvent e) { e.consume(); }
            @Override public void mouseExited(MouseEvent e) { e.consume(); }
        };
        this.addMouseListener(blocker);
        this.addMouseMotionListener(blocker);
    }

    private JSlider createCustomSlider(int min, int max, int value) {
        slider = new JSlider(min, max, value);
        customUI = new BasicSliderUI(slider) {
            @Override
            public void paintTrack(Graphics g) {
                g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (trackGray != null) {
                    g2d.drawImage(trackGray, trackRect.x-15, trackRect.y, trackRect.width+20, trackRect.height, null);
                }

                int thumbPos = thumbRect.x + (thumbRect.width / 2);

                if (trackRed != null) {
                    Shape oldClip = g2d.getClip();
                    g2d.setClip(trackRect.x, 0, thumbPos - trackRect.x, slider.getHeight());
                    g2d.drawImage(trackRed, trackRect.x-15, trackRect.y, trackRect.width+20, trackRect.height, null);
                    g2d.setClip(oldClip);
                }

                g2d.dispose();
            }

            @Override
            public void paintThumb(Graphics g) {
                g2d = (Graphics2D) g.create();
                int centerX = thumbRect.x + thumbRect.width / 2;
                int centerY = thumbRect.y + thumbRect.height / 2;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (scorllingImage != null) {
                    int imgW = thumbRect.width;
                    int imgH = thumbRect.height;
                    g2d.drawImage(scorllingImage, centerX - imgW / 2, centerY - imgH / 2, imgW, imgH, null);
                }

                g2d.dispose();
            }

            @Override
            protected Dimension getThumbSize() {
                return new Dimension(20, 45);
            }
        };

        slider.setPreferredSize(new Dimension(500, 80));
        slider.setOpaque(false);
        slider.setFocusable(false);
        slider.setUI(customUI);

        return slider;
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(30, 30, 30, 200));
        g2.fillRect(0, 0, getWidth(), getHeight());

        if (bgImage != null) {
            int x = (getWidth() - bgImage.getWidth(null)) / 2;
            int y = (getHeight() - bgImage.getHeight(null)) / 2;
            g2.drawImage(bgImage, x, y, this);
        }
        g2.dispose();
    }

    public void setInGameMode(boolean isInGame) {
        if (isInGame) {
            buttonBack.setVisible(true);
            buttonReturn.setVisible(true);
        } else {
            buttonBack.setVisible(true);
            buttonReturn.setVisible(false);
        }

        this.revalidate();
        this.repaint();
    }
}
