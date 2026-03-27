package Minigame.RotateYanPackage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class YanJPanel extends JPanel {
    private BufferedImage img;
    private int angle = 0;

    public YanJPanel(String path, RotateYan frame){
        try {
            img = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.setOpaque(false);
        this.addMouseListener(new RotateHandler(frame));
    }
    public int getAngle(){
        return angle;
    }
    public void rotateImage() {
        angle = (angle + 90) % 360;
        revalidate();
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        double cx = getWidth() / 2.0;
        double cy = getHeight() / 2.0;

        g2.rotate(Math.toRadians(angle), cx, cy);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.drawImage(img, 0, 0, getWidth(), getHeight(), null);

        g2.dispose();
    }

}
