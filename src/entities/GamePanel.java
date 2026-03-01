package entities;
import entities.Item;
import entities.GameObject;

import scenes.SceneManager;
import system.KeyHandler;
// import system.QTEManager;

import javax.swing.*;
import java.awt.*;
import java.awt.Cursor;
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
        this.setDoubleBuffered(true); // ลดอาการภาพกะพริบ
        this.addKeyListener(keyH);
        this.setFocusable(true);

        mainPlayer = new Player("player", 350, 550, 128, 128);
        sceneManager = new SceneManager();
        Item Candle = new Item("candle", 300, 400, 50, 50, "เทียนไข", "เทียนไขที่ยังไม่จุด", "Candle.png", "CandleStroke.png");

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (sceneManager.getCurrentScene() != null) {
                    for (GameObject obj : sceneManager.getCurrentScene().getObjectsInScene()) {
                        if (obj.getHitbox().contains(e.getPoint()) && obj instanceof Interactable && ((Interactable) obj).isInteractable()) {
                            ((Interactable) obj).onInteract(mainPlayer);
                            setCursor(Cursor.getDefaultCursor());
                            break;
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

        int speed = 5;
        if (keyH.left) {
            if (mainPlayer.getX() - speed >= 0) {
                mainPlayer.setX(mainPlayer.getX() - speed);
            } else {
                mainPlayer.setX(0);
            }
        }
        if (keyH.right) {
            if (mainPlayer.getX() + mainPlayer.getWidth() + speed <= this.getWidth()) {
                mainPlayer.setX(mainPlayer.getX() + speed);
            } else {
                mainPlayer.setX(this.getWidth() - mainPlayer.getWidth());
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

            if (mainPlayer.getSprite() == null){
                g2d.setColor(Color.WHITE);
                g2d.fillRect(mainPlayer.getX(), mainPlayer.getY(), mainPlayer.getWidth(), mainPlayer.getHeight());
            }
        }

    }
}
