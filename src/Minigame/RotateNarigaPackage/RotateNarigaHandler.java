package Minigame.RotateNarigaPackage;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.Arrays;

public class RotateNarigaHandler extends MouseAdapter {
    private RotateNariga rn;
    private int deg;
    private JPanel panel;
    public RotateNarigaHandler(RotateNariga rn, int deg, JPanel panel){
        this.rn = rn;
        this.deg = deg;
        this.panel = panel;
    }
    @Override
    public void mouseClicked(MouseEvent e){
        int targetAngle[] = {108, 270};
        if (!(rn.getFinishRotateNariga())){
            ((KemImagePanel) panel).rotate(deg);
            int nowAngle [] = (rn.getKemAngle());
            System.out.println(((KemImagePanel) panel).getAngle());
            if (Arrays.equals(targetAngle, nowAngle)) {
                rn.setFinishRotateNariga(true);
                System.out.println("You win");
            }
        }
    }
}
