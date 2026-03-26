package Minigame.UnlockBoxPackage;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

public class UnlockBox implements Runnable {

    private JFrame mainFrame;
    private SlotJPanel slot[];
    private JPanel mainPanel, slotPanel, upButtonPanel, downButtonPanel, backgroundPanel;
    private BufferedImage background, slotBackground;
    private static boolean finished = false;

    public  UnlockBox() {
        mainFrame = new JFrame();
        mainPanel = new JPanel();

        mainPanel.setOpaque(false);
        slot = new SlotJPanel[]{new SlotJPanel("Image/Box1.png"),
            new SlotJPanel("Image/Box2.png"),
            new SlotJPanel("Image/Box3.png"),
            new SlotJPanel("Image/Box4.png"),
        };
        try{
            background = ImageIO.read(getClass().getResource("Image/BG BOX.png"));
            slotBackground = ImageIO.read(getClass().getResource("Image/BoxBox.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        backgroundPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };
        upButtonPanel = new JPanel();
        downButtonPanel = new JPanel();
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
        backgroundPanel.setLayout(null);

        mainPanel.setSize(1600,587);
        upButtonPanel.setSize(1600, 84);
        downButtonPanel.setSize(1600, 84);

        mainPanel.setOpaque(false);
        slotPanel.setOpaque(false);
        upButtonPanel.setOpaque(false);
        downButtonPanel.setOpaque(false);

        slotPanel.setBorder(BorderFactory.createEmptyBorder(60,80,60,80));
        upButtonPanel.setBorder(BorderFactory.createEmptyBorder(0,80,0,80));
        downButtonPanel.setBorder(BorderFactory.createEmptyBorder(0,80,0,80));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(40,240,40,240));
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
//        mainFrame.setUndecorated(true);
        mainFrame.setResizable(false);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(mainFrame);

        backgroundPanel.add(mainPanel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(backgroundPanel,  BorderLayout.CENTER);
        mainFrame.setVisible(true);
        SwingUtilities.invokeLater(() ->{
            mainPanel.setLocation(mainPanel.getParent().getWidth()/2 - mainPanel.getWidth()/2, mainPanel.getParent().getHeight()/2 -  mainPanel.getHeight()/2);
            mainPanel.getParent().repaint();
            mainPanel.getParent().revalidate();
        });
    }
    @Override
    public void run() {
        int[] target = new int[]{5, 2, 2, 2};
        while (true) {
            int now[] = new int[slot.length];
            for(int i = 0; i < slot.length; i++){
                now[i] = slot[i].getNowSlot();
            }
            if(Arrays.equals(now, target)){
                finished = !finished;
                System.out.println("Unlock box finished!!");
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
}
