package Minigame.RotateNarigaPackage;

import ui.MainGameFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class RotateNariga extends JPanel{
    private JPanel nariga, kemSmall, kemBig, btPanel;
    private JButton increaseH, increaseM, closeBtn;
    private boolean finished = false;
    private MainGameFrame mainGameFrame;
    private int[] saveAngles;
    private boolean[] isSolvedState;
    private Runnable onwin;
    private BufferedImage narigaImage, background;
    private ImageIcon long_def, long_hover, short_def, short_hover, exit;

    public  RotateNariga(MainGameFrame mainGameFrame, int[] saveAngles, boolean[] isSolvedState, Runnable onwin){
        try{
            narigaImage = ImageIO.read(getClass().getResource("Image/Nariga.png"));
            background = ImageIO.read(getClass().getResource("Image/background.png"));
            long_def = new ImageIcon(getClass().getResource("Image/Long_default.PNG"));
            long_hover = new ImageIcon(getClass().getResource("Image/Long_hover.PNG"));
            short_def = new ImageIcon(getClass().getResource("Image/Short_default.PNG"));
            short_hover = new ImageIcon(getClass().getResource("Image/Short_hover.PNG"));
            exit = new ImageIcon(getClass().getResource("Image/Exit_minigame_btn.png"));
        } catch(IOException e){
            e.printStackTrace();
            narigaImage = null;
            background = null;
            long_def = null;
            long_hover = null;
            short_def = null;
            short_hover = null;
        }

        this.mainGameFrame = mainGameFrame;
        this.saveAngles = saveAngles;
        this.isSolvedState = isSolvedState;
        this.onwin = onwin;

        this.setLayout(new BorderLayout());

        btPanel = new JPanel();
        increaseM = new JButton(long_def);
        increaseH = new JButton(short_def);
        closeBtn = new JButton(exit);
        setButton(increaseH, short_hover);
        setButton(increaseM, long_hover);
        setButton(closeBtn, exit);

        closeBtn.addActionListener(e -> {
            saveAngles[0] = ((KemImagePanel) kemSmall).getAngle();
            saveAngles[1] = ((KemImagePanel) kemBig).getAngle();
            mainGameFrame.closeMinigame();
        });

        nariga = new JPanel(){
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                if(narigaImage != null){
                    g2d.drawImage(narigaImage, (getWidth() - getHeight()) / 2, 0, getHeight(), getHeight(), this);
                } else{
                    g2d.setColor(new Color(26,26,26,255));
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
                g2d.dispose();
            }
        };
        nariga.setOpaque(false);
        kemSmall = new KemImagePanel("Image/kemSmall.png", 30,saveAngles[0]);
        kemBig = new KemImagePanel("Image/kemBig.png", 6, saveAngles[1]);

        increaseH.addMouseListener(new RotateNarigaHandler(this, 30, kemSmall));
        increaseM.addMouseListener(new RotateNarigaHandler(this,6, kemBig));


        btPanel.setSize(short_def.getIconWidth() * 2 + 80, short_def.getIconHeight());
        closeBtn.setSize(exit.getIconWidth(), exit.getIconHeight());
        btPanel.setOpaque(false);
        btPanel.setLayout(new GridLayout(1,2,80,0));
        nariga.setLayout(null);

        btPanel.add(increaseH); btPanel.add(increaseM);
        nariga.add(btPanel); nariga.add(closeBtn);
        nariga.add(kemSmall); nariga.add(kemBig);
        this.add(nariga, BorderLayout.CENTER);

        nariga.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                kemSmall.setLocation(nariga.getWidth() / 2 - (kemSmall.getWidth() / 2), nariga.getHeight() / 2 - (kemSmall.getHeight() / 2));
                kemBig.setLocation(nariga.getWidth() / 2 - (kemBig.getWidth() / 2), nariga.getHeight() / 2 - (kemBig.getHeight() / 2));

                btPanel.setLocation((nariga.getWidth() / 2 - (btPanel.getWidth() / 2)), (int) (0.78 * nariga.getHeight()));
                closeBtn.setLocation((int) (0.05 * nariga.getWidth()), (int) (0.05 * nariga.getHeight()));
            }
        });
        System.out.println(increaseH.getWidth());
        System.out.println(increaseM.getHeight());

    }
    public boolean isFinished(){
        return finished;
    }
    public int[] getKemAngle(){
        int kemAngle[] = {((KemImagePanel) kemSmall).getAngle(), ((KemImagePanel) kemBig).getAngle()};
        return  kemAngle;
    }

    public void winClose() {
        finished = true;
        isSolvedState[0] = true;
        System.out.println("เล่นผ่านแล้วจ้าาา");

        if (onwin != null) {
            onwin.run();
        }

        saveAngles[0] = ((KemImagePanel) kemSmall).getAngle();
        saveAngles[1] = ((KemImagePanel) kemBig).getAngle();

        mainGameFrame.closeMinigame();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        if(background != null){
            g2.drawImage(background, 0, 0,getWidth(), getHeight(),this);
        } else{
            g2.setColor(new Color(26,26,26,100));
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
        g2.dispose();
    }
    public void setButton(JButton button, ImageIcon hover){
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if(hover != null){
            button.setRolloverIcon(hover);
        }
    }
}
