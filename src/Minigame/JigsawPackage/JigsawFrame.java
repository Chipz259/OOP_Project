package Minigame.JigsawPackage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class JigsawFrame extends JPanel {
    private JPanel sup;
    private JigsawParts jigsawPart[];
    private JButton exitButton;
    private BufferedImage background, exit;
    public JigsawFrame(){
        try{
            background = ImageIO.read(getClass().getResource("Image/BG pic.png"));
            exit = ImageIO.read(getClass().getResource("Image/Exit_minigame_btn.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Container create
        sup = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };
        exitButton = new JButton(new ImageIcon(exit));
        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        sup.setSize(background.getWidth(), background.getHeight());
        this.setLayout(null);
        this.setBackground(new Color(26,26,26,177));

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

        sup.setLayout(null);

        for(JigsawParts jigsaw: jigsawPart){
            sup.add(jigsaw);
        }

        this.add(sup);
        this.add(exitButton);
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                sup.setLocation(getWidth() / 2 - (sup.getWidth() / 2), getHeight() / 2 - (sup.getHeight() / 2));
                exitButton.setLocation((int) (0.05 * getWidth()), (int) (0.05 * getHeight()));
            }
        });
    }
}
