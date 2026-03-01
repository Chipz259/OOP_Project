package system;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class KeyHandler implements KeyListener {
    public boolean left, right;

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_A) {
            left = true;
        }
        if(code == KeyEvent.VK_D) {
            right = true;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_A) {
            left = false;
        }
        if(code == KeyEvent.VK_D) {
            right = false;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
}
