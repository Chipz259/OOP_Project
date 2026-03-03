package system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FadeTransition extends JPanel {
    private float fadeAlpha = 0;
    private boolean isFading = false;
    private  Timer fadeTimer;

    public FadeTransition(){
        this.setOpaque(false);
        this.setVisible(false);
    }

    public void executeFade(Runnable actionAfterFadeOut){
        if (isFading) return;;

        isFading = true;
        this.setVisible(true);

        fadeTimer = new Timer(30, new ActionListener() {
            boolean isFadingOut = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (isFadingOut) {
                    fadeAlpha += 0.05f;
                    if (fadeAlpha >= 1){
                        fadeAlpha = 1;
                        isFadingOut = false;

                        if (actionAfterFadeOut != null) {
                            actionAfterFadeOut.run();
                        }
                    }
                }
                else {
                    fadeAlpha -= 0.05f;
                    if (fadeAlpha <= 0) {
                        fadeAlpha = 0;
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

        float safeAlpha = Math.max(0, Math.min(1, fadeAlpha));
        g2d.setColor(new Color(0, 0, 0, safeAlpha));
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    public boolean isFading(){
        return isFading;
    }
}
