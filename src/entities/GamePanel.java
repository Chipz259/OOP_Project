package entities;

import scenes.SceneQTE_Choke;
import scenes.SceneManager;
import system.FadeTransition;
import system.KeyHandler;
import system.MouseHandler;
import ui.MainGameFrame;
import ui.SettingPanel;
import ui.DiaryUi;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
// import java.awt.event.KeyAdapter;

public class GamePanel extends JPanel implements Runnable {
    private Thread gameThread;
    private boolean isRunning;
    // private int fps;
    public Player mainPlayer;
    public SceneManager sceneManager;
    private Inventory inventory;
    public static Font customFont;
    // private MouseAdapter mouseHandler;
    private KeyHandler keyH;
    private SettingPanel settingPanel;
    private JLayeredPane layeredPane;
    private MainGameFrame parentFrame;
    private JButton btnSetting;
    private ImageIcon settingIcon, settingHoverIcon;

    public GameObject targetItem = null;
    public FadeTransition fadeTransition;

    public GamePanel(MainGameFrame parentFrame, FadeTransition fadeTransition) {
        this.parentFrame = parentFrame;
        this.fadeTransition = fadeTransition;

        this.setPreferredSize(new Dimension(1920, 1080));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setLayout(null);

        keyH = new KeyHandler();
        this.addKeyListener(keyH);

        MouseHandler mouseH = new MouseHandler(this);
        this.addMouseListener(mouseH);
        this.addMouseMotionListener(mouseH);

        settingIcon = new ImageIcon(new ImageIcon("src/res/GamePanelNormalBtnSetting.png").getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH));
        settingHoverIcon = new ImageIcon(new ImageIcon("src/res/GamePanelHoverBtnSetting.png").getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH));
        btnSetting = new JButton(settingIcon);
        btnSetting.setRolloverIcon(settingHoverIcon);
        btnSetting.setBorderPainted(false);
        btnSetting.setContentAreaFilled(false);
        btnSetting.setFocusPainted(false);
        btnSetting.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSetting.setBounds(1780, 20, 120, 120);
        btnSetting.addActionListener(e -> {
            stopPlayerMovement();
            parentFrame.toggleSetting(true, true); // เปิดโหมดในเกม (มีปุ่ม Return)
        });
        this.add(btnSetting);
        btnSetting.setFocusable(false);

        loadCustomFont();
        inventory = new Inventory("slots.png");
        mainPlayer = new Player("player", 1650, 550, 150, 313);
        sceneManager = new SceneManager(mainPlayer);
        keyH.setSceneManager(sceneManager);
        sceneManager.setFadeTransition(this.fadeTransition);
        sceneManager.setGamePanel(this);
    }

    public void loadCustomFont(){
        try {
            InputStream is = getClass().getResourceAsStream("/res/Font/DSNSM__.TTF");
            customFont = Font.createFont(Font.TRUETYPE_FONT, is);
        }
        catch (Exception e) {
            System.err.println("ระบบ: โหลดฟอนต์ไม่ได้");
            e.printStackTrace();
            customFont = new Font("Arial", Font.PLAIN, 24);
        }
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        isRunning = true;
        gameThread.start();
        this.requestFocusInWindow();
    }

    public void stopGameThread() {
        isRunning = false;
        gameThread = null;
    }

    public void run() {
        // 1 วินาที มี 1,000,000,000 นาโนวินาที / FPS
        double drawInterval = 1000000000 / 60;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null && isRunning) {
            update();
            repaint();
            try {
                double remainingTime = (nextDrawTime - System.nanoTime()) / 1000000;
                if (remainingTime < 0)
                    remainingTime = 0;
                //สั่ง sleep ถ้าทำงานเสร็จก่อนเวลา ถ้าไม่ก็จะทำงานรอบต่อไปทันที
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        sceneManager.update();

        boolean isQTE = sceneManager.getCurrentScene() instanceof SceneQTE_Choke;
        boolean isTalking = sceneManager.getOverlay() != null && sceneManager.getOverlay().isActive();
        boolean isFading = fadeTransition != null && fadeTransition.isFading();
        if (isQTE || isTalking || isFading) {
            if (btnSetting.isVisible()) btnSetting.setVisible(false);
        } else {
            if (!btnSetting.isVisible()) btnSetting.setVisible(true);
        }

        if (sceneManager.getOverlay() != null && sceneManager.getOverlay().isActive()){
            if (mainPlayer != null) {
                mainPlayer.setMoving(false);
            }
            return;
        }

        if (fadeTransition == null || !fadeTransition.isFading()) {
            if (mainPlayer != null) {
                mainPlayer.update();
            }

            int speed = 100;
            boolean isWalking = false;
            boolean isFacingLeft = false;

            if (keyH.left || keyH.right) {
                targetItem = null;
            }
            if (targetItem != null) {
                int playerCenter = mainPlayer.getX() + (mainPlayer.getWidth() / 2);
                int targetCenter = targetItem.getX() + (targetItem.getWidth() / 2);
                int distance = Math.abs(playerCenter - targetCenter);
                if (distance < 150) {
                    if (targetItem instanceof Interactable) {
                        ((Interactable) targetItem).onInteract(mainPlayer);
                    }
                    targetItem = null;
                } else {
                    isWalking = true;
                    if (playerCenter < targetCenter) {
                        if (mainPlayer.getX() + mainPlayer.getWidth() + speed <= this.getWidth()) {
                            mainPlayer.setX(mainPlayer.getX() + speed);
                        }
                        mainPlayer.setFacingLeft(false);
                    } else {
                        if (mainPlayer.getX() - speed >= 0) {
                            mainPlayer.setX(mainPlayer.getX() - speed);
                        }
                        mainPlayer.setFacingLeft(true);
                    }
                }
            }
            else {
                if (keyH.left) {
                    if (mainPlayer.getX() - speed >= 0) {
                        mainPlayer.setX(mainPlayer.getX() - speed);
                    } else {
                        mainPlayer.setX(0);
                    }
                    isWalking = true;
                    mainPlayer.setFacingLeft(true);
                }
                if (keyH.right) {
                    if (mainPlayer.getX() + mainPlayer.getWidth() + speed <= this.getWidth()) {
                        mainPlayer.setX(mainPlayer.getX() + speed);
                    } else {
                        mainPlayer.setX(this.getWidth() - mainPlayer.getWidth());
                    }
                    isWalking = true;
                    mainPlayer.setFacingLeft(false);
                }
            }

            if (mainPlayer != null) {
                mainPlayer.setMoving(isWalking);
            }

            if (sceneManager.getCurrentScene() != null && mainPlayer != null) {
                for (GameObject obj : sceneManager.getCurrentScene().getObjectsInScene()) {
                    if (obj instanceof scenes.Door) {

                        int playerCenter = mainPlayer.getX() + (mainPlayer.getWidth() / 2);
                        int objCenter = obj.getX() + (obj.getWidth() / 2);
                        int distance = Math.abs(playerCenter - objCenter);

                        if (distance < 150) {
                            obj.setVisible(true);
                        } else {
                            obj.setVisible(false);
                        }
                    }
                }
            }
        } else {
            // ถ้ากำลังเปลี่ยนฉากอยู่ บังคับตัวละครหยุดเดิน
            if (mainPlayer != null) {
                mainPlayer.setMoving(false);
            }

            if (sceneManager.getCurrentScene() != null) {
                for (GameObject obj : sceneManager.getCurrentScene().getObjectsInScene()) {
                    if (obj instanceof scenes.Door) {
                        obj.setVisible(false);
                    }
                }
            }
        }
    }

    public void stopPlayerMovement() {
        keyH.left = false;
        keyH.right = false;

        if (mainPlayer != null) {
            mainPlayer.setMoving(false);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (sceneManager != null) {
            sceneManager.render(g2d);
        }

        boolean isTalk = sceneManager.getOverlay() != null && sceneManager.getOverlay().isActive();


        if (!(sceneManager.getCurrentScene() instanceof SceneQTE_Choke)) {

            if (mainPlayer != null) {
                mainPlayer.render(g2d);

                if (mainPlayer.getInventory() != null && !isTalk) {
                    mainPlayer.getInventory().render(g2d, getWidth(), getHeight());
                }
            }
            if (mainPlayer != null && mainPlayer.getInventory() != null && !isTalk) {
                system.ObjectiveManager.getInstance().draw(g2d, mainPlayer.getInventory());
            }
        }

        if (sceneManager != null && sceneManager.getCurrentScene() != null) {
            for (GameObject obj : sceneManager.getCurrentScene().getObjectsInScene()) {
                if (obj instanceof scenes.Door) {
                    obj.render(g2d);
                }
            }
        }

        if (sceneManager.getOverlay() != null && sceneManager.getOverlay().isActive()) {
            sceneManager.getOverlay().render(g2d, 1920, 1080);
        }

        DiaryUi.getInstance().draw(g2d, getWidth(), getHeight());
    }

    public void triggerDeath() {
        parentFrame.showGameOver(true);
    }
}
