package system;

import javax.sound.sampled.*;
import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AudioManager {
    public static int bgmVolume = 50;
    public static int sfxVolume = 50;
    private static Clip bgMusic;
    private static Map<String, Clip> preloadedClips = new HashMap<>();
    private static Map<String, Clip> activeSfxMap = new ConcurrentHashMap<>();
    private static float currentBgmOffset = 0.0f;
    private static String currentBgmPath = "";
    private static long lastPosition = 0;

    public static void setBgmVolume(int volume) {
        bgmVolume = volume;
        if (bgMusic != null && bgMusic.isOpen()) {
            applyVolume(bgMusic, bgmVolume, currentBgmOffset);
        }
    }

    public static void setSfxVolume(int volume) {
        sfxVolume = volume;
        // ปรับเสียง SFX ทุกตัวที่กำลังเล่นอยู่ตาม Slider ทันที
        for (Clip clip : activeSfxMap.values()) {
            if (clip.isOpen()) {
                applyVolume(clip, sfxVolume, 0.0f);
            }
        }
    }

    public static void playMusic(String path, float offsetDB) {
        if (path.equals(currentBgmPath) && bgMusic != null && bgMusic.isRunning()) return;

        try {
            File musicFile = new File(path);
            if (musicFile.exists()) {
                stopMusic();
                currentBgmPath = path;
                currentBgmOffset = offsetDB;
                lastPosition = 0; // รีเซ็ตตำแหน่งใหม่เพราะเป็นการเริ่มเพลงใหม่

                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicFile);
                bgMusic = AudioSystem.getClip();
                bgMusic.open(audioInput);
                bgMusic.loop(Clip.LOOP_CONTINUOUSLY);

                applyVolume(bgMusic, bgmVolume, currentBgmOffset);
                bgMusic.start();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void pauseBGMusic() {
        if (bgMusic != null && bgMusic.isRunning()) {
            lastPosition = bgMusic.getMicrosecondPosition();
            bgMusic.stop();
        }
    }

    public static void resumeBGMusic(String path, float offsetDB) {
        // 1. ถ้าเป็นเพลงเดิมและกำลังเล่นอยู่ -> ไม่ต้องทำอะไรเลย (เล่นต่อเนื่องไป)
        if (path.equals(currentBgmPath) && bgMusic != null && bgMusic.isRunning()) {
            return;
        }

        // 2. ถ้าเป็นเพลงเดิมแต่ถูกหยุดไว้ (เช่น กลับมาจากหน้า Setting) -> Resume ต่อจากที่ค้างไว้
        if (path.equals(currentBgmPath) && bgMusic != null && !bgMusic.isRunning()) {
            bgMusic.setMicrosecondPosition(lastPosition);
            applyVolume(bgMusic, bgmVolume, currentBgmOffset);
            bgMusic.start();
            return;
        }

        // 3. ถ้าเป็นเพลงใหม่ (Path ไม่ตรงกับของเดิม) -> เริ่มเล่นเพลงใหม่ทันที
        try {
            File musicFile = new File(path);
            if (musicFile.exists()) {
                stopMusic(); // เคลียร์ตัวเก่าทิ้ง

                currentBgmPath = path;
                currentBgmOffset = offsetDB;
                lastPosition = 0;

                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicFile);
                bgMusic = AudioSystem.getClip();
                bgMusic.open(audioInput);
                bgMusic.loop(Clip.LOOP_CONTINUOUSLY);

                applyVolume(bgMusic, bgmVolume, currentBgmOffset);
                bgMusic.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playSFX(String path, float offsetDB) {
        try {
            File sfxFile = new File(path);
            if (sfxFile.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(sfxFile);
                Clip sfxClip = AudioSystem.getClip();
                sfxClip.open(audioInput);
                applyVolume(sfxClip, sfxVolume, offsetDB);

                activeSfxMap.put(path, sfxClip);
                sfxClip.start();

                sfxClip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        sfxClip.close();
                        activeSfxMap.remove(path);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopMusic() {
        if (bgMusic != null) {
            bgMusic.stop();
            bgMusic.close();
            bgMusic = null;
            currentBgmPath = "";
            lastPosition = 0;
        }
    }

    /**
     * หยุด SFX เฉพาะไฟล์ที่ระบุ
     */
    public static void stopSFX(String path) {
        Clip clip = activeSfxMap.get(path);
        if (clip != null) {
            clip.stop();
            clip.close();
            activeSfxMap.remove(path);
        }
    }

    /**
     * หยุด SFX ทั้งหมด (ใช้ตอน Reset เกม หรือเปลี่ยนฉากใหญ่)
     */
    public static void stopAllSFX() {
        for (String path : activeSfxMap.keySet()) {
            stopSFX(path);
        }
        activeSfxMap.clear();
    }

    public static void preloadSFX(String path) {
        try {
            File file = new File(path);
            if (file.exists() && !preloadedClips.containsKey(path)) {
                AudioInputStream ais = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                preloadedClips.put(path, clip);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void playPreloadedSFX(String path, float offsetDB) {
        Clip clip = preloadedClips.get(path);
        if (clip != null) {
            clip.setFramePosition(0);
            applyVolume(clip, sfxVolume, offsetDB);
            clip.start();

            // เพิ่มลงใน activeSfxMap เพื่อให้ปรับ Volume ตาม Slider ได้
            activeSfxMap.put(path, clip);
        } else {
            playSFX(path, offsetDB);
        }
    }

    private static void applyVolume(Clip clip, int volume, float offsetDB) {
        if (clip == null || !clip.isOpen()) return;
        try {
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float baseDB = (float) (Math.log10(Math.max(volume, 0.0001) / 100.0) * 20.0);
                float finalDB = baseDB + offsetDB;
                finalDB = Math.max(gainControl.getMinimum(), Math.min(finalDB, gainControl.getMaximum()));
                gainControl.setValue(finalDB);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static boolean isSfxRunning() {
        return !activeSfxMap.isEmpty();
    }
}