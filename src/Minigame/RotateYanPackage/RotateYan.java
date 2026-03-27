package Minigame.RotateYanPackage;

import ui.MainGameFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class RotateYan extends JPanel{
    private MainGameFrame mainGameFrame;
    private Runnable onWinCallBack;
    private JPanel mainPanel;
    private  YanJPanel[] yanArray;
    private JButton exitButton;
    private JButton backButton;
    private boolean success = false;
    int gap = 12;
    private BufferedImage background, exit;

    public RotateYan(MainGameFrame mainGameFrame, Runnable onWinCallBack){
        try{
            background = ImageIO.read(getClass().getResource("Image/BG Door.PNG"));
            exit = ImageIO.read(getClass().getResource("Image/Exit_minigame_btn.png"));
        }catch(IOException e){
            e.printStackTrace();
            background = null;
        }
        this.mainGameFrame = mainGameFrame;
        this.onWinCallBack = onWinCallBack;

        mainPanel = new JPanel();
        mainPanel.setSize(1204, 901);
        mainPanel.setLayout(new GridLayout(3,4));

        exitButton = new JButton(new ImageIcon(exit));
        exitButton.setSize(exit.getWidth(), exit.getHeight());
        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitButton.addActionListener(e -> {
            mainGameFrame.closeMinigame();
        });

        yanArray = new YanJPanel[]{
                new YanJPanel("Image/yan1.png", this),
                new YanJPanel("Image/yan2.png", this),
                new YanJPanel("Image/yan3.png", this),
                new YanJPanel("Image/yan4.png", this),
                new YanJPanel("Image/yan5.png", this),
                new YanJPanel("Image/yan6.png", this),
                new YanJPanel("Image/yan7.png", this),
                new YanJPanel("Image/yan8.png", this),
                new YanJPanel("Image/yan9.png", this),
                new YanJPanel("Image/yan10.png", this),
                new YanJPanel("Image/yan11.png", this),
                new YanJPanel("Image/yan12.png", this),
        };

        mainPanel.setLayout(new GridLayout(3,4,gap,gap));
        mainPanel.setOpaque(false);

        for(YanJPanel yan : yanArray){
            mainPanel.add(yan);
        }

        this.setLayout(null);
        this.setOpaque(false);

        this.add(mainPanel);
        this.add(exitButton);
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                mainPanel.setLocation(getWidth() / 2 - (mainPanel.getWidth() / 2), getHeight() / 2 - (mainPanel.getHeight() / 2));
                exitButton.setLocation((int) (0.05 * getWidth()), (int) (0.05 * getHeight()));
            }
        });
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        if(background != null){
            g2.drawImage(background, 0, 0, this);
        } else{
            g2.setColor(new Color(26,26,26,100));
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
        g2.dispose();

    }

    public int[] getPanelAngle(){
        int panelAngle[] = new int[yanArray.length];
        for(int i = 0; i < yanArray.length; i++){
            panelAngle[i] = yanArray[i].getAngle();
        }
        return panelAngle;
    }
    public boolean getSuccess(){
        return success;
    }
    public void setSuccess(boolean b){
        success = b;
    }
    public void winAnimated(){
        int start = gap;
        int endVborder = 18;
        int endHborder = 12;
        int end = 0;
        int duration = 1000;
        int fps = 60;
        int delay = 1000 / fps;
        int steps = duration / delay;

        Timer timer = new Timer(delay, null);

        timer.addActionListener(new ActionListener() {
            int step = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                step ++;
                double t = (double) step /steps;

                gap = (int) (start + (end - start) * t);
                int borderV = (int) (endVborder * t);
                int borderH = (int) (endHborder * t);
                LayoutManager mainPanelLayout = mainPanel.getLayout();
                GridLayout mainPanelGrid = (GridLayout) mainPanelLayout;
                mainPanelGrid.setHgap(gap);
                mainPanelGrid.setVgap(gap);
                mainPanel.setBorder(BorderFactory.createEmptyBorder(borderH, borderV, borderH, borderV));

                mainPanel.revalidate();
                mainPanel.repaint();

                if (step >= steps) {
                    gap = end;
                    borderV = endVborder;
                    borderH = endHborder;
                    mainPanel.setBorder(BorderFactory.createEmptyBorder(borderH, borderV, borderH, borderV));
                    mainPanel.repaint();
                    timer.stop();

                    //พอแอนิเมชันชนะเล่นจบ รอ 1 วิ แล้วปิดหน้าต่างมินิเกมอัตโนมัติ
                    Timer closeTime = new Timer(1000, evt -> {
                        if (onWinCallBack != null) onWinCallBack.run();
                        mainGameFrame.closeMinigame();
                    });
                    closeTime.setRepeats(false);
                    closeTime.start();
                }

            }
        });
        timer.start();
        this.repaint();
    }

}