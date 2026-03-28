package Minigame.RotateYanPackage;

import system.AudioManager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class RotateHandler extends MouseAdapter {
    RotateYan rotateYan;
    public RotateHandler(RotateYan rotateYan){
        this.rotateYan = rotateYan;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (!(rotateYan.getSuccess())){
            this.playYanFlipSound();
            YanJPanel p = (YanJPanel) e.getSource();
            p.rotateImage();
            rotateYan.repaint();
            int targetAngle[] = {180, 270, 180, 0, 0, 180, 180, 270, 180, 180, 90, 180};

            int nowAngle[] = rotateYan.getPanelAngle();
            if (Arrays.equals(targetAngle, nowAngle)) {
                rotateYan.winAnimated();
                rotateYan.setSuccess(true);
                System.out.println("เย้ๆ ฉลาดจังเลย");
            }
        }
    }
    public void playYanFlipSound(){
        AudioManager.playPreloadedSFX("src/res/sound/yanFlip.wav", 10.0f);
    }

}
