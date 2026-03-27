package Minigame.JigsawPackage;

import ui.MainGameFrame;

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
    private MainGameFrame mainGameFrame;
    private Runnable onWinCallback;

    public JigsawFrame(MainGameFrame mainGameFrame, Runnable onWinCallback){
        this.mainGameFrame = mainGameFrame;
        this.onWinCallback = onWinCallback;

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
        exitButton.addActionListener(e -> {
            mainGameFrame.closeMinigame();
        });

        sup.setSize(background.getWidth(), background.getHeight());
        this.setLayout(null);
        this.setBackground(new Color(26,26,26,177));

        //JigsawPart create
        jigsawPart = new JigsawParts[]{
//                new JigsawParts("Image/jigsaw1.png", this,670, 500, 86,70),
//                new JigsawParts("Image/jigsaw2.png", this,550, 550, 115,70),
//                new JigsawParts("Image/jigsaw3.png",this,690, 600, 442,70),
//                new JigsawParts("Image/jigsaw4.png", this,600, 500, 86,541),
//                new JigsawParts("Image/jigsaw5.png", this,500, 700, 171,456),
//                new JigsawParts("Image/jigsaw6.png", this, 690, 660, 312,476),
//                new JigsawParts("Image/jigsaw7.png", this, 570, 520, 86,727),
//                new JigsawParts("Image/jigsaw8.png", this, 670, 590, 299,659),
//                new JigsawParts("Image/jigsaw9.png", this, 490, 510, 363,872),
//                new JigsawParts("Image/jigsaw10.png", this, 500, 600, 616,569)
                new JigsawParts("Image/jigsaw1.png", this, 800, 450, 86, 70),
                new JigsawParts("Image/jigsaw2.png", this, 750, 180, 115, 70),
                new JigsawParts("Image/jigsaw3.png", this, 800, 130, 442, 70),

                // กลุ่มตรงกลาง (ซ้อนกันนิดๆ ให้ดูเป็นธรรมชาติ)
                new JigsawParts("Image/jigsaw4.png", this, 750, 40, 86, 541),
                new JigsawParts("Image/jigsaw5.png", this, 1150, 700, 171, 456),
                new JigsawParts("Image/jigsaw6.png", this, 1000, 30, 312, 476),

                // กลุ่มด้านล่าง
                new JigsawParts("Image/jigsaw7.png", this, 1080, 480, 86, 727),
                new JigsawParts("Image/jigsaw8.png", this, 1250, 520, 299, 659),
                new JigsawParts("Image/jigsaw9.png", this, 950,  600, 363, 872),
                new JigsawParts("Image/jigsaw10.png", this, 900, 650, 616, 569)
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
                if (getWidth() > 0 && getHeight() > 0) {
                    sup.setLocation(getWidth() / 2 - (sup.getWidth() / 2), getHeight() / 2 - (sup.getHeight() / 2));
                    exitButton.setLocation((int) (0.05 * getWidth()), (int) (0.05 * getHeight()));
                }
            }
        });
    }

    @Override
    public void doLayout() {
        super.doLayout();
        if (sup != null) {
            sup.setLocation(getWidth() / 2 - sup.getWidth() / 2, getHeight() / 2 - sup.getHeight() / 2);
        }
        if (exitButton != null) {
            exitButton.setLocation((int) (0.05 * getWidth()), (int) (0.05 * getHeight()));
        }
    }

    public void checkWinCondition() {
        boolean allFinished = true;
        for (JigsawParts jp : jigsawPart) {
            if (!jp.isReachTarget()) {
                allFinished = false;
                break;
            }
        }

        if (allFinished) {
            Timer winTimer = new Timer(1000, evt -> {
               if (onWinCallback != null) onWinCallback.run();
               mainGameFrame.closeMinigame();
            });
            winTimer.setRepeats(false);
            winTimer.start();
        }
    }
}
