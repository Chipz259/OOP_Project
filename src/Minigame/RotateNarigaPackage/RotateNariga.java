package Minigame.RotateNarigaPackage;

import javax.swing.*;
import java.awt.*;

public class RotateNariga {
    private JPanel bg, kemSmall, kemBig, btPanel;
    private JFrame mainFrame;
    private JButton increaseH, increaseM;
    private boolean finishRotateNariga = false;
    public  RotateNariga(){
        mainFrame = new JFrame();
        btPanel = new JPanel();
        increaseH = new JButton("+ H");
        increaseM = new JButton("+ M");
        bg = new ImagePanel("Image/Nariga.png");
        kemSmall = new KemImagePanel("Image/kemSmall.png", 6,0);
        kemBig = new KemImagePanel("Image/kemBig.png", 30, 90);

        increaseH.addMouseListener(new RotateNarigaHandler(this, 30, kemBig));
        increaseM.addMouseListener(new RotateNarigaHandler(this,6, kemSmall));

        btPanel.setLayout(new GridLayout(1,2,10,10));
        bg.setLayout(null);
        ((JComponent) mainFrame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(40,240,40,240));
//        mainFrame.setUndecorated(true);
        mainFrame.setResizable(false);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(mainFrame);

        btPanel.add(increaseH); btPanel.add(increaseM);
        bg.add(kemSmall); bg.add(kemBig);
        mainFrame.add(bg, BorderLayout.CENTER);
        mainFrame.add(btPanel, BorderLayout.SOUTH);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

        mainFrame.setVisible(true);

        SwingUtilities.invokeLater(() -> {
            kemSmall.setLocation(bg.getWidth() / 2 - (kemSmall.getWidth() / 2), bg.getHeight() / 2 - (kemSmall.getHeight() / 2));
            kemBig.setLocation(bg.getWidth() / 2 - (kemBig.getWidth() / 2), bg.getHeight() / 2 - (kemBig.getHeight() / 2));
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
}
