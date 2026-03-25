package Minigame.KonKlongPackage;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class KonKlong {
    private JFrame mainFrame;
    private JPanel mainPanel, konKlongPanel, DangPanel;

    public KonKlong(){
        mainFrame = new JFrame();
        mainPanel = new JPanel();
        konKlongPanel = new ImagePanel("Image/KonKlong.png");
        DangPanel = new ImagePanel("Image/Dang.png");
        MouseHandler mouseHandler = new MouseHandler();
        DangPanel.addMouseListener(mouseHandler);
        DangPanel.addMouseMotionListener(mouseHandler);

        konKlongPanel.setLocation(0, 0);
        DangPanel.setLocation(200, 200);

        mainPanel.setLayout(null);
        mainPanel.setOpaque(false);

        ((JComponent) mainFrame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(40,240,40,240));
//        mainFrame.setUndecorated(true);
        mainFrame.setResizable(false);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(mainFrame);

        mainPanel.add(konKlongPanel); mainPanel.add(DangPanel);
        mainPanel.setComponentZOrder(konKlongPanel, 1);
        mainPanel.setComponentZOrder(DangPanel, 0);
        mainFrame.add(mainPanel, BorderLayout.CENTER);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

        SwingUtilities.invokeLater(() ->{
            konKlongPanel.setLocation((mainPanel.getWidth() - konKlongPanel.getWidth()) / 2, (mainPanel.getHeight() - konKlongPanel.getHeight()) / 2);
            mainPanel.repaint();
            mainFrame.revalidate();
        });
    }
}
