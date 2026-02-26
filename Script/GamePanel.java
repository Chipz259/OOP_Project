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
    public GamePanel() {
        this.setPreferredSize(new Dimension(800, 600));
        this.setBackground(Color.BLACK);
    }
    public void startGameThread() {

    }
    public void stopGameThread() {

    }
    public void run() {

    }
    public void update() {

    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillRect(350, 250, 50, 50);
    }
}
