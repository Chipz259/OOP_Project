package Minigame.UnlockBoxPackage;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class UnlockBox {
    private JFrame mainFrame;
    private JPanel slot;
    private JButton up, down;

    public  UnlockBox() {
        mainFrame = new JFrame();
        slot = new SlotJPanel("Image/testSlot.png");
        up = new JButton("Up");
        down = new JButton("Down");

        slot.setLocation(50,500);
        up.setBounds(50, 250, 100, 100);
        down.setBounds(50, 750, 100, 100);


        up.addActionListener(new ActionHandler(slot));
        down.addActionListener(new ActionHandler(slot));

        mainFrame.setLayout(null);
        ((JComponent) mainFrame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(40, 240, 40, 240));
//        mainFrame.setUndecorated(true);
        mainFrame.setResizable(false);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(mainFrame);

        mainFrame.add(slot);
        mainFrame.add(up);
        mainFrame.add(down);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }
}
