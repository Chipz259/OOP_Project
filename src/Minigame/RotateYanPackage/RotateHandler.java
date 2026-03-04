package Minigame.RotateYanPackage;

import system.AudioManager;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

public class RotateHandler extends MouseAdapter {
    RotateYan rotateYan;
    Clip yanFlipSound;
    public RotateHandler(RotateYan rotateYan, Clip yanFlipSound){
        this.rotateYan = rotateYan;
        this.yanFlipSound = yanFlipSound;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (!(rotateYan.getSuccess())){
            this.playYanFlipSound();
            ImagePanel p = (ImagePanel) e.getSource();
            p.rotateImage();
            rotateYan.getMainFrame().repaint();
            int targetAngle[] = {180, 180, 270, 0, 0, 180, 180, 270, 180, 180, 90, 180};

            int nowAngle[] = rotateYan.getPanelAngle();
            if (Arrays.equals(targetAngle, nowAngle)) {
                rotateYan.winAnimated();
                rotateYan.setSuccess(true);
            }
        }
    }
    public void playYanFlipSound(){
        if (AudioManager.isSfxRunning()){
            AudioManager.stopMusic();
        }

        AudioManager.playSFX("src/res/sound/yanFlip.wav", 10.0f);
    }

}
