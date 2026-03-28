package Minigame.FinalBossFightPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class YanKeys {
    private int keyButton;
    private changableImagePanel alpha, container;
    private JLabel keyLabel;
    private Color activeColor, unactiveColor;
    private boolean isTarget = false;
    private int textH;
    private int normalH, targetH;
    public YanKeys(int keyButton, String unactive, String active){
        this.keyButton = keyButton;
        activeColor = new Color(239, 154, 7, 255);
        unactiveColor = new Color(245, 245, 245, 255);
        container = new changableImagePanel("Image/Bg_default.png", "Image/Bg_hover.png");
        alpha = new changableImagePanel(unactive, active);
        keyLabel = new JLabel(KeyEvent.getKeyText(keyButton));
        try{
            InputStream is = getClass().getResourceAsStream("/res/Font/MN Tom Saep.ttf");
            Font PimdeedIIIFont = Font.createFont(Font.TRUETYPE_FONT, is);
            Font sizeFont =  PimdeedIIIFont.deriveFont(Font.BOLD,32f);
            keyLabel.setFont(sizeFont);
            FontMetrics fontMetrics = keyLabel.getFontMetrics(sizeFont);
            textH = fontMetrics.getAscent();
        } catch (IOException | FontFormatException ex){
            ex.printStackTrace();
        }

        keyLabel.setHorizontalAlignment(JLabel.CENTER);
        alpha.setLocation(0,0);
        container.setLayout(null);
        container.setOpaque(false);
        alpha.setOpaque(false);

        container.add(alpha); container.add(keyLabel);
        container.setComponentZOrder(alpha, 0);
        container.setComponentZOrder(keyLabel, 1);
        container.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e){
                normalH = (int)(0.71 * container.getHeight());
                targetH = (int)(0.78 * container.getHeight());
                alpha.setSize(container.getWidth(),container.getHeight());
                keyLabel.setSize(container.getWidth(), textH);
                if(!isTarget){
                    keyLabel.setLocation(0, normalH);
                } else {
                    keyLabel.setLocation(0, targetH);
                }
            }
        });
    }
    public int getKeyButton(){
        return  keyButton;
    }
    public JPanel getContainer(){
        return container;
    }
    public void setActive(){
        isTarget = false;
        keyLabel.setLocation(0, normalH);
        alpha.setActive();
        container.setActive();
        keyLabel.setForeground(activeColor);
        alpha.setTarget(false);
        container.setTarget(false);
    }

    public void setUnactive(){
        isTarget = false;
        keyLabel.setLocation(0, normalH);
        alpha.setUnactive();
        alpha.setTarget(false);
        container.setUnactive();
        container.setTarget(false);
        keyLabel.setForeground(unactiveColor);
    }
    public void setAsTarget(){
        isTarget = true;
        keyLabel.setLocation(0, targetH);
        container.setTarget(true);
        alpha.setTarget(true);
        container.revalidate();
    }
}
