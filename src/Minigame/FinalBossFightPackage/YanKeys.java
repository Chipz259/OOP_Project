package Minigame.FinalBossFightPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class YanKeys {
    private int keyButton;
    private changableImagePanel img;

    public YanKeys(int keyButton, changableImagePanel img) {}
    private JPanel container, keyPanel;
    private JLabel keyLabel;
    public YanKeys(int keyButton, String path){
        this.keyButton = keyButton;
        img = new changableImagePanel(path);

        container = new JPanel();
        keyPanel = new CircleJPanel(55,40);
        keyPanel.setLayout(new BorderLayout());
        keyPanel.setBorder(BorderFactory.createEmptyBorder(170,17,0,0));
        keyLabel = new JLabel(KeyEvent.getKeyText(keyButton));
        try{
            InputStream is = getClass().getResourceAsStream("/res/Font/MN Tom Saep.ttf");
            Font PimdeedIIIFont = Font.createFont(Font.TRUETYPE_FONT, is);
            Font sizeFont =  PimdeedIIIFont.deriveFont(Font.BOLD,32f);
            keyLabel.setFont(sizeFont);
        } catch (IOException | FontFormatException ex){
            ex.printStackTrace();
        }
        setAsUnactive();
        keyPanel.add(keyLabel, BorderLayout.CENTER);
        container.setLayout(new OverlayLayout(container));
        container.setOpaque(false);

        img.setAlignmentX(0.5f);
        img.setAlignmentY(0.5f);

        keyPanel.setAlignmentX(0.5f);
        keyPanel.setAlignmentY(0.5f);

        container.add(keyPanel);
        container.add(img);
    }

    public int getKeyButton(){
        return  keyButton;
    }
    public JPanel getImg(){
        return container;
    }
    public void setAsUnactive(){
        keyLabel.setForeground(new Color(67, 69, 69,255));
        img.setUnactive();
    }
    public void setAsActive(){
        keyLabel.setForeground(new Color(30, 30, 46, 255));
        img.setActive();
    }

}
