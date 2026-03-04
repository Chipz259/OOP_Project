package Minigame.RotateNarigaPackage;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class RotateNarigaHandler extends MouseAdapter {
    private int deg;
    private JPanel panel;
    public RotateNarigaHandler(int deg, JPanel panel){
        this.deg = deg;
        this.panel = panel;
    }
    @Override
    public void mouseClicked(MouseEvent e){
        ((KemImagePanel) panel).rotate(deg);

    }
}
