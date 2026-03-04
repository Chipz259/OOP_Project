package system;
import javax.sound.sampled.*;
import java.io.File;

public class AudioManager {
    public static int bgmVolume = 50;
    public static int sfxVolume = 50;
    private static Clip bgMusic;


    public static void setBgmVolume(int volume) {
        bgmVolume = volume;
        System.out.println("BGM Volume changed to: " + bgmVolume);
        if (bgMusic != null && bgMusic.isOpen()) {
            try {
                FloatControl gainControl = (FloatControl) bgMusic.getControl(FloatControl.Type.MASTER_GAIN);

                // สูตรคำนวณแบบ Logarithmic ที่ตอบสนองเร็วขึ้น
                float dB = (float) (Math.log(volume != 0 ? volume / 100.0 : 0.0001) / Math.log(10.0) * 20.0);

                // จำกัดค่าไม่ให้เกินขอบเขตของ hardware
                if (dB < gainControl.getMinimum()) dB = gainControl.getMinimum();
                if (dB > gainControl.getMaximum()) dB = gainControl.getMaximum();

                gainControl.setValue(dB);
            } catch (IllegalArgumentException e) {
                // กรณี Hardware ไม่รองรับ MASTER_GAIN
            }
        }
    }

    public static void setSfxVolume(int volume) {
        sfxVolume = volume;
        System.out.println("SFX Volume changed to: " + sfxVolume);
    }

    public static void playMusic(String path, int lowVolume) {
        try {
            File musicPath = new File(path);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                bgMusic = AudioSystem.getClip();
                bgMusic.open(audioInput);

                // ตั้งค่าให้เล่นวนลูปไปเรื่อยๆ
                bgMusic.loop(Clip.LOOP_CONTINUOUSLY);

                // ปรับระดับเสียงเริ่มต้น
                setBgmVolume(bgmVolume - lowVolume);

                bgMusic.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopMusic() {
        if (bgMusic != null && bgMusic.isRunning()) {
            bgMusic.stop(); // หยุดเล่น
            bgMusic.setFramePosition(0); // รีเซ็ตหัวอ่านกลับไปที่จุดเริ่มเพลง
        }
    }
}
