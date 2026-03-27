package Minigame.KonKlongPackage;

import Minigame.JigsawPackage.Main;
import ui.MainGameFrame;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class KonKlong extends JPanel {
    private JPanel mainPanel, konKlongPanel, DangPanel, backgroudnPanel, topPanel;
    private BufferedImage background;
    private MainGameFrame mainGameFrame;
    private JButton closeBtn;

    public KonKlong(MainGameFrame mainGameFrame){
        this.mainGameFrame = mainGameFrame;
        this.setLayout(new BorderLayout());

        try{
            background = ImageIO.read(getClass().getResource("Image/BG.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainPanel = new JPanel();
        konKlongPanel = new ImagePanel("Image/KonKlong.png");
        DangPanel = new ImagePanel("Image/Dang.png");
        backgroudnPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };


        MouseHandler mouseHandler = new MouseHandler();
        DangPanel.addMouseListener(mouseHandler);
        DangPanel.addMouseMotionListener(mouseHandler);

        konKlongPanel.setLocation(0, 0);
        DangPanel.setLocation(200, 200);

        mainPanel.setLayout(null);
        mainPanel.setOpaque(false);

        backgroudnPanel.setLayout(new BorderLayout());
        backgroudnPanel.setBorder(BorderFactory.createEmptyBorder(40,240,40,240));
//        mainFrame.setUndecorated(true);

        closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> {
            mainGameFrame.closeMinigame();
        });

        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false);
        topPanel.add(closeBtn);

//        this.setResizable(false);
//        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
//        gd.setFullScreenWindow(this);

        mainPanel.add(konKlongPanel); mainPanel.add(DangPanel);
        mainPanel.setComponentZOrder(konKlongPanel, 1);
        mainPanel.setComponentZOrder(DangPanel, 0);

        backgroudnPanel.add(topPanel, BorderLayout.NORTH);
        backgroudnPanel.add(mainPanel, BorderLayout.CENTER);
        this.add(backgroudnPanel, BorderLayout.CENTER);

//        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        mainFrame.setVisible(true);

//        SwingUtilities.invokeLater(() ->{
//            konKlongPanel.setLocation((mainPanel.getWidth() - konKlongPanel.getWidth()) / 2, (mainPanel.getHeight() - konKlongPanel.getHeight()) / 2);
//            mainPanel.repaint();
//            this.revalidate();
//        });

        //ระบบจะสลับหน้าจอ -> ขยาย mainPanel จนเต็ม -> พอ mainPanel ขยายเสร็จ -> จะไปสั่ง ComponentAdapter ว่าเอาขนาดไปคำนวณกึ่งกลางได้เลย
        mainPanel.addComponentListener(new ComponentAdapter() {
            private boolean isCenter = false;

            @Override
            public void componentResized(ComponentEvent e) {
                if (!isCenter && mainPanel.getWidth() > 0 && mainPanel.getHeight() > 0) {

                    konKlongPanel.setLocation((mainPanel.getWidth() - konKlongPanel.getWidth()) / 2, (mainPanel.getHeight() - konKlongPanel.getHeight()) / 2);
                    DangPanel.setLocation((mainPanel.getWidth() - DangPanel.getWidth()) / 2 + 150, (mainPanel.getHeight() - DangPanel.getHeight()) / 2 + 150);
                    isCenter = true;

                }
            }
        });
    }
}
