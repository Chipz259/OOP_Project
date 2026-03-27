package Minigame.RotateNarigaPackage;

import ui.MainGameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class RotateNariga extends JPanel{
    private JPanel nariga, kemSmall, kemBig, btPanel;
    private JButton increaseH, increaseM, closeBtn;
    private boolean finished = false;
    private MainGameFrame mainGameFrame;
    private int[] saveAngles;
    private boolean[] isSolvedState;
    private Runnable onwin;
    private BufferedImage narigaI;

    public  RotateNariga(MainGameFrame mainGameFrame, int[] saveAngles, boolean[] isSolvedState, Runnable onwin){
        this.mainGameFrame = mainGameFrame;
        this.saveAngles = saveAngles;
        this.isSolvedState = isSolvedState;
        this.onwin = onwin;

        this.setLayout(new BorderLayout());

        btPanel = new JPanel();
        increaseH = new JButton("+ H");
        increaseM = new JButton("+ M");
        closeBtn = new JButton("ออก");
        closeBtn.addActionListener(e -> {
            saveAngles[0] = ((KemImagePanel) kemSmall).getAngle();
            saveAngles[1] = ((KemImagePanel) kemBig).getAngle();
            mainGameFrame.closeMinigame();
        });

        nariga = new ImagePanel("Image/Nariga.png");
        kemSmall = new KemImagePanel("Image/kemSmall.png", 30,saveAngles[0]);
        kemBig = new KemImagePanel("Image/kemBig.png", 6, saveAngles[1]);

        increaseH.addMouseListener(new RotateNarigaHandler(this, 30, kemSmall));
        increaseM.addMouseListener(new RotateNarigaHandler(this,6, kemBig));

        btPanel.setLayout(new GridLayout(1,3,10,10));
        nariga.setLayout(null);

        btPanel.add(increaseH); btPanel.add(increaseM); btPanel.add(closeBtn);
        nariga.add(kemSmall); nariga.add(kemBig);
        this.add(nariga, BorderLayout.CENTER);
        this.add(btPanel, BorderLayout.SOUTH);


        nariga.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                kemSmall.setLocation(nariga.getWidth() / 2 - (kemSmall.getWidth() / 2), nariga.getHeight() / 2 - (kemSmall.getHeight() / 2));
                kemBig.setLocation(nariga.getWidth() / 2 - (kemBig.getWidth() / 2), nariga.getHeight() / 2 - (kemBig.getHeight() / 2));
            }
        });
    }
    public boolean isFinished(){
        return finished;
    }
    public int[] getKemAngle(){
        int kemAngle[] = {((KemImagePanel) kemSmall).getAngle(), ((KemImagePanel) kemBig).getAngle()};
        return  kemAngle;
    }

    public void winClose() {
        finished = true;
        isSolvedState[0] = true;
        System.out.println("เล่นผ่านแล้วจ้าาา");

        if (onwin != null) {
            onwin.run();
        }

        saveAngles[0] = ((KemImagePanel) kemSmall).getAngle();
        saveAngles[1] = ((KemImagePanel) kemBig).getAngle();

        mainGameFrame.closeMinigame();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
