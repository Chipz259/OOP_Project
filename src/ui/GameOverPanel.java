package ui;

import system.GameOverHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameOverPanel extends JPanel {
    private MainGameFrame parent;
    private BufferedImage bgNormal, bgLight, currentBG;
    private ImageIcon retryNormal, retryHover;
    private JButton btnRetry;

    public GameOverPanel(MainGameFrame parent) {
        btnRetry = new JButton();
        this.parent = parent;
        this.setLayout(null);
        this.setDoubleBuffered(true);

        try {
            bgNormal = ImageIO.read(getClass().getResourceAsStream("/res/GameOverNormal.png"));
            bgLight = ImageIO.read(getClass().getResourceAsStream("/res/GameOverLight.png"));
            currentBG = bgNormal;
            retryNormal = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/res/GameOverBtnTryagainNormal.png")));
            retryHover = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/res/GameOverBtnTryagainHover.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        btnRetry.setIcon(retryNormal);
        btnRetry.setRolloverIcon(retryHover);

        btnRetry.setBounds(860, 590, retryNormal.getIconWidth(), retryNormal.getIconHeight());
        btnRetry.setBorderPainted(false);
        btnRetry.setContentAreaFilled(false);
        btnRetry.setFocusPainted(false);
        btnRetry.setFocusable(false); // ป้องกันการแย่ง Focus
        btnRetry.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(btnRetry);

        GameOverHandler handler = new GameOverHandler(this);
        btnRetry.addActionListener(handler);
        btnRetry.addMouseListener(handler);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (currentBG != null) {
            // วาดพื้นหลังปัจจุบันให้เต็มจอ (1920x1080)
            g.drawImage(currentBG, 0, 0, getWidth(), getHeight(), null);
        }
    }

    public void setCurrentBG(BufferedImage bg) {
        currentBG = bg;
    }

    public BufferedImage getBgNormal() {
        return bgNormal;
    }

    public BufferedImage getBgLight() {
        return bgLight;
    }

    public MainGameFrame getParentFrame() {
        return parent;
    }
}
