package Minigame.JigsawPackage;

import java.awt.*;
import javax.swing.*;

public class JigsawFrame {
    private JFrame mainFrame;
    private JPanel sup,picFrame;
    private JigsawParts jigsawPart[];
    public JigsawFrame(){
        //Container create
        mainFrame = new JFrame();
        sup = new JPanel();
        picFrame = new JPanel();

        //JigsawPart create
        jigsawPart = new JigsawParts[]{new JigsawParts("JigsawImage/jigsaw1.png", 670, 500, 30,718),
                new JigsawParts("JigsawImage/jigsaw2.png", 670, 500, 559,559),
                new JigsawParts("JigsawImage/jigsaw3.png", 670, 500, 307,862),
                new JigsawParts("JigsawImage/jigsaw4.png", 670, 500, 59,62),
                new JigsawParts("JigsawImage/jigsaw5.png", 670, 500, 385,62),
                new JigsawParts("JigsawImage/jigsaw6.png", 670, 500, 30,62),
                new JigsawParts("JigsawImage/jigsaw7.png", 670, 500, 30,532),
                new JigsawParts("JigsawImage/jigsaw8.png", 670, 500, 254,465),
                new JigsawParts("JigsawImage/jigsaw9.png", 670, 500, 114,445),
                new JigsawParts("JigsawImage/jigsaw10.png", 670, 500, 228,684)
        };

        ((JComponent)mainFrame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(40, 240, 40, 240));

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(mainFrame);

        picFrame.setBounds(30,60,650,939);
        picFrame.setBackground(new Color(127,127,127,255));
        sup.setBackground(new Color(37,28,9,255));
        sup.setLayout(null);

        sup.setSize(1320,990);
        for(JigsawParts jigsaw: jigsawPart){
            sup.add(jigsaw);
        }
        sup.add(picFrame);
        mainFrame.add(sup);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);

    }
}
