package Minigame.FinalBossFightPackage;

public class Main {
    static void main() {
        System.setProperty("sun.java2d.uiScale", "1.0");
        FinalBossFight fbf = new FinalBossFight();
        Thread t = new Thread(fbf);
        t.start();
    }
}
