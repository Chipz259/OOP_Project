package Minigame.RotateYanPackage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.Border;

public class RotateYan {
    private JFrame mainFrame;
    private JPanel sup;
    private JPanel panel1, panel2, panel3, panel4, panel5, panel6, panel7, panel8, panel9, panel10, panel11, panel12;
    private boolean success = false;
    int gap = 15;
    int borderV = 104;
    int borderH = 83;

    public RotateYan(){
//        this.loadFlipSound();
        mainFrame = new JFrame();
        sup = new JPanel();

        panel1 = new ImagePanel("YanImage/yan1.png", this, null);
        panel2 = new ImagePanel("YanImage/yan2.png", this, null);
        panel3 = new ImagePanel("YanImage/yan3.png", this, null);
        panel4 = new ImagePanel("YanImage/yan4.png", this, null);
        panel5 = new ImagePanel("YanImage/yan5.png", this, null);
        panel6 = new ImagePanel("YanImage/yan6.png", this, null);
        panel7 = new ImagePanel("YanImage/yan7.png", this, null);
        panel8 = new ImagePanel("YanImage/yan8.png", this, null);
        panel9 = new ImagePanel("YanImage/yan9.png", this, null);
        panel10 = new ImagePanel("YanImage/yan10.png", this, null);
        panel11 = new ImagePanel("YanImage/yan11.png", this, null);
        panel12 = new ImagePanel("YanImage/yan12.png", this, null);


        sup.setBackground(new Color(214,84,84,255));
        mainFrame.getContentPane().setBackground(new Color(255, 255, 255, 100));

        sup.setBorder(BorderFactory.createEmptyBorder(borderH, borderV, borderH + 1, borderV + 1));
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
    public boolean getSuccess(){
        return success;
    }
    public void setSuccess(boolean b){
        success = b;
    }
    public void winAnimated(){
        int start = gap;
        int startVborder = borderV;
        int startHborder = borderH;
        int endVborder = 127;
        int endHborder = 98;
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
                borderV = (int) (startVborder + (endVborder - startVborder) * t);
                borderH = (int) (startHborder + (endHborder - startHborder) * t);
                LayoutManager supLayout = sup.getLayout();
                GridLayout supGrid = (GridLayout) supLayout;
                supGrid.setHgap(gap);
                supGrid.setVgap(gap);
                sup.setBorder(BorderFactory.createEmptyBorder(borderH, borderV, borderH + 1, borderV));

                sup.revalidate();
                sup.repaint();

                if (step >= steps) {
                    gap = end;
                    borderV = endVborder;
                    borderH = endHborder;
                    sup.setBorder(BorderFactory.createEmptyBorder(borderH, borderV, borderH + 1, borderV));
                    sup.revalidate();
                    sup.repaint();
                    timer.stop();
                }

            }
        });
        timer.start();
        mainFrame.repaint();
    }

    public JFrame getMainFrame(){return mainFrame;}
}