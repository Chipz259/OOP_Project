package Minigame.FinalBossFightPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class FinalBossFight implements Runnable {
    private JFrame mainFrame;
    private JPanel comboBox, timer;
    private YanKeys YanKeysArray[];
    private Stage stage1, stage2, stage3, stage4, nowStage;
    private boolean finished = false, timeout = false;
    private KeyHandler kh;
    private double timerStep = 1;


    public FinalBossFight(){
        //Setting All key's Attribute
        YanKeysArray = new YanKeys[]{new YanKeys(KeyEvent.VK_W, "Image/Alpha1.png"),
                new YanKeys(KeyEvent.VK_A, "Image/Alpha2.png"),
                new YanKeys(KeyEvent.VK_S, "Image/Alpha3.png"),
                new YanKeys(KeyEvent.VK_D, "Image/Alpha4.png")
        };

        //Set Key For Each Stage
        stage1 = new Stage(new YanKeys[]{YanKeysArray[0],YanKeysArray[1],YanKeysArray[2], YanKeysArray[3]});
        stage2 = new Stage(new YanKeys[]{YanKeysArray[3], YanKeysArray[0],YanKeysArray[2], YanKeysArray[1]});
        stage3 = new Stage(new YanKeys[]{YanKeysArray[2], YanKeysArray[0], YanKeysArray[1],  YanKeysArray[3]});
        stage4 = new Stage(new YanKeys[]{YanKeysArray[1], YanKeysArray[3], YanKeysArray[0],  YanKeysArray[2]});

        mainFrame = new JFrame();
        comboBox = new JPanel();
        timer = new JPanel();

        timer.setBackground(Color.white);
        timer.setLocation(240, 380);
        comboBox.setLocation(240, 100);
        comboBox.setLayout(new GridLayout(1, 4, 20, 0));
        comboBox.setBackground(new Color(217, 217, 217, 255));
        mainFrame.setLayout(null);
        mainFrame.getContentPane().setBackground(new Color(252, 240, 202, 255));

        mainFrame.setResizable(false);
        mainFrame.setFocusable(true);
        mainFrame.requestFocusInWindow();


        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(mainFrame);
        nowStage = stage1;
        kh = new KeyHandler(nowStage, this);
        mainFrame.addKeyListener(kh);
        for (YanKeys yanKeys : nowStage.getYanKeysArray()) {
            comboBox.add(yanKeys.getImg());
        }
        mainFrame.add(comboBox);
        mainFrame.add(timer);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SwingUtilities.invokeLater(() -> {
            comboBox.setSize(mainFrame.getWidth() - 480, 250);
            timer.setSize(mainFrame.getWidth() - 480, 7);
            mainFrame.revalidate();
            mainFrame.repaint();
            mainFrame.setVisible(true);
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
        int duration = 30000;
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
                timer.revalidate();
                timer.repaint();

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
        mainFrame.repaint();
    }
    public boolean isTimeOut(){
        return timeout;
    }
    public void increaseTimerStep(){
        timerStep++;
    }
    @Override
    public void run(){
        Stage previousStage = stage1;
        while(!finished && !timeout){
            if(stage1.isFinished()){
                if(stage2.isFinished()){
                    if(stage3.isFinished()){
                        if(stage4.isFinished()){
                            finished = !finished;
                        } else{
                            nowStage = stage4;
                        }
                    }else{
                        nowStage = stage3;
                    }
                } else{
                    nowStage = stage2;
                }
            }
            if(nowStage != previousStage && !finished){
                kh.setNewStage(nowStage);
                for (YanKeys yanKeys : nowStage.getYanKeysArray()) {
                    yanKeys.setAsUnactive();
                    comboBox.add(yanKeys.getImg());
                }
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
}
