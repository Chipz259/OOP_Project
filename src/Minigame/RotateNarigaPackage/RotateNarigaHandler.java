package Minigame.RotateNarigaPackage;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RotateNarigaHandler extends MouseAdapter {
    private int deg;
    public RotateNarigaHandler(int deg){
        this.deg = deg;
    }
    @Override
    public void mouseClicked(MouseEvent e){
        JPanel panel = (JPanel) e.getSource();
        ((test) panel).rotate(deg);
    }
}
