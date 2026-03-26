package Minigame.UnlockBoxPackage;

public class Main {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "1.0");
        UnlockBox ub = new UnlockBox();
        Thread t = new Thread(ub);
        t.start();
    }
}
