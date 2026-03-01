package entities;

import entities.Item;
import entities.GameObject;

import scenes.SceneManager;
import system.KeyHandler;
// import system.QTEManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;;
// import java.awt.event.KeyAdapter;

public class GamePanel extends JPanel implements Runnable {
    private Thread gameThread;
    private boolean isRunning;
    // private int fps;
    private Player mainPlayer;
    private SceneManager sceneManager;
    // private QTEManager qteManager;
    // private MouseAdapter mouseHandler;
    // private KeyAdapter keyHandler;

    KeyHandler keyH = new KeyHandler();

    public GamePanel() {
        this.setPreferredSize(new Dimension(1920, 1080));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        mainPlayer = new Player("player", 1650, 550, 250, 250);
        sceneManager = new SceneManager();

        Item Candle = new Item("candle", 300, 400, 50, 50, "เทียนไข", "เทียนไขที่ยังไม่จุด", "Candle.png",
                "CandleStroke.png");

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (sceneManager.isTransition()) {
                    return;
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
                                break;
                            } else {
                                // ถ้าคลิกไกลเกินจะแสดงการแจ้งเตือน
                                System.out.println("ระบบ: อยู่ไกลเกินไอเวร");
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
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        isRunning = true;
        gameThread.start();
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
        if (!sceneManager.isTransition()) {
            if (mainPlayer != null) {
                mainPlayer.update();
            }

            int speed = 5;
            boolean isWalking = false;
            boolean isFacingLeft = false;

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

            if (mainPlayer != null) {
                mainPlayer.setMoving(isWalking);
            }

            if (sceneManager.getCurrentScene() != null && mainPlayer != null) {
                for (GameObject obj : sceneManager.getCurrentScene().getObjectsInScene()) {
                    if (obj instanceof scenes.Door) {

                        int playerCenter = mainPlayer.getX() + (mainPlayer.getWidth() / 2);
                        int objCenter = obj.getX() + (obj.getWidth() / 2);
                        int distance = Math.abs(playerCenter - objCenter);

                        if (distance < 250) {
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
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        sceneManager.render(g2d);

        if (mainPlayer != null) {
            mainPlayer.render(g2d);

            if (mainPlayer.getSprite() == null) {
                g2d.setColor(Color.WHITE);
                g2d.fillRect(mainPlayer.getX(), mainPlayer.getY(), mainPlayer.getWidth(), mainPlayer.getHeight());
            }
        }

    }
}
