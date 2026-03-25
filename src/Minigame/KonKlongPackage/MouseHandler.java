package Minigame.KonKlongPackage;

import Minigame.JigsawPackage.JigsawParts;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;

public class MouseHandler extends MouseAdapter {
    private Point offset;
    @Override
    public void mouseClicked(MouseEvent e){
        ImagePanel konKlongPanel = (ImagePanel) e.getSource();
        konKlongPanel.rotateImage();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        offset = e.getPoint();
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        ImagePanel konKlongPanel = (ImagePanel) e.getSource();
        BufferedImage img = konKlongPanel.getImage();
        img = konKlongPanel.getImage();
        int pixel = img.getRGB(offset.x, offset.y);
        int alpha = (pixel >> 24) & 0xff;
        if (alpha > 0){
            int newX = konKlongPanel.getX() + e.getX() - offset.x;
            int newY = konKlongPanel.getY() + e.getY() - offset.y;
            Container parent = konKlongPanel.getParent();
            if (parent != null) {
                int maxX = parent.getWidth() - konKlongPanel.getWidth();
                int maxY = parent.getHeight() - konKlongPanel.getHeight();
                newX = Math.max(0, Math.min(newX, maxX));
                newY = Math.max(0, Math.min(newY, maxY));
            }
            konKlongPanel.setLocation(newX, newY );
            konKlongPanel.getParent().repaint();
        }
    }
}
