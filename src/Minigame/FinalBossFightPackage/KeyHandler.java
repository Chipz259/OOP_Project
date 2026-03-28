package Minigame.FinalBossFightPackage;

import system.AudioManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyHandler extends KeyAdapter {
    private Stage nowStage;
    private YanKeys[] YanKeysArray;
    private FinalBossFight fbf;
    public KeyHandler(Stage stage, FinalBossFight fbf) {
        this.nowStage = stage;
        YanKeysArray = stage.getYanKeysArray();
        this.fbf = fbf;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_A || key == KeyEvent.VK_D || key ==  KeyEvent.VK_S ||  key == KeyEvent.VK_W) {
            if (!nowStage.isFinished()) {
                if (key == YanKeysArray[nowStage.getCnt()].getKeyButton()) {
                    YanKeysArray[nowStage.getCnt()].setActive();
                    nowStage.updateCNT();
                    AudioManager.playSFX("src/res/sound/MinigameBossCorrect.wav", 0.0f);
                } else {
                    nowStage.setDefault();
                    fbf.decreaseTimer();
                    AudioManager.playSFX("src/res/sound/MinigameBossMiss.wav", 0.0f);
                }

            }
        }
    }
    public void setNewStage(Stage newStage){
        this.nowStage = newStage;
        YanKeysArray = newStage.getYanKeysArray();
    }
}
