package Minigame.RotateNarigaPackage;

import ui.MainGameFrame;

import javax.swing.*;
import java.awt.*;

public class RotateNariga extends JPanel{
    private JPanel bg, kemSmall, kemBig, btPanel;
    private JButton increaseH, increaseM, closeBtn;
    private boolean finishRotateNariga = false;
    private MainGameFrame mainGameFrame;
    private int[] saveAngles;
    private boolean[] isSolvedState;
    private Runnable onwin;

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

        bg = new ImagePanel("Image/Nariga.png");
        kemSmall = new KemImagePanel("Image/kemSmall.png", 30,saveAngles[0]);
        kemBig = new KemImagePanel("Image/kemBig.png", 6, saveAngles[1]);

        increaseH.addMouseListener(new RotateNarigaHandler(this, 30, kemSmall));
        increaseM.addMouseListener(new RotateNarigaHandler(this,6, kemBig));

        btPanel.setLayout(new GridLayout(1,3,10,10));
        bg.setLayout(null);
//        ((JComponent) mainFrame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(40,240,40,240));
//        mainFrame.setUndecorated(true);
//        mainFrame.setResizable(false);

//        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
//        gd.setFullScreenWindow(mainFrame);

        btPanel.add(increaseH); btPanel.add(increaseM); btPanel.add(closeBtn);
        bg.add(kemSmall); bg.add(kemBig);
        this.add(bg, BorderLayout.CENTER);
        this.add(btPanel, BorderLayout.SOUTH);
//        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        mainFrame.setVisible(true);

//        SwingUtilities.invokeLater(() -> {
//            kemSmall.setLocation(bg.getWidth() / 2 - (kemSmall.getWidth() / 2), bg.getHeight() / 2 - (kemSmall.getHeight() / 2));
//            kemBig.setLocation(bg.getWidth() / 2 - (kemBig.getWidth() / 2), bg.getHeight() / 2 - (kemBig.getHeight() / 2));
//        });

        bg.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                kemSmall.setLocation(bg.getWidth() / 2 - (kemSmall.getWidth() / 2), bg.getHeight() / 2 - (kemSmall.getHeight() / 2));
                kemBig.setLocation(bg.getWidth() / 2 - (kemBig.getWidth() / 2), bg.getHeight() / 2 - (kemBig.getHeight() / 2));
            }
        });
    }
    public void setFinishRotateNariga(boolean b){
        finishRotateNariga = b;
    }
    public boolean getFinishRotateNariga(){
        return finishRotateNariga;
    }
    public int[] getKemAngle(){
        int kemAngle[] = {((KemImagePanel) kemSmall).getAngle(), ((KemImagePanel) kemBig).getAngle()};
        return  kemAngle;
    }

    public void winClose() {
        setFinishRotateNariga(true);
        isSolvedState[0] = true;
        System.out.println("เล่นผ่านแล้วจ้าาา");

        if (onwin != null) {
            onwin.run();
        }

        saveAngles[0] = ((KemImagePanel) kemSmall).getAngle();
        saveAngles[1] = ((KemImagePanel) kemBig).getAngle();

        mainGameFrame.closeMinigame();
    }
}
