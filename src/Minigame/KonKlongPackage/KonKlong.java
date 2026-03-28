package Minigame.KonKlongPackage;

import ui.MainGameFrame;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class KonKlong extends JPanel {
    private JPanel konKlongPanel, DangPanel;
    private BufferedImage background, exit;
    private MainGameFrame mainGameFrame;
    private JButton closeBtn;

    public KonKlong(MainGameFrame mainGameFrame){
        this.mainGameFrame = mainGameFrame;
        this.setLayout(null);

        try{
            background = ImageIO.read(getClass().getResource("Image/BG.png"));
            exit = ImageIO.read(getClass().getResource("Image/Exit_minigame_btn.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        konKlongPanel = new ImagePanel("Image/KonKlong.png");
        DangPanel = new ImagePanel("Image/Dang.png");


        MouseHandler mouseHandler = new MouseHandler();
        DangPanel.addMouseListener(mouseHandler);
        DangPanel.addMouseMotionListener(mouseHandler);

        konKlongPanel.setLocation(0, 0);
        DangPanel.setLocation(200, 200);

        closeBtn = new JButton(new ImageIcon(exit));
        closeBtn.setSize(exit.getWidth(), exit.getHeight());
        closeBtn.setBorderPainted(false);
        closeBtn.setContentAreaFilled(false);
        closeBtn.setFocusPainted(false);
        closeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeBtn.addActionListener(e -> {
            mainGameFrame.closeMinigame();
        });

        this.addComponentListener(new ComponentAdapter() {
            private boolean isCenter = false;

            @Override
            public void componentResized(ComponentEvent e) {
            }
        });
    }
}
