package ui;

import system.MenuActionHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CutscenePanel extends JPanel { ;
    private Font customFont;
    private Timer timer;
    private String[] storyLines;
    private boolean isAutio;
    private Runnable onCompleate;
    private int currentLineIndex = 0, charIndex = 0, waitCounter = 0;
    private String currentDisplay = "";
    private boolean isLineFinished = false;

    public CutscenePanel(Font font){
        this.customFont = font;
        this.setBackground(Color.BLACK);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMouseClick();
            }
        });

        // ตั้งเวลาให้พิมพ์ตัวอักษรทุกๆ 50 มิลลิวินาที
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                updateAutoText();
                repaint();
            }
        });
    }

    public void startCutscene(String[] lines, boolean auto, Runnable onCompleate){
        this.storyLines = lines;
        this.isAutio = auto;
        this.onCompleate = onCompleate;

        this.currentLineIndex = 0;
        this.charIndex = 0;
        this.currentDisplay = "";
        this.waitCounter = 0;
        this.isLineFinished = false;

        timer.start(); //ให้เริ่มพิมพ์
    }

    public void updateAutoText(){
        if (storyLines == null || currentLineIndex >= storyLines.length){
            finishCutscene(); //จบเนื้อเรื่อง
            return;
        }

        String fullLine = storyLines[currentLineIndex];

        if (charIndex < fullLine.length()) {
            currentDisplay += fullLine.charAt(charIndex);
            charIndex++;
            isLineFinished = false;
        }
        else {
            isLineFinished = true;
            //เมื่อพิมพ์จบ ค้างไว้ให้อ่าน
            if (isAutio){
                waitCounter++;
                if (waitCounter >= 40){
                    nextLine();
                }
            }
        }
    }


    public void handleMouseClick () {
        if (storyLines == null || currentLineIndex >= storyLines.length) return;

        if (!isLineFinished) {
            currentDisplay = storyLines[currentLineIndex];
            charIndex = storyLines[currentLineIndex].length();
            isLineFinished = true;
            waitCounter = 0;
        }
        else {
            nextLine();
        }
    }

    public void nextLine(){
        waitCounter = 0;
        charIndex = 0;
        currentDisplay = "";
        currentLineIndex++;
        isLineFinished = false;
    }

    public void finishCutscene(){
        timer.stop();
        if (onCompleate != null) {
            onCompleate.run();
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (customFont != null){
            g2d.setFont(customFont.deriveFont(Font.PLAIN, 50f));
        }
        g2d.setColor(Color.WHITE);

        //จัดให้อยู่กึ่งกลาง
        FontMetrics fm = g2d.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(currentDisplay)) / 2;
        int y = (getHeight() / 2) + (fm.getAscent() / 2);

        g2d.drawString(currentDisplay, x, y);
    }
}
