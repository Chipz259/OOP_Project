package Minigame.RotateYanPackage;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {
    private Image img;
    private int angle = 0;

    public ImagePanel(String path){
        img = new ImageIcon(getClass().getResource(path)).getImage();
    }
    public int getAngle(){
        return angle;
    }
    public void rotateImage() {
        angle = (angle + 90) % 360;
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        int w = getWidth();
        int h = getHeight();

        double diag = Math.sqrt(w*w + h*h);

        g2.translate(w/2.0, h/2.0);
        g2.rotate(Math.toRadians(angle));

        g2.drawImage(img,
                (int)(-diag/2),
                (int)(-diag/2),
                (int)diag,
                (int)diag,
                this);
    }

}
