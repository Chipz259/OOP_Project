package Minigame.FinalBossFightPackage;

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
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == YanKeysArray[nowStage.getCnt()].getKeyButton()){
            YanKeysArray[nowStage.getCnt()].setAsActive();
            nowStage.updateCNT();
        } else{
            for(YanKeys yanKeys : nowStage.getYanKeysArray()){
                yanKeys.setAsUnactive();
            }
            nowStage.resetCNT();
            fbf.increaseTimerStep();
        }
    }
    public void setNewStage(Stage newStage){
        this.nowStage = newStage;
        YanKeysArray = newStage.getYanKeysArray();
    }
}
