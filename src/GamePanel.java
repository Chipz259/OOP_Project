import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.KeyAdapter;
public class GamePanel extends JPanel implements Runnable {
    private Thread gameThread;
    private boolean isRunning;
    private int fps;
    private Player mainPlayer;
    private SceneManager sceneManager;
    private QTEManager qteManager;
    private MouseAdapter mouseHandler;
    private KeyAdapter keyHandler;
    KeyHandler keyH = new KeyHandler();
    public GamePanel() {
        this.setPreferredSize(new Dimension(1920, 1080));
        this.setBackground(Color.BLACK);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        mainPlayer = new Player("player", 350, 550, 128, 128);
        sceneManager = new SceneManager();
        Scene scene1 = new Scene("scene1");

        sceneManager.addScene(scene1);
        sceneManager.loadScene("scene1");
    }
    public void startGameThread() {
        gameThread = new Thread(this);
        isRunning = true;
        gameThread.start();
    }
    public void stopGameThread() {

    }
    public void run() {
        double drawInterval = 1000000000 / 60;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null && isRunning) {
            update();
            repaint();
            try {
                double remainingTime = (nextDrawTime - System.nanoTime()) / 1000000;
                if(remainingTime < 0) remainingTime = 0;
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void update() {
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
        if (mainPlayer != null) {
            mainPlayer.render(g2d);
        }
    }
}
