package Minigame.RotateNarigaPackage;

import system.AudioManager;

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
        int targetAngle[] = {270, 108};
        AudioManager.playSFX("src/res/sound/MinigameRotateClock.wav", 0.0f);
        if (!(rn.isFinished())){
            ((KemImagePanel) panel).rotate(deg);
            int nowAngle [] = (rn.getKemAngle());
            if (Arrays.equals(targetAngle, nowAngle)) {
                AudioManager.playSFX("src/res/sound/MinigameRotateClockFinish.wav", 0.0f);
                rn.winClose();
                System.out.println("You win");
            }
        }
    }
}
