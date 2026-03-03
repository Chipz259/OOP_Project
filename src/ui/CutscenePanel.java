package ui;

import system.MenuActionHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CutscenePanel extends JPanel {
    private MainGameFrame mainFrame;
    private Font customFont;

    private String[] storyLines = {
        "อยู่ๆ ก็มีคนโทรมา ติ๊งๆๆๆๆ",
        "รับสายดิวะๆๆๆๆ",
        "พ่อตายแล้วน้าฮือๆๆๆ",
        "ไปงานศพพ่อด้วยจ้าาาา"
    };

    private int currentLineIndex = 0, charIndex = 0, waitCounter = 0;
    private String currentDisplay = "";
    private Timer timer;

    public CutscenePanel(MainGameFrame frame, Font font){
        this.mainFrame = frame;
        this.customFont = font;
        this.setBackground(Color.BLACK);

        // ตั้งเวลาให้พิมพ์ตัวอักษรทุกๆ 50 มิลลิวินาที
        timer = new Timer(50, new ActionListener() {
        
            @Override
            public void actionPerformed(ActionEvent e){
                updateAutoText();
                repaint();
            }
        });
    }

    public void startCutscene(){
        currentLineIndex = 0;
        charIndex = 0;
        currentDisplay = "";
        waitCounter = 0;
        timer.start(); //ให้เริ่มพิมพ์
    }

    public void updateAutoText(){
        if (currentLineIndex >= storyLines.length){
            timer.stop();
//            mainFrame.transitionToGame();
            return;
        }

        String fullLine = storyLines[currentLineIndex];

        if (charIndex < fullLine.length()) {
            currentDisplay += fullLine.charAt(charIndex);
            charIndex++;
        }
        else {
            //เมื่อพิมพ์จบ ค้างไว้ให้อ่าน
            waitCounter++;
            if (waitCounter == 40){
                waitCounter = 0;
                charIndex = 0;
                currentDisplay = "";
                currentLineIndex++;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (customFont != null){
            g2d.setFont(customFont.deriveFont(Font.PLAIN, 50));
        }
        g2d.setColor(Color.WHITE);

        //จัดให้อยู่กึ่งกลาง
        FontMetrics fm = g2d.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(currentDisplay)) / 2;
        int y = (getHeight() / 2) + (fm.getAscent() / 2);

        g2d.drawString(currentDisplay, x, y);
    }
}
