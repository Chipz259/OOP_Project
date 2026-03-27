package Minigame.JigsawPackage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class JigsawParts extends JPanel{
    private int targetX;
    private int targetY;
    private BufferedImage img;
    private Dimension size;
    private Boolean reachTartget = false;

    public JigsawParts(String path){
        this(path, 0, 0, 0, 0);
    }

    public JigsawParts(String path, int x, int y, int targetX, int targetY){
        JigsawPartHandler jsHandler = new JigsawPartHandler();
        try {
            img = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.setOpaque(false);
        this.addMouseListener(jsHandler);
        this.addMouseMotionListener(jsHandler);

        this.targetX = targetX;
        this.targetY = targetY;

        this.setBounds(x, y, img.getWidth(), img.getHeight());
    }
    public BufferedImage getImg(){
        return  img;
    }
    public void setReachTartget(Boolean b){
        reachTartget = b;
    }
    public int getTargetX(){
        return targetX;
    }
    public int getTargetY(){
        return targetY;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);
    }
    @Override
    public Dimension getPreferredSize() {
        if (img != null) {
            return new Dimension(img.getWidth(), img.getHeight());
        }
        return super.getPreferredSize();
    }

}
