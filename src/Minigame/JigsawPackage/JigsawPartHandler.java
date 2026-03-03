package Minigame.JigsawPackage;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class JigsawPartHandler extends MouseAdapter {
    private Point offset;
    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println(offset);
        JPanel panel = (JPanel) e.getSource();
        BufferedImage img = ((JigsawParts) panel).getImg();
        int dX = Math.abs(panel.getX() - ((JigsawParts) panel).getTargetX());
        int dY = Math.abs(panel.getY() - ((JigsawParts) panel).getTargetY());
        int distance = (int) Math.sqrt(dX * dX + dY * dY);
        System.out.println("---------------");
        System.out.println(e.getSource());
        System.out.println(String.format("dis X: %d", dX));
        System.out.println(String.format("dis Y: %d", dY));
        if ( distance > 10){
            int pixel = img.getRGB(offset.x, offset.y);
            int alpha = (pixel >> 24) & 0xff;
            if (alpha > 0){
                int newX = panel.getX() + e.getX() - offset.x;
                int newY = panel.getY() + e.getY() - offset.y;
                panel.setLocation(newX, newY );
                panel.getParent().repaint();
                ((JigsawParts) panel).setReachTartget(true);
            }
        } else {
            panel.setLocation(((JigsawParts) panel).getTargetX(), ((JigsawParts) panel).getTargetY());
            panel.getParent().repaint();
            System.out.println("this part is already on the right location");
        }

    }

    public void mousePressed(MouseEvent e) {
        offset = e.getPoint();
        System.out.println(offset);
    }

    public void mouseReleased(MouseEvent e){
        JPanel panel = (JPanel) e.getSource();
        System.out.println(panel.getX());
        System.out.println(panel.getY());
    }


}
