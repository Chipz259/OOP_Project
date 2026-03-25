package ui;

import system.MenuActionHandler;
import system.FontManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CutscenePanel extends JPanel { ;
    private Timer timer;
    private TextAnimator textAnimator = new TextAnimator();

    public CutscenePanel(){

        this.setBackground(Color.BLACK);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                textAnimator.handleMouseClick();
            }
        });

        // ตั้งเวลาให้พิมพ์ตัวอักษรทุกๆ 50 มิลลิวินาที
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                textAnimator.update();
                repaint();

                if (!textAnimator.isActive()) {
                    timer.stop();
                }
            }
        });
    }

    public void startCutscene(String[] lines, boolean auto, boolean canSkip, Runnable onComplete){
        textAnimator.startText(lines, auto, canSkip, onComplete);
        timer.start(); //ให้เริ่มพิมพ์
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        textAnimator.draw(g2d, FontManager.customFont, -1, (getHeight() / 2), getWidth());
    }
}
