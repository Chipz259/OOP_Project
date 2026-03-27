package Minigame.JigsawPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class JigsawPartHandler extends MouseAdapter {
    private Point offset;
    @Override
    public void mouseDragged(MouseEvent e) {
        JPanel panel = (JPanel) e.getSource();
        BufferedImage img = ((JigsawParts) panel).getImg();
        int dX = Math.abs(panel.getX() - ((JigsawParts) panel).getTargetX());
        int dY = Math.abs(panel.getY() - ((JigsawParts) panel).getTargetY());
        int distance = (int) Math.sqrt(dX * dX + dY * dY);
        if ( distance > 10){
            int pixel = img.getRGB(offset.x, offset.y);
            int alpha = (pixel >> 24) & 0xff;
            if (alpha > 0){
                int newX = panel.getX() + e.getX() - offset.x;
                int newY = panel.getY() + e.getY() - offset.y;
                Container parent = panel.getParent();
                if (parent != null) {
                    int maxX = parent.getWidth() - panel.getWidth();
                    int maxY = parent.getHeight() - panel.getHeight();
                    newX = Math.max(0, Math.min(newX, maxX));
                    newY = Math.max(0, Math.min(newY, maxY));
                }
                panel.setLocation(newX, newY );
                panel.repaint();
                ((JigsawParts) panel).setReachTartget(true);
            }
        } else {
            panel.setLocation(((JigsawParts) panel).getTargetX(), ((JigsawParts) panel).getTargetY());
            panel.repaint();
            System.out.println("this part is already on the right location");
        }

    }
    @Override
    public void mousePressed(MouseEvent e) {
        offset = e.getPoint();
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        JigsawParts jp = (JigsawParts) e.getSource();
        System.out.println(jp.getX());
        System.out.println(jp.getY());
        System.out.println("--------------------");
    }

}
