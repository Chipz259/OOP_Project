package Minigame.FinalBossFightPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class FinalBossFight {
    private JFrame mainFrame;
    private JPanel timer,comboBox;
    private JPanel targetImgArray[];
    private JLabel timerText;
    private int targetArray[] = {KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_S};
    private int timerW, timerH = 20;

    public FinalBossFight(){
        mainFrame = new JFrame();

        timer = new JPanel();
        timerText = new JLabel();
        targetImgArray = new JPanel[]{new changableImagePanel("Image/Alpha1.png"),
                new changableImagePanel("Image/Alpha2.png"),
                new changableImagePanel("Image/Alpha3.png"),
                new changableImagePanel("Image/Alpha4.png")
        };
        comboBox = new JPanel();
        comboBox.setLocation(240, 100);
        comboBox.setLayout(new GridLayout(1, 4, 20, 30));
        timerText.setLocation(500, 500);
        timerText.setSize(200, 200);
        timer.setBounds(240,300, 20,20);
        timer.setBackground(new Color(255,255,255,255));
        mainFrame.setLayout(null);
        mainFrame.getContentPane().setBackground(new Color(217, 217, 217, 255));

        mainFrame.setResizable(false);
        mainFrame.setFocusable(true);
        mainFrame.requestFocusInWindow();
//        mainFrame.addKeyListener(new KeyHandler(this));

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(mainFrame);
        for (int i = 0 ; i < targetImgArray.length; i++){
            comboBox.add(targetImgArray[i]);
        }
        mainFrame.add(timer); mainFrame.add(timerText); mainFrame.add(comboBox);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

        SwingUtilities.invokeLater(() -> {
            timerW = mainFrame.getWidth() - 480;
            timer.setSize(timerW, timerH);
            comboBox.setSize(mainFrame.getWidth() - 480, 200);
            startTimer();
        });
    }
    public int[] getTargetArray(){return targetArray;}
    public JPanel[] getTargetImgArray(){return targetImgArray;}
    public void startTimer(){
        int duration = 5000;
        int fps = 60;
        int delay = 1000 / fps;
        int steps = duration / delay;
        int start = timer.getWidth();
        int end = 0;

        Timer timer1 = new Timer(delay, null);

        timer1.addActionListener(new ActionListener() {
            int step = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                step++;
                double t = (double) step / steps;
                timerW = (int) (start + (end - start) * t);
                timer.setSize(timerW, timerH);
                timerText.setText(String.valueOf(step * delay / 1000));
                timer.revalidate();
                timer.repaint();
                if (step >= steps){
                    timerW = end;
                    timer.setSize(timerW, timerH);
                    timer.revalidate();
                    timer.repaint();
                    timerText.setText(String.valueOf(step * delay / 1000));
                    timer1.stop();
                }
            }

        });
        timer1.start();
        mainFrame.repaint();
    }

}
