package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Tutorial extends JPanel {
    private MainGameFrame parent;
    private BufferedImage[] tutorialImage;
    private int currentImage = 0;
    private JButton btnLeft, btnRight, btnClose;
    private ImageIcon iconLeft, iconRight, iconClose;
    private Boolean isActive = false;

    public Tutorial(MainGameFrame parent) {
        this.parent = parent;

        tutorialImage = new BufferedImage[4];
        try {
            tutorialImage[0] = ImageIO.read(getClass().getResource("/src/res/Tutorial1.png"));
            tutorialImage[1] = ImageIO.read(getClass().getResource("/src/res/Tutorial2.png"));
            tutorialImage[2] = ImageIO.read(getClass().getResource("/src/res/Tutorial3.png"));
            tutorialImage[3] = ImageIO.read(getClass().getResource("/src/res/Tutorial4.png"));

            iconLeft = new ImageIcon(ImageIO.read(getClass().getResource("/src/res/TutorialBtnLeft.png")));
            iconRight = new ImageIcon(ImageIO.read(getClass().getResource("/src/res/TutorialBtnRight.png")));
//            iconClose = new ImageIcon(ImageIO.read(getClass().getResource("")));
        }
        catch (IOException e) {
            System.out.println("ระบบ : โหลดรูปไม่ได้จ้า");
            e.printStackTrace();
        }

        // Button Left
        btnLeft = new JButton(iconLeft);


        this.setLayout(null);
        this.setOpaque(false);
        this.setVisible(false);
    }
}
