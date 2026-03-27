package Minigame.FinalBossFightPackage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

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


    public FinalBossFight(){
        //Set Key For Each Stage
        allStage = new Stage[]{
                new Stage(false),
                new Stage(false),
                new Stage(false),
                new Stage(false),
                new Stage(true),
                new Stage(true),
                new Stage(false),
                new Stage(false),
                new Stage(true),
                new Stage(false),
                new Stage(true),
                new Stage(false)
        };
        try{
            timerImage = ImageIO.read(getClass().getResource("Image/Timer.png"));
            background = ImageIO.read(getClass().getResource("Image/background.png"));
        } catch(IOException e){
            e.printStackTrace();
            timerImage = null;
        }
        phase1 = new changableImagePanel("Image/Yan_Phase1_default.png", "Image/Yan_Phase1_hover.png");
        phase2 = new changableImagePanel("Image/Yan_Phase2_default.png", "Image/Yan_Phase2_hover.png");
        phase3 = new changableImagePanel("Image/Yan_Phase3_default.png", "Image/Yan_Phase3_hover.png");
        phase4 = new changableImagePanel("Image/Yan_Phase4_default.png", "Image/Yan_Phase4_hover.png");


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

        phase1.setSize(200, 250);
        phase2.setSize(200, 250);
        phase3.setSize(200, 250);
        phase4.setSize(200, 250);

        phase1.setOpaque(false);
        phase2.setOpaque(false);
        phase3.setOpaque(false);
        phase4.setOpaque(false);

        comboBox.setSize(1260, 303);
        comboBox.setLayout(new GridLayout(1, 4, 20, 0));
        comboBox.setBackground(new Color(217, 217, 217, 255));
        comboBox.setOpaque(false);

        timer.setSize(1260, timerImage.getHeight());
        timer.setOpaque(false);

        firseGroupPhase.setSize(1580, 250);
        firseGroupPhase.setLayout(new GridLayout(1, 2, 1180, 0));
        firseGroupPhase.setOpaque(false);
        firseGroupPhase.add(phase1); firseGroupPhase.add(phase2);

        secondGroupPhase.setSize(1100, 250);
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
                firseGroupPhase.setLocation(getWidth() / 2 - (firseGroupPhase.getWidth() / 2), (int)(0.6 * getHeight()));
                secondGroupPhase.setLocation(getWidth() / 2 - (secondGroupPhase.getWidth() / 2), (int)(0.8 * getHeight()));
            }
        });
        SwingUtilities.invokeLater(() ->{
            kh = new KeyHandler(nowStage, this);
            this.addKeyListener(kh);
            startTimer();
        });
    }
    public boolean isFinished() {
        return finished;
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
                double t = (double) step / steps;

                timer.setSize((int) (start + (end - start) * t), timerH);
                timer.repaint();
                timer.revalidate();
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
            }
        });
        timer1.setInitialDelay(500);
        timer1.start();
    }
    public boolean isTimeOut(){
        return timeout;
    }
    public void increaseTimerStep(){
        timerStep++;
    }
    @Override
    public void run(){
        Stage previousStage = allStage[0];
        while(!finished && !timeout){
            if(nowStage.isFinished()){
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
                SwingUtilities.invokeLater(() -> {
                    comboBox.removeAll();
                    for (YanKeys yanKeys : nowStage.getYanKeysArray()) {
                        yanKeys.setUnactive();
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
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }
}
