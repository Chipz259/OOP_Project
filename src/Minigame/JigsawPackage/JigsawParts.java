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
        size = this.getPreferredSize();
        this.targetX = targetX;
        this.targetY = targetY;
        this.setOpaque(false);
        this.addMouseListener(jsHandler);
        this.addMouseMotionListener(jsHandler);
        this.setBounds(x, y, size.width, size.height);
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
    int x = this.getX();
    int y = this.getY();
    int h = this.getHeight();
    int w = this.getHeight();
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, x + w/2, y + h/2, this);
    }
    @Override
    public Dimension getPreferredSize() {
        if (img != null) {
            return new Dimension(img.getWidth(this), img.getHeight(this));
        }
        return super.getPreferredSize();
    }

}
