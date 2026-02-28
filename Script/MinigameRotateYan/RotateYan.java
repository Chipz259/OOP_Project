package MinigameRotateYan;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class RotateYan {
    private JFrame mainFrame;
    private JPanel sup;
    private JPanel panel1, panel2, panel3, panel4, panel5, panel6, panel7, panel8, panel9, panel10, panel11, panel12;
    private JLabel success;
    int gap = 12;
    public RotateYan(){
        this.loadFlipSound();
        mainFrame = new JFrame();
        sup = new JPanel();

        panel1 = new ImagePanel("YanImage/yan1.png");
        panel2 = new ImagePanel("YanImage/yan2.png");
        panel3 = new ImagePanel("YanImage/yan3.png");
        panel4 = new ImagePanel("YanImage/yan4.png");
        panel5 = new ImagePanel("YanImage/yan5.png");
        panel6 = new ImagePanel("YanImage/yan6.png");
        panel7 = new ImagePanel("YanImage/yan7.png");
        panel8 = new ImagePanel("YanImage/yan8.png");
        panel9 = new ImagePanel("YanImage/yan9.png");
        panel10 = new ImagePanel("YanImage/yan10.png");
        panel11 = new ImagePanel("YanImage/yan11.png");
        panel12 = new ImagePanel("YanImage/yan12.png");

        success = new JLabel("You win");
        success.setVisible(false);

        panel1.addMouseListener(new RotateHandler(this, yanFlipSound));
        panel2.addMouseListener(new RotateHandler(this, yanFlipSound));
        panel3.addMouseListener(new RotateHandler(this, yanFlipSound));
        panel4.addMouseListener(new RotateHandler(this, yanFlipSound));
        panel5.addMouseListener(new RotateHandler(this, yanFlipSound));
        panel6.addMouseListener(new RotateHandler(this, yanFlipSound));
        panel7.addMouseListener(new RotateHandler(this, yanFlipSound));
        panel8.addMouseListener(new RotateHandler(this, yanFlipSound));
        panel9.addMouseListener(new RotateHandler(this, yanFlipSound));
        panel10.addMouseListener(new RotateHandler(this, yanFlipSound));
        panel11.addMouseListener(new RotateHandler(this, yanFlipSound));
        panel12.addMouseListener(new RotateHandler(this, yanFlipSound));

        sup.setBackground(new Color(255,255,255,0));
        mainFrame.getContentPane().setBackground(new Color(255, 255, 255, 100));

        sup.setSize(1204, 900);
        sup.setLayout(new GridLayout(3,4,gap,gap));

        mainFrame.setLayout(new BorderLayout());
        ((JComponent) mainFrame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(40,240,40,240));
//        mainFrame.setUndecorated(true);
        mainFrame.setResizable(false);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(mainFrame);


        sup.add(panel1); sup.add(panel2);
        sup.add(panel3); sup.add(panel4);
        sup.add(panel5); sup.add(panel6);
        sup.add(panel7); sup.add(panel8);
        sup.add(panel9); sup.add(panel10);
        sup.add(panel11); sup.add(panel12);
        mainFrame.add(sup, BorderLayout.CENTER);
        mainFrame.add(success, BorderLayout.SOUTH);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }

    public int[] getPanelAngle(){
        int panelAngle[] = {((ImagePanel) panel1).getAngle(),((ImagePanel) panel3).getAngle(),
                ((ImagePanel) panel2).getAngle(),((ImagePanel) panel4).getAngle(),
                ((ImagePanel) panel5).getAngle(),((ImagePanel) panel6).getAngle(),
                ((ImagePanel) panel7).getAngle(),((ImagePanel) panel8).getAngle(),
                ((ImagePanel) panel9).getAngle(),((ImagePanel) panel10).getAngle(),
                ((ImagePanel) panel11).getAngle(),((ImagePanel) panel12).getAngle()};
        return panelAngle;
    }
    public JLabel getSuccess(){
        return success;
    }
    public void winAnimated(){
        int start = 12;
        int end = 0;

        int duration = 500;
        int fps = 60;
        int delay = 900 / fps;
        int steps = (int) (duration / delay);

        Timer timer = new Timer(delay, null);

        timer.addActionListener(new ActionListener() {
            int step = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                step ++;
                double t = (double) step /steps;

                gap = (int) (start + (end - start) * t);
                LayoutManager supLayout = sup.getLayout();
                GridLayout supGrid = (GridLayout) supLayout;
                supGrid.setHgap(gap);
                supGrid.setVgap(gap);
                sup.revalidate();
                sup.repaint();

                if (step >= steps) {
                    gap = end;
                    System.out.println(((GridLayout) sup.getLayout()).getHgap());
                    timer.stop();
                }

            }
        });
        timer.start();
        mainFrame.repaint();
    }
    Clip yanFlipSound;
    public void loadFlipSound(){
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getResource("SoundEffect/yanFlip.wav"));
            yanFlipSound = AudioSystem.getClip();
            yanFlipSound.open(audio);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
