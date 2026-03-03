package Minigame.RotateYanPackage;

import javax.sound.sampled.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

public class RotateHandler implements MouseListener {
    RotateYan rotateYan;
    Clip yanFlipSound;
    public RotateHandler(RotateYan rotateYan, Clip yanFlipSound){
        this.rotateYan = rotateYan;
        this.yanFlipSound = yanFlipSound;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        this.playYanFlipSound();
        ImagePanel p = (ImagePanel) e.getSource();
        p.rotateImage();

        int targetAngle[] = {180, 0, 270, 270, 0, 0, 180, 0, 180, 0, 180, 270};

        int nowAngle[] = rotateYan.getPanelAngle();
        if (Arrays.equals(targetAngle, nowAngle)){
            rotateYan.winAnimated();
            rotateYan.getSuccess().setVisible(true);
        } else{
            rotateYan.getSuccess().setVisible(false);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    public void playYanFlipSound(){
        if (yanFlipSound.isRunning()){
            yanFlipSound.stop();
        }

        yanFlipSound.setFramePosition(0);
        yanFlipSound.start();
    }

}
