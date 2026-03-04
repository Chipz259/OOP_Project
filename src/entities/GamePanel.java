package entities;

import entities.Item;
import entities.GameObject;
import scenes.SceneQTE_Choke;
import system.AudioManager;
import system.FadeTransition;
import scenes.SceneManager;
import system.KeyHandler;
import ui.MainGameFrame;
import ui.SettingPanel;
// import system.QTEManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
// import java.awt.event.KeyAdapter;

public class GamePanel extends JPanel implements Runnable {
    private Thread gameThread;
    private boolean isRunning;
    // private int fps;
    private Player mainPlayer;
    private SceneManager sceneManager;
    private Inventory inventory;
    public static Font customFont;
    // private QTEManager qetManager;
    // private MouseAdapter mouseHandler;
    // private KeyAdapter keyHandler;
    private SettingPanel settingPanel;
    private JLayeredPane layeredPane;
    private MainGameFrame parentFrame;
    private JButton btnSetting;
    private ImageIcon settingIcon, settingHoverIcon;

    KeyHandler keyH = new KeyHandler();
    private GameObject targetItem = null;
    private FadeTransition fadeTransition;

    public GamePanel(MainGameFrame parentFrame, FadeTransition fadeTransition) {
        this.parentFrame = parentFrame;
        this.fadeTransition = fadeTransition;
        this.setPreferredSize(new Dimension(1920, 1080));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.setLayout(null);

        settingIcon = new ImageIcon(new ImageIcon("src/res/GamePanelNormalBtnSetting.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        settingHoverIcon = new ImageIcon(new ImageIcon("src/res/GamePanelHoverBtnSetting.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        btnSetting = new JButton(settingIcon);
        btnSetting.setRolloverIcon(settingHoverIcon);
        btnSetting.setBorderPainted(false);
        btnSetting.setContentAreaFilled(false);
        btnSetting.setFocusPainted(false);
        btnSetting.setCursor(new Cursor(Cursor.HAND_CURSOR));

        inventory = new Inventory("slots.png");
        mainPlayer = new Player("player", 1650, 550, 150, 313);
        sceneManager = new SceneManager(mainPlayer);
        keyH.setSceneManager(sceneManager);
        sceneManager.setFadeTransition(this.fadeTransition);
        btnSetting.setBounds(1810, 30, 80, 80);
        loadCustomFont();

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (fadeTransition != null && fadeTransition.isFading()) {
                    return;
                }
                if (mainPlayer != null && mainPlayer.getInventory() != null) {
                    boolean clickedInventory = mainPlayer.getInventory().handleClick(e.getX(), e.getY(), getWidth(), getHeight());
                    if (clickedInventory) {
                        return; // คลิกโดน = หยุดการทำงาน
                    }
                }

                if (sceneManager.getCurrentScene() != null) {
                    for (GameObject obj : sceneManager.getCurrentScene().getObjectsInScene()) {

                        if (obj.getHitbox().contains(e.getPoint()) && obj.isVisible() && obj instanceof Interactable
                                && ((Interactable) obj).isInteractable()) {

                            int playerCenter = mainPlayer.getX() + (mainPlayer.getWidth() / 2);
                            int objCenter = obj.getX() + (obj.getWidth() / 2);

                            int distance = Math.abs(playerCenter - objCenter);

                            if (distance < 250) {
                                ((Interactable) obj).onInteract(mainPlayer);
                                targetItem = null;
                                break;
                            } else {
                                // ถ้าคลิกไกลเกินจะแสดงการแจ้งเตือน
                                System.out.println("ระบบ: กำลังเดินไปเก็บ " + obj);
                                targetItem = obj;
                                break;
                            }
                        }
                    }
                }
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                boolean isHoveringAnyItem = false;

                if (sceneManager != null && sceneManager.getCurrentScene() != null) {

                    for (GameObject obj : sceneManager.getCurrentScene().getObjectsInScene()) {

                        if (obj instanceof Item) {
                            Item item = (Item) obj;

                            if (item.getHitbox().contains(e.getPoint()) && item.isInteractable()) {
                                item.setHovered(true);
                                isHoveringAnyItem = true;
                            } else {
                                item.setHovered(false);
                            }
                        }
                    }
                }

                if (isHoveringAnyItem) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                } else {
                    setCursor(Cursor.getDefaultCursor());
                }
            }
        });

        btnSetting.addActionListener(e -> {
            parentFrame.toggleSetting(true);
        });
        this.add(btnSetting);
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
        AudioManager.playMusic("src/res/sound/UIABg.wav", 1.0f);
    }

    public void stopGameThread() {
        isRunning = false;
        gameThread = null;
    }

    public void run() {
        double drawInterval = 1000000000 / 60;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null && isRunning) {
            update();
            repaint();
            try {
                double remainingTime = (nextDrawTime - System.nanoTime()) / 1000000;
                if (remainingTime < 0)
                    remainingTime = 0;
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        sceneManager.update();

        if (keyH.esc) {
            keyH.esc = false; // รีเซ็ตค่าเพื่อไม่ให้เมนูเด้งรัวๆ
            parentFrame.toggleSetting(true); // เรียกเปิดหน้า Setting
        }

        if (fadeTransition == null || !fadeTransition.isFading()) {
            if (mainPlayer != null) {
                mainPlayer.update();
            }

            int speed = 8;
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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (sceneManager != null) {
            sceneManager.render(g2d);
        }
        if (!(sceneManager.getCurrentScene() instanceof SceneQTE_Choke)) {

            if (mainPlayer != null) {
                mainPlayer.render(g2d);

                if (mainPlayer.getInventory() != null) {
                    mainPlayer.getInventory().render(g2d, getWidth(), getHeight());
                }
            }
        }

    }
}
