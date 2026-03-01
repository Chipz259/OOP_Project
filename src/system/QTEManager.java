package system;

import java.awt.*;
import java.awt.event.KeyEvent;
public class QTEManager {
    private boolean isQteActive;
    private String qteType;
    private int timeLimitMs;
    private int currentProgress;
    private int targetProgress;
    private String targetSequence;
    private String currentInputSequence;
    public QTEManager() {

    }
    public boolean isQteActive() {
        return true; //ใส่ไว้ก่อน กัน error
    }
    public void setQteActive(boolean isQteActive) {

    }
    public String getQteType() {
        return "0"; //ใส่ไว้ก่อน กัน error
    }
    public void setQteType(String qteType) {

    }
    public int getTimeLimitMs() {
        return 0; //ใส่ไว้ก่อน กัน error
    }
    public void setTimeLimitMs(int timeLimitMs) {

    }
    public int getCurrentProgress() {
        return 0; //ใส่ไว้ก่อน กัน error
    }
    public void setCurrentProgress(int currentProgress) {

    }
    public int getTargetProgress() {
        return 0; //ใส่ไว้ก่อน กัน error
    }
    public void setTargetProgress(int targetProgress) {

    }
    public int getTargetSequence() {
        return 0; //ใส่ไว้ก่อน กัน error
    }
    public void setTargetSequence(String targetSequence) {

    }
    public int getCurrentInputSequence() {
        return 0; //ใส่ไว้ก่อน กัน error
    }
    public void setCurrentInputSequence(String currentInputSequence) {

    }
    public void startMashQTE(int targetClicks, int timeLimitMs) {

    }
    public void startSequenceQTE(String targetSeq, int timeLimitMs) {

    }
    public void handleKeyPress(KeyEvent e) {

    }
    public void updateTimer() {

    }
    public void renderQTEUI(Graphics2D g2d) {

    }
    public void resolveQTE(boolean isSuccess) {

    }
}
