package ui;

public class AudioManager {
    public static int bgmVolume = 50;
    public static int sfxVolume = 50;

    public static void setBgmVolume(int volume) {
        bgmVolume = volume;
        System.out.println("BGM Volume changed to: " + bgmVolume);
    }

    public static void setSfxVolume(int volume) {
        sfxVolume = volume;
        System.out.println("SFX Volume changed to: " + sfxVolume);
    }
}
