package Minigame.KonKlongPackage;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class KonKlong {
    private JFrame mainFrame;
    private JPanel mainPanel, konKlongPanel, DangPanel, backgroudnPanel;
    private BufferedImage background;

    public KonKlong(){
        try{
            background = ImageIO.read(getClass().getResource("Image/BG.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainFrame = new JFrame();
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
        backgroudnPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(false);

        backgroudnPanel.setBorder(BorderFactory.createEmptyBorder(40,240,40,240));
//        mainFrame.setUndecorated(true);
        mainFrame.setResizable(false);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(mainFrame);

        mainPanel.add(konKlongPanel); mainPanel.add(DangPanel);
        mainPanel.setComponentZOrder(konKlongPanel, 1);
        mainPanel.setComponentZOrder(DangPanel, 0);
        backgroudnPanel.add(mainPanel, BorderLayout.CENTER);
        mainFrame.add(backgroudnPanel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

        SwingUtilities.invokeLater(() ->{
            konKlongPanel.setLocation((mainPanel.getWidth() - konKlongPanel.getWidth()) / 2, (mainPanel.getHeight() - konKlongPanel.getHeight()) / 2);
            mainPanel.repaint();
            mainFrame.revalidate();
        });
    }
}
