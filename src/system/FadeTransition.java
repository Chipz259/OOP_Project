package system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FadeTransition extends JPanel {
    private float fadeAlpha = 0f;
    private boolean isFading = false;
    private Timer fadeTimer;

    public FadeTransition(){
        this.setOpaque(false);
        this.setVisible(false);
    }

    //หน่วยเป็นมิลลิวินาที 1000 ms = 1 วินาที
    public void executeFade(int fadeOut, int wait, int fadeIn, Runnable actionAfterFadeOut){
        if (isFading) return;

        isFading = true;
        this.setVisible(true);

        int fpsDelay = 16;

        float alphaOut;
        if (fadeOut > 0) {
            alphaOut = (float) fpsDelay / fadeOut;
        }
        else {
            //ถ้าอยากให้พริบตาเดียวในการเปลี่ยน กรอก 0 หรือ ติดลบ
            alphaOut = 1.0f;
        }

        float alphaIn;
        if (fadeIn > 0) {
            alphaIn = (float) fpsDelay / fadeIn;
        }
        else {
            //ถ้าอยากให้พริบตาเดียวในการเปลี่ยน กรอก 0 หรือ ติดลบ
            alphaIn = 1.0f;
        }

        int waitTicks;
        if (wait > 0) {
            waitTicks = wait / fpsDelay;
        }
        else {
            waitTicks = 0;
        }

        fadeTimer = new Timer(fpsDelay, new ActionListener() {
            int state = 0; // 0 : fade Out, 1 = Wait, 2 = Fade In
            int currentWait = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                // fade Out
                if (state == 0) {
                    fadeAlpha += alphaOut;
                    if (fadeAlpha >= 1f){
                        fadeAlpha = 1f;
                        state = 1; //เปลี่ยนสถานะ

                        if (actionAfterFadeOut != null) {
                            actionAfterFadeOut.run();
                        }
                    }
                }
                //wait
                else if (state == 1) {
                    currentWait++;
                    if (currentWait >= wait){
                        state = 2; //เปลี่ยนสถานะ
                    }
                }
                //Fade In
                else if (state == 2) {
                    fadeAlpha -= alphaIn;
                    if (fadeAlpha <= 0f) {
                        fadeAlpha = 0f;
                        isFading = false;
                        setVisible(false);
                        fadeTimer.stop();
                    }
                }

                repaint();
            }
        });
        fadeTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        float safeAlpha = Math.max(0f, Math.min(1f, fadeAlpha));
        g2d.setColor(new Color(0f, 0f, 0f, safeAlpha));
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    public boolean isFading(){
        return isFading;
    }
}
