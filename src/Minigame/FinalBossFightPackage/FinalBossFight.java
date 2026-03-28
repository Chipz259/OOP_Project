package Minigame.FinalBossFightPackage;

import ui.MainGameFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FinalBossFight extends JPanel implements Runnable {
    private JPanel comboBox, timer ,firseGroupPhase, secondGroupPhase;
    private YanKeys YanKeysArray[];
    private Stage allStage[], nowStage;
    private boolean finished = false, timeout = false;
    private KeyHandler kh;
    private double timerStep = 1;
    private int stageCnt = 0;
    private BufferedImage timerImage, background;
    private changableImagePanel phase1, phase2, phase3, phase4;

    private MainGameFrame mainGameFrame;
    private Runnable onWinCallback, onLoseCallback;


    public FinalBossFight(MainGameFrame mainGameFrame, Runnable onWinCallback, Runnable onLoseCallback){
        this.mainGameFrame = mainGameFrame;
        this.onWinCallback = onWinCallback;
        this.onLoseCallback = onLoseCallback;

        //Set Key For Each Stage
        allStage = new Stage[12];
        for(int i = 0; i < 12; i++){
            int switchOrNot = Math.random() < 0.5 ? 0 : 1;
            if(switchOrNot == 0){
                allStage[i] = new Stage(false);
            } else{
                allStage[i] = new Stage(true);
            }
        }
        try{
            timerImage = ImageIO.read(getClass().getResource("Image/Timer.png"));
            background = ImageIO.read(getClass().getResource("Image/background.png"));
        } catch(IOException e){
            e.printStackTrace();
            timerImage = null;
        }
        phase1 = new changableImagePanel("Image/Yan_Phase1_default.PNG", "Image/Yan_Phase1_hover.PNG");
        phase2 = new changableImagePanel("Image/Yan_Phase2_default.PNG", "Image/Yan_Phase2_hover.PNG");
        phase3 = new changableImagePanel("Image/Yan_Phase3_default.PNG", "Image/Yan_Phase3_hover.PNG");
        phase4 = new changableImagePanel("Image/Yan_Phase4_default.PNG", "Image/Yan_Phase4_hover.PNG");


        firseGroupPhase = new JPanel();
        secondGroupPhase = new JPanel();
        comboBox = new JPanel();
        timer = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                if(timerImage != null){
                    g2.drawImage(timerImage, 0, 0,this);
                } else{
                    g2.setColor(Color.WHITE);
                    g2.fillRect(0, 0, getWidth(), getHeight());
                }
                g2.dispose();
            }
        };

        phase1.setOpaque(false);
        phase2.setOpaque(false);
        phase3.setOpaque(false);
        phase4.setOpaque(false);

        comboBox.setSize(1260, 303);
        comboBox.setLayout(new GridLayout(1, 4, 20, 0));
        comboBox.setBackground(new Color(217, 217, 217, 255));
        comboBox.setOpaque(false);

        if (timerImage != null) {
            timer.setSize(1260, timerImage.getHeight());
        } else {
            timer.setSize(1260, 50);
        }
        timer.setOpaque(false);

        firseGroupPhase.setSize(1520, 345);
        firseGroupPhase.setLayout(new GridLayout(1, 2, 1180, 0));
        firseGroupPhase.setOpaque(false);
        firseGroupPhase.add(phase1); firseGroupPhase.add(phase2);

        secondGroupPhase.setSize(1040, 345);
        secondGroupPhase.setLayout(new GridLayout(1, 2, 700, 0));
        secondGroupPhase.setOpaque(false);
        secondGroupPhase.add(phase3); secondGroupPhase.add(phase4);

        this.setLayout(null);
        this.setFocusable(true);

        nowStage = allStage[0];
        for (YanKeys yanKeys : nowStage.getYanKeysArray()) {
            comboBox.add(yanKeys.getContainer());
        }
        this.add(comboBox);
        this.add(timer);
        this.add(firseGroupPhase);
        this.add(secondGroupPhase);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e){
                comboBox.setLocation(getWidth() / 2 - (comboBox.getWidth() / 2), (int)(0.18 * getHeight()));
                timer.setLocation(getWidth() / 2 - (timer.getWidth() / 2), (int)(0.1 * getHeight()));
                firseGroupPhase.setLocation(getWidth() / 2 - (firseGroupPhase.getWidth() / 2), (int)(0.5 * getHeight()));
                secondGroupPhase.setLocation(getWidth() / 2 - (secondGroupPhase.getWidth() / 2), (int)(0.7 * getHeight()));
            }
        });

        SwingUtilities.invokeLater(() ->{
            kh = new KeyHandler(nowStage, this);
            this.addKeyListener(kh);
            this.requestFocusInWindow();
            comboBox.repaint();
            comboBox.revalidate();
            startTimer();
            new Thread(this).start();
        });
    }

    public void startTimer(){
        int start = timer.getWidth();
        int timerH = timer.getHeight();
        int end = 0;
        int duration = 40000;
        int fps = 60;
        int delay = 1000 / fps;
        int steps = duration / delay;

        Timer timer1 = new Timer(delay, null);
        timer1.addActionListener(new ActionListener() {
            double step = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                step += timerStep;
                if (step < 0) step = 0;
                double t = (double) step / steps;

                timer.setSize((int) (start + (end - start) * t), timerH);
                timer.repaint();
                timer.revalidate();
                repaint();
                if(step >= steps){
                    timer.setSize(end , timerH);
                    timer.revalidate();
                    timer.repaint();
                    timeout = true;
                    timer1.stop();
                }
                if(finished){
                    timer1.stop();
                    return;
                }
                if(timerStep != 1){
                    setDefaultTimer();
                }
            }
        });
        timer1.setInitialDelay(500);
        timer1.start();
    }
    public boolean isTimeOut(){
        return timeout;
    }
    public void increaseTimerStep(){
        timerStep = 180;
    }
    public void decreaseTimerStep(){
        timerStep = -50;
    }
    public void setDefaultTimer(){
        timerStep = 1;
    }
    @Override
    public void run(){
        Stage previousStage = allStage[0];
        while(!finished && !timeout){
            if(nowStage.isFinished()){
                decreaseTimerStep();
                stageCnt++;
                if (stageCnt < allStage.length) {
                    previousStage = nowStage;
                    nowStage = allStage[stageCnt];
                } else{
                    finished = true;
                    break;
                }
            }
            if(nowStage != previousStage && !finished){
                kh.setNewStage(nowStage);
                nowStage.setDefault();
                SwingUtilities.invokeLater(() -> {
                    comboBox.removeAll();
                    YanKeys[] appearYankeys = nowStage.getYanKeysArray();
                    if(nowStage.isSwitch()){
                        appearYankeys  = nowStage.getSwitchYanKeysArray();
                    }
                    for (YanKeys yanKeys : appearYankeys) {
                        comboBox.add(yanKeys.getContainer());
                    }
                    comboBox.revalidate();
                    comboBox.repaint();
                    switch (stageCnt - 1){
                        case 2 -> phase1.setActive();
                        case 5 -> phase2.setActive();
                        case 8 -> phase3.setActive();
                        case 11 -> phase4.setActive();
                    }
                });

                previousStage = nowStage;
            }
            if(finished){
                System.out.println("You Win!!!");
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(finished){
            System.out.println("You Win!!!");
        }
        else {

            System.out.println("You Losed!!");
        }

        SwingUtilities.invokeLater(() -> {
            if(finished){
                System.out.println("ระบบ: ชนะบอสแล้ว!");
                mainGameFrame.closeMinigame();
                if(onWinCallback != null) onWinCallback.run();
            } else {
                System.out.println("ระบบ: แพ้บอส! ");
                mainGameFrame.closeMinigame();
                mainGameFrame.showGameOver(true);
                if (onLoseCallback != null) onLoseCallback.run();
            }
        });

    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        if (background != null) {
            g2d.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }

        double timePercent = (double) timer.getWidth() / 1260;
        if (timePercent < 0.5) {
            Point2D center = new Point2D.Float(getWidth() / 2, getHeight() / 2);
            float radius = (float) Math.sqrt(Math.pow(getWidth(), 2) + Math.pow(getHeight(), 2)) / 2;

            float[] dist = {0.0f, 0.6f, 1.0f};
            double pulse = (Math.sin(System.currentTimeMillis() * 0.005) + 1.0) / 2.0;
            float baseAlpha = (float) (1.0 - (timePercent / 0.5));

            float finalAlpha = (float) (baseAlpha * (0.5 + 0.5 * pulse));
            finalAlpha = Math.min(0.8f, Math.max(0.0f, finalAlpha)); // ป้องกัน Error

            Color[] colors = {
                    new Color(0, 0, 0, 0),
                    new Color(255, 0, 0, (int)(baseAlpha * 50)),
                    new Color(150 * (int)(finalAlpha), 0, 0, (int)(finalAlpha * 255))
            };

            // 4. สร้าง Paint และวาดทับลงไป
            RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors);
            g2d.setPaint(p);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }

        g2d.dispose();
    }
}
