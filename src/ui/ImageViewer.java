package ui;

import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ImageViewer extends JPanel{
    private BufferedImage image, exitIcon;
    private JButton exitBtn;

    public ImageViewer(MainGameFrame mainGameFrame, String imagePath) {

        try {
            image = ImageIO.read(getClass().getResource(imagePath));
            exitIcon = ImageIO.read(getClass().getResource("/res/Exit.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setLayout(null);
        this.setOpaque(false);

        exitBtn = new JButton(new ImageIcon(exitIcon));
        exitBtn.setSize(exitIcon.getWidth(), exitIcon.getHeight());
        exitBtn.setPressedIcon(new ImageIcon(exitIcon));
        exitBtn.setBorderPainted(false);
        exitBtn.setContentAreaFilled(false);
        exitBtn.setRolloverEnabled(false);
        exitBtn.setFocusPainted(false);
        exitBtn.setOpaque(false);
        exitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitBtn.addActionListener(e -> {
            mainGameFrame.closeMinigame();
        });

        this.add(exitBtn);

        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                exitBtn.setLocation((int) (0.05 * getWidth()), (int) (0.05 * getHeight()));
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0, 0, 0, 255));
        g.fillRect(0, 0, getWidth(), getHeight());
        int imgW = image.getWidth();
        int imgH = image.getHeight();
        int x = (getWidth() - imgW) / 2;
        int y = (getHeight() - imgH) / 2;
        g.drawImage(image, x, y, imgW, imgH, this);
    }
}
