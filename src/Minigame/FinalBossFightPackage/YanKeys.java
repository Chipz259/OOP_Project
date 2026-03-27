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
        } catch (IOException | FontFormatException ex){
            ex.printStackTrace();
        }

        keyLabel.setSize(300,25);
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
                alpha.setSize(container.getWidth(),container.getHeight());
                keyLabel.setLocation(0,(int) (0.8* container.getHeight()));
                setUnactive();
            }
        });

    }
    public int getKeyButton(){
        return  keyButton;
    }
    public JPanel getContainer(){
        return container;
    }
    public changableImagePanel getAlpha(){
        return alpha;
    }
    public void setActive(){
        alpha.setActive();
        container.setActive();
        keyLabel.setForeground(activeColor);
    }

    public void setUnactive(){
        alpha.setUnactive();
        container.setUnactive();
        keyLabel.setForeground(unactiveColor);
    }
}
