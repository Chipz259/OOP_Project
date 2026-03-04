package Minigame.FinalBossFightPackage;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class hoverHandler extends MouseAdapter {
    private Color active = new Color(71, 248, 248, 255), unactive = new Color(67, 69, 69, 255);
    @Override
    public void mouseEntered(MouseEvent e) {
        changableImagePanel panel = (changableImagePanel) e.getSource();
        panel.setTint(active);
    }
    @Override
    public void mouseExited(MouseEvent e){
        changableImagePanel panel = (changableImagePanel) e.getSource();
        panel.setTint(unactive);
    }

}
