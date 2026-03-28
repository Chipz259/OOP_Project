package ui;

import system.TutorialHandler;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Tutorial extends JPanel {
    private MainGameFrame parent;
    private BufferedImage[] tutorialStartImage, tutorialCombineImage;
    private int currentImage = 0;
    private JButton btnLeft, btnRight, btnClose;
    private ImageIcon iconLeft, iconRight, iconCloseNormal, iconCloseHover;
    private Boolean isActive = false;
    private String currentSceen;

    public Tutorial(MainGameFrame parent) {
        this.parent = parent;
        tutorialStartImage = new BufferedImage[4];
        tutorialCombineImage = new BufferedImage[2];
        try {
            tutorialStartImage[0] = ImageIO.read(getClass().getResource("/res/Tutorial1.png"));
            tutorialStartImage[1] = ImageIO.read(getClass().getResource("/res/Tutorial2.png"));
            tutorialStartImage[2] = ImageIO.read(getClass().getResource("/res/Tutorial3.png"));
            tutorialStartImage[3] = ImageIO.read(getClass().getResource("/res/Tutorial4.png"));

            tutorialCombineImage[0] = ImageIO.read(getClass().getResource("/res/Tutorial5.png"));
            tutorialCombineImage[1] = ImageIO.read(getClass().getResource("/res/Tutorial6.png"));

            iconLeft = new ImageIcon(new ImageIcon("src/res/TutorialBtnLeft.png").getImage().getScaledInstance(110, 74, Image.SCALE_SMOOTH));
            iconRight = new ImageIcon(new ImageIcon("src/res/TutorialBtnRight.png").getImage().getScaledInstance(110, 74, Image.SCALE_SMOOTH));
            iconCloseNormal = new ImageIcon(new ImageIcon("src/res/TutorialBtnOK1.png").getImage().getScaledInstance(190, 72, Image.SCALE_SMOOTH));
            iconCloseHover = new ImageIcon(new ImageIcon("src/res/TutorialBtnOK2.png").getImage().getScaledInstance(190, 72, Image.SCALE_SMOOTH));
        }
        catch (IOException e) {
            System.out.println("ระบบ : โหลดรูปไม่ได้จ้า");
            e.printStackTrace();
        }

        // Button Left
        btnLeft = new JButton(iconLeft);
        setupButtonStyle(btnLeft);
        btnLeft.setBounds(400, 490, 110, 74);
        btnLeft.addActionListener(e -> prevPage(currentSceen));
        this.add(btnLeft);

        // Button Right
        btnRight = new JButton(iconRight);
        setupButtonStyle(btnRight);
        btnRight.setBounds(1410, 490, 110, 74);
        btnRight.addActionListener(e -> nextPage(currentSceen));
        this.add(btnRight);

        // Button Close
        btnClose = new JButton(iconCloseNormal);
        btnClose.setRolloverIcon(iconCloseHover);
        setupButtonStyle(btnClose);
        btnClose.setBounds(860, 880, 190, 72);
        btnClose.addActionListener(e -> closeTutorial());
        btnClose.setVisible(false);
        this.add(btnClose);

        TutorialHandler handler = new TutorialHandler(this);
        this.addMouseListener(handler);

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

    private void nextPage(String scene) {
        if (scene.equals("StartGame")) {
            if (currentImage < tutorialStartImage.length - 1) {
                currentImage++;
                updateButtonStates(scene);
                repaint();
            }
        }
        else if (scene.equals("Combine")) {
            if (currentImage < tutorialCombineImage.length - 1) {
                currentImage++;
                updateButtonStates(scene);
                repaint();
            }
        }
    }

    private void prevPage(String sceen) {
        if (sceen.equals("StartGame")) {
            if (currentImage > 0) {
                currentImage--;
                updateButtonStates(sceen);
                repaint();
            }
        }
        else if (sceen.equals("Combine")) {
            if (currentImage > 0) {
                currentImage--;
                updateButtonStates(sceen);
                repaint();
            }
        }

    }

    private void updateButtonStates(String sceen) {
        if (sceen.equals("StartGame")) {
            btnLeft.setVisible(currentImage > 0);
            btnRight.setVisible(currentImage < tutorialStartImage.length - 1);
            btnClose.setVisible(currentImage == tutorialStartImage.length - 1);
        }
        else if (sceen.equals("Combine")) {
            btnLeft.setVisible(currentImage > 0);
            btnRight.setVisible(currentImage < tutorialCombineImage.length - 1);
            btnClose.setVisible(currentImage == tutorialCombineImage.length - 1);
        }

    }

    public void showTutorial(String sceen) {
        currentSceen = sceen;
        this.currentImage = 0;
        this.isActive = true;
        updateButtonStates(currentSceen);
        this.setVisible(true);
        this.requestFocusInWindow();
        repaint();
    }

    private void closeTutorial() {
        this.isActive = false;
        this.setVisible(false);
        parent.getGamePanel().requestFocusInWindow();
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(new Color(30, 30, 30, 200));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        if (currentSceen.equals("StartGame")) {
            if (tutorialStartImage != null && tutorialStartImage[currentImage] != null) {
                BufferedImage img = tutorialStartImage[currentImage];

                // ขนาด 80%
                int newW = (int) (getWidth() * 0.8);
                int newH = (int) (newW * ((double) img.getHeight() / img.getWidth()));

                if (newH > getHeight() * 0.8) {
                    newH = (int) (getHeight() * 0.8);
                    newW = (int) (newH * ((double) img.getWidth() / img.getHeight()));
                }

                int x = (getWidth() - newW) / 2;
                int y = (getHeight() - newH) / 2;

                g2d.drawImage(img, x, y, newW, newH, null);
            }
        }
        else if (currentSceen.equals("Combine")) {
            if (tutorialCombineImage != null && tutorialCombineImage[currentImage] != null) {
                BufferedImage img = tutorialCombineImage[currentImage];

                // ขนาด 80%
                int newW = (int) (getWidth() * 0.8);
                int newH = (int) (newW * ((double) img.getHeight() / img.getWidth()));

                if (newH > getHeight() * 0.8) {
                    newH = (int) (getHeight() * 0.8);
                    newW = (int) (newH * ((double) img.getWidth() / img.getHeight()));
                }

                int x = (getWidth() - newW) / 2;
                int y = (getHeight() - newH) / 2;

                g2d.drawImage(img, x, y, newW, newH, null);
            }
        }
    }
}
