package Minigame.FinalBossFightPackage;

import javax.swing.*;
import java.awt.*;

public class CircleJPanel extends JPanel {
    public CircleJPanel(int width, int height) {
        setOpaque(false);
        setPreferredSize(new Dimension(width, height + 170));
        setMinimumSize(new Dimension(width, height + 170));
        setMaximumSize(new Dimension(width, height + 170));
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(new Color(177,182,185,255));
        g2.fillOval(0,170,getWidth()-1,getHeight()-171);
        g2.dispose();

    }
}
