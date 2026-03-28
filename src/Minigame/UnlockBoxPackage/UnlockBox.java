package Minigame.UnlockBoxPackage;

import ui.MainGameFrame;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

public class UnlockBox extends JPanel implements Runnable {

    private SlotJPanel slot[];
    private JPanel mainPanel, slotPanel, upButtonPanel, downButtonPanel;
    private BufferedImage background, slotBackground, exit;
    private JButton exitButton;
    private static boolean finished = false;
    private MainGameFrame mainGameFrame;
    private Runnable onWinCallback;
    private Thread checkThread;

    public  UnlockBox(MainGameFrame mainGameFrame, Runnable onWinCallback) {
        this.mainGameFrame = mainGameFrame;
        this.onWinCallback = onWinCallback;
        finished = false;

        slot = new SlotJPanel[]{new SlotJPanel("Image/Box1.png"),
            new SlotJPanel("Image/Box2.png"),
            new SlotJPanel("Image/Box3.png"),
            new SlotJPanel("Image/Box4.png"),
        };
        try{
            background = ImageIO.read(getClass().getResource("Image/BG BOX.png"));
            slotBackground = ImageIO.read(getClass().getResource("Image/BoxBox.png"));
            exit = ImageIO.read(getClass().getResource("Image/Exit_minigame_btn.png"));
        } catch (IOException e) {
            e.printStackTrace();
            background = null;
            slotBackground = null;
            exit = null;
        }

        mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        upButtonPanel = new JPanel();
        downButtonPanel = new JPanel();
        exitButton = new JButton(new ImageIcon(exit));
        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitButton.addActionListener(e -> {
            finished = true;
            mainGameFrame.closeMinigame();
        });

        slotPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(slotBackground, 0, 0,this);
            }
        };

        slotPanel.setOpaque(false);
        upButtonPanel.setLayout(new GridLayout(1, 4));
        downButtonPanel.setLayout(new GridLayout(1, 4));
        slotPanel.setLayout(new GridLayout(1, 4));
        mainPanel.setLayout(new BorderLayout());
        this.setLayout(null);

        mainPanel.setSize(1600, 587);
        upButtonPanel.setSize(1600, 84);
        downButtonPanel.setSize(1600, 84);
        exitButton.setSize(exit.getWidth(), exit.getHeight());

        mainPanel.setOpaque(false);
        slotPanel.setOpaque(false);
        upButtonPanel.setOpaque(false);
        downButtonPanel.setOpaque(false);

        slotPanel.setBorder(BorderFactory.createEmptyBorder(60,80,60,80));
        upButtonPanel.setBorder(BorderFactory.createEmptyBorder(0,80,0,80));
        downButtonPanel.setBorder(BorderFactory.createEmptyBorder(0,80,0,80));
        ((GridLayout)upButtonPanel.getLayout()).setHgap(80);
        ((GridLayout)downButtonPanel.getLayout()).setHgap(80);
        ((GridLayout)slotPanel.getLayout()).setHgap(80);
        for(int i = 0 ; i < 4; i++) {
            upButtonPanel.add(new UpDownButton(1, slot[i]));
            downButtonPanel.add(new UpDownButton(-1, slot[i]));
            slotPanel.add(slot[i]);
        }

        mainPanel.add(upButtonPanel, BorderLayout.NORTH);
        mainPanel.add(slotPanel, BorderLayout.CENTER);
        mainPanel.add(downButtonPanel,BorderLayout.SOUTH);
        this.add(exitButton);
        this.add(mainPanel);
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                mainPanel.setLocation(getWidth() / 2 - (mainPanel.getWidth() / 2), getHeight() / 2 - (mainPanel.getHeight() / 2));
                exitButton.setLocation((int) (0.05 * getWidth()), (int) (0.05 * getHeight()));
            }
        });

        checkThread = new Thread(this);
        checkThread.start();
    }

    @Override
    public void run() {
        int[] target = new int[]{5, 2, 2, 2};
        while (!finished) {
            int now[] = new int[slot.length];
            for(int i = 0; i < slot.length; i++){
                now[i] = slot[i].getNowSlot();
            }
            if(Arrays.equals(now, target)){
                finished = !finished;
                System.out.println("Unlock box finished!!");

                SwingUtilities.invokeLater(() -> {
                    if (onWinCallback != null) onWinCallback.run();
                    mainGameFrame.closeMinigame();
                });

                return;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static boolean isFinished() {return finished;}
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }
}
