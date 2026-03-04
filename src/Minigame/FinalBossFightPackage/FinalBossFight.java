package Minigame.FinalBossFightPackage;

import javax.swing.*;
import java.awt.*;

public class FinalBossFight {
    private JFrame mainFrame;
    private JPanel panel;

    public FinalBossFight(){
        mainFrame = new JFrame();
        panel = new changableImagePanel("Image/Alpha1.png");

        panel.setBounds(20, 20, 200, 200);
        panel.addMouseListener(new hoverHandler());
        mainFrame.setLayout(null);

        ((JComponent)mainFrame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(40, 240, 40, 20));
        mainFrame.setResizable(false);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(mainFrame);
        mainFrame.add(panel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }
}
