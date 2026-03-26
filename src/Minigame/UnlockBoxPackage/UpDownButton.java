package Minigame.UnlockBoxPackage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class UpDownButton extends JButton {
    private BufferedImage img;
    private int opp;
    private SlotJPanel activateSlot;

    public UpDownButton(int opp, SlotJPanel activateSlot) {
        try{
            if(opp == 1){
                setActionCommand("Up");
                img =  ImageIO.read(getClass().getResource("Image/up.png"));
            } else{
                setActionCommand("Down");
                img =  ImageIO.read(getClass().getResource("Image/down.png"));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        this.opp = opp;
        this.activateSlot = activateSlot;
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addActionListener(new ActionHandler(activateSlot));
        setIcon(new ImageIcon(img));
        setHorizontalAlignment(JLabel.CENTER);
        setOpaque(false);
    }
}
