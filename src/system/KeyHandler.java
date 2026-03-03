package system;

import scenes.SceneQTE_Choke;
import scenes.SceneManager;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class KeyHandler implements KeyListener {
    public boolean left, right;
    private SceneManager sceneManager;

    public void setSceneManager(SceneManager sm) {
        this.sceneManager = sm;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (sceneManager != null && sceneManager.getCurrentScene() instanceof SceneQTE_Choke) {
            if (code == KeyEvent.VK_E) {
                SceneQTE_Choke qteScene = (SceneQTE_Choke) sceneManager.getCurrentScene();
                qteScene.registerClick();
            }
            return;
        }
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
