package Minigame.JigsawPackage;

import java.awt.*;
import javax.swing.*;

public class JigsawFrame {
    private JFrame mainFrame;
    private JPanel sup,picFrame;
    private JPanel jigsawPart1, jigsawPart2, jigsawPart3, jigsawPart4, jigsawPart5, jigsawPart6, jigsawPart7;
    public JigsawFrame(){
        //Container create
        mainFrame = new JFrame();
        sup = new JPanel();
        picFrame = new JPanel();

        //JigsawPart create
        jigsawPart1 = new JigsawParts("JigsawImage/jigsaw1.png",800, 100, 30,60);
        jigsawPart2 = new JigsawParts("JigsawImage/jigsaw2.png", 900, 350, 239, 59);
        jigsawPart3 = new JigsawParts("JigsawImage/jigsaw3.png", 800, 60, 30, 284);
        jigsawPart4 = new JigsawParts("JigsawImage/jigsaw4.png", 900, 550, 236, 375);
        jigsawPart5 = new JigsawParts("JigsawImage/jigsaw5.png", 990, 220, 504, 272);
        jigsawPart6 = new JigsawParts("JigsawImage/jigsaw6.png", 800, 400, 30, 592);
        jigsawPart7 = new JigsawParts("JigsawImage/jigsaw7.png", 770, 190, 303, 581);


        picFrame.setBounds(30,60,670,870);
        picFrame.setBackground(new Color(127,127,127,255));
        sup.setBackground(new Color(37,28,9,255));
        sup.setLayout(null);
        mainFrame.setLayout(null);
        ((JComponent) mainFrame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(40,240,40,240));
//        mainFrame.setUndecorated(true);
        mainFrame.setResizable(false);


        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(mainFrame);
        sup.setBounds((int)((mainFrame.getWidth() - 1320) / 2),(int)((mainFrame.getHeight() - 990) / 2),1320,990);

        sup.add(jigsawPart1); sup.add(jigsawPart2); sup.add(jigsawPart3);
        sup.add(jigsawPart4); sup.add(jigsawPart5); sup.add(jigsawPart6);
        sup.add(jigsawPart7);
        sup.add(picFrame);
        mainFrame.add(sup);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }
}
