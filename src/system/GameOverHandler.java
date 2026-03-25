package system;

import ui.GameOverPanel;
import java.awt.event.*;

public class GameOverHandler implements ActionListener, MouseListener {
    private GameOverPanel gui;

    public GameOverHandler(GameOverPanel gui) {
        this.gui = gui;
        if (gui.getParentFrame() != null) {
            // ใช้เมธอดเดิมที่น้องมีใน MainGameFrame เพื่อ Reset เกม
//            gui.getParentFrame().transitionToGame();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        gui.setVisible(false);

        if (gui.getParentFrame() != null) {
            gui.getParentFrame().retryFromGameOver();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        gui.setCurrentBG(gui.getBgLight());
        gui.repaint();

    }

    @Override
    public void mouseExited(MouseEvent e) {
        gui.setCurrentBG(gui.getBgNormal());
        gui.repaint();
    }
}
