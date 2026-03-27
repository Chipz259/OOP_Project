package system;

import javax.sound.sampled.*;
import java.io.File;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class AudioManager {
    public static int bgmVolume = 50;
    public static int sfxVolume = 50;

    private static Clip bgMusic;
    private static Map<String, Clip> preloadedClips = new HashMap<>();
    // ใช้เก็บ Clip SFX ที่กำลังเล่นอยู่ เพื่อให้ปรับเสียงแบบ Real-time ได้ถ้าต้องการ
    private static List<Clip> activeSfx = new CopyOnWriteArrayList<>();

    // หัวใจสำคัญ: เก็บ Offset ล่าสุดของเพลง BGM ที่กำลังเล่นอยู่
    private static float currentBgmOffset = 0.0f;

    /**
     * ใช้สำหรับ Slider ปรับเสียงเพลง Background
     */
    public static void setBgmVolume(int volume) {
        bgmVolume = volume;
        if (bgMusic != null && bgMusic.isOpen()) {
            // ต้องส่ง currentBgmOffset เข้าไปด้วยเสมอ เพื่อรักษาความสมดุล (-16.0f)
            applyVolume(bgMusic, bgmVolume, currentBgmOffset);
        }
    }

    /**
     * ใช้สำหรับ Slider ปรับเสียง Effect
     */
    public static void setSfxVolume(int volume) {
        sfxVolume = volume;
        // ปรับเสียง SFX ทุกตัวที่ยังเล่นไม่จบตาม Slider ทันที
        for (Clip clip : activeSfx) {
            if (clip.isOpen()) {
                // สำหรับ SFX ปกติ offset มักเป็น 0 หรือค่าเฉพาะตัว
                // ในที่นี้ถ้าไม่ได้เก็บแยก จะใช้ base volume ไปก่อน
                applyVolume(clip, sfxVolume, 0.0f);
            }
        }
    }

    public static void playMusic(String path, float offsetDB) {
        // ถ้าเป็นเพลงเดิมที่กำลังเล่นอยู่ ไม่ต้องเริ่มใหม่ แค่ปรับ Volume
        if (bgMusic != null && bgMusic.isRunning()) {
            // ถ้าน้องอยากเปลี่ยน Offset กลางคันให้ใส่ตรงนี้ แต่ปกติจะ return
            return;
        }

        try {
            File musicFile = new File(path);
            if (musicFile.exists()) {
                stopMusic(); // ปิดตัวเก่าก่อนเสมอ

                currentBgmOffset = offsetDB; // บันทึกค่า Offset ของเพลงนี้ (-16.0f)
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

                // ใช้ sfxVolume ร่วมกับ offset เฉพาะไฟล์
                applyVolume(sfxClip, sfxVolume, offsetDB);

                activeSfx.add(sfxClip);
                sfxClip.start();

                // ปิดและลบออกจาก List เมื่อเล่นจบ
                sfxClip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        sfxClip.close();
                        activeSfx.remove(sfxClip);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void applyVolume(Clip clip, int volume, float offsetDB) {
        if (clip == null || !clip.isOpen()) return;

        try {
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

                // สูตรคำนวณ dB: เปลี่ยนจาก Linear (0-100) เป็น Logarithmic (dB)
                float baseDB = (float) (Math.log10(Math.max(volume, 0.0001) / 100.0) * 20.0);

                // รวมค่าพื้นฐานกับ Offset
                float finalDB = baseDB + offsetDB;

                // Clamp ค่าให้อยู่ในช่วงที่ Hardware รับได้
                finalDB = Math.max(gainControl.getMinimum(), Math.min(finalDB, gainControl.getMaximum()));

                gainControl.setValue(finalDB);
            }
        } catch (Exception e) {
            System.err.println("Error applying volume: " + e.getMessage());
        }
    }

    public static void stopMusic() {
        if (bgMusic != null) {
            bgMusic.stop();
            bgMusic.close(); // ปล่อย Resource ทุกครั้งที่หยุดเพื่อเปลี่ยนเพลง
        }
    }

    public static void preloadSFX(String path) {
        try {
            File file = new File(path);
            if (file.exists() && !preloadedClips.containsKey(path)) {
                AudioInputStream ais = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                preloadedClips.put(path, clip); // โหลดใส่ RAM ทิ้งไว้
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void playPreloadedSFX(String path, float offsetDB) {
        Clip clip = preloadedClips.get(path);
        if (clip != null) {
            clip.setFramePosition(0); // รีเซ็ตไปจุดเริ่มต้น
            applyVolume(clip, sfxVolume, offsetDB);
            clip.start();
        } else {
            // ถ้ายังไม่ได้โหลดไว้ ก็ให้เล่นแบบปกติ (แต่จะช้าหน่อย)
            playSFX(path, offsetDB);
        }
    }

    public static boolean isSfxRunning() {
        return !activeSfx.isEmpty();
    }
}