package Minigame.JigsawPackage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class JigsawFrame {
    private JFrame mainFrame;
    private JPanel sup, backgroundPanel;
    private JigsawParts jigsawPart[];
    private BufferedImage background;
    public JigsawFrame(){
        try{
            background = ImageIO.read(getClass().getResource("Image/BG pic.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Container create
        mainFrame = new JFrame();
        sup = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };
        sup.setPreferredSize(new Dimension(background.getWidth(), background.getHeight()));
        backgroundPanel = new JPanel();
        backgroundPanel.setLayout(new GridBagLayout());
        backgroundPanel.setBackground(new Color(26,26,26,177));

        //JigsawPart create
        jigsawPart = new JigsawParts[]{new JigsawParts("Image/jigsaw1.png", 670, 500, 86,70),
                new JigsawParts("Image/jigsaw2.png", 670, 500, 115,70),
                new JigsawParts("Image/jigsaw3.png", 670, 500, 442,70),
                new JigsawParts("Image/jigsaw4.png", 670, 500, 86,541),
                new JigsawParts("Image/jigsaw5.png", 670, 500, 171,456),
                new JigsawParts("Image/jigsaw6.png", 670, 500, 312,476),
                new JigsawParts("Image/jigsaw7.png", 670, 500, 86,727),
                new JigsawParts("Image/jigsaw8.png", 670, 500, 299,659),
                new JigsawParts("Image/jigsaw9.png", 670, 500, 363,872),
                new JigsawParts("Image/jigsaw10.png", 670, 500, 616,569)
        };

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(mainFrame);

        sup.setLayout(null);

        for(JigsawParts jigsaw: jigsawPart){
            sup.add(jigsaw);
        }
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;

        c.weightx = 0;
        c.weighty = 0;
        c.fill = GridBagConstraints.NONE;

        c.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(sup, c);
        mainFrame.add(backgroundPanel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
    }
}
