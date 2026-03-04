package system;
import javax.sound.sampled.*;
import java.io.File;
public class AudioManager {
    public static int bgmVolume = 50;
    public static int sfxVolume = 50;
    private static Clip bgMusic;

    // เพิ่มตัวแปรเก็บค่า Offset ล่าสุดของ BGM เพื่อให้เวลาเลื่อน Slider แล้วเสียงยังสมดุลอยู่
    private static float currentBgmOffset = 0.0f;

    public static void setBgmVolume(int volume) {
        bgmVolume = volume;
        // เรียกใช้เมธอดคำนวณเสียงโดยใช้ Offset ล่าสุดที่ตั้งไว้
        applyVolume(bgMusic, bgmVolume, currentBgmOffset);
    }

    public static void setSfxVolume(int volume) {
        sfxVolume = volume;
    }

    // แก้ไขให้รับ offsetDB: + สำหรับเร่งเสียงที่เบา, - สำหรับลดเสียงที่ดังเกิน
    public static void playMusic(String path, float offsetDB) {
        // ป้องกันเพลงเล่นซ้อนถ้าเป็นไฟล์เดิม
        if (bgMusic != null && bgMusic.isRunning()) return;

        try {
            File musicPath = new File(path);
            if (musicPath.exists()) {
                currentBgmOffset = offsetDB; // จำค่า offset ของเพลงนี้ไว้
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                bgMusic = AudioSystem.getClip();
                bgMusic.open(audioInput);
                bgMusic.loop(Clip.LOOP_CONTINUOUSLY);

                applyVolume(bgMusic, bgmVolume, offsetDB);
                bgMusic.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ปรับปรุง SFX ให้รับค่า Offset เฉพาะไฟล์ได้เช่นกัน
    public static void playSFX(String path, float offsetDB) {
        try {
            File sfxFile = new File(path);
            if (sfxFile.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(sfxFile);
                Clip sfxClip = AudioSystem.getClip();
                sfxClip.open(audioInput);

                applyVolume(sfxClip, sfxVolume, offsetDB);
                sfxClip.start();

                sfxClip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        sfxClip.close();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // เมธอดกลางที่ใช้คำนวณระดับเสียง (ใช้ร่วมกันทั้ง BGM และ SFX)
    private static void applyVolume(Clip clip, int volume, float offsetDB) {
        if (clip != null && clip.isOpen()) {
            try {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

                // 1. คำนวณ dB จากค่า 0-100
                float baseDB = (float) (Math.log(volume != 0 ? volume / 100.0 : 0.0001) / Math.log(10.0) * 20.0);

                // 2. บวกค่า Offset เฉพาะไฟล์เข้าไป
                float finalDB = baseDB + offsetDB;

                // 3. ป้องกันค่าเกินขีดจำกัดของ Hardware
                if (finalDB < gainControl.getMinimum()) finalDB = gainControl.getMinimum();
                if (finalDB > gainControl.getMaximum()) finalDB = gainControl.getMaximum();

                gainControl.setValue(finalDB);
            } catch (IllegalArgumentException e) {
                // กรณี Hardware ไม่รองรับ MASTER_GAIN
            }
        }
    }

    public static void stopMusic() {
        if (bgMusic != null) {
            bgMusic.stop();
            bgMusic.setFramePosition(0);
        }
    }
}