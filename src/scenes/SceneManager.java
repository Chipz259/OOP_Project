package scenes;

import entities.Item;
import entities.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class SceneManager {

    private float fade = 0;
    private boolean fadingOut = false, fadingIn = false;

    private String nextSceneId;
    private Player pendingPlayer;
    private int spawnX, spawnY;

    private HashMap<String, Scene> scenes;
    private Scene currentScene;

    public SceneManager() {
        scenes = new HashMap<>();
        initScenes(); // สั่งสร้างและประกอบฉากทันทีที่เปิดเกม
    }

    // Setter และ Getter
    public Scene getCurrentScene() {
        return currentScene;
    }

    // ระบบสลับฉาก
    public void loadScene(String sceneId) {
        if (scenes.containsKey(sceneId)) {
            currentScene = scenes.get(sceneId);
            System.out.println("ระบบ: เปลี่ยนเป็นฉาก -> " + sceneId);
        } else {
            System.out.println("ระบบ: ไม่พบฉาก ID -> " + sceneId);
        }
    }

    // ระบบประกอบร่างฉาก
    public void initScenes() {
        BufferedImage imgLeftArrow = null;
        BufferedImage imgRightArrow = null;

        // โหลดรูปลูกศร
        try {
            imgLeftArrow = ImageIO.read(getClass().getResource("/res/leftArrow.png"));
            imgRightArrow = ImageIO.read(getClass().getResource("/res/rightArrow.png"));
        } catch (IOException e) {
            System.err.println("โหลดรูปภาพไม่สำเร็จ");
        }

        // สร้างฉากเปล่าๆ ทั้ง 8 ฉาก
        for (int i = 1; i <= 8; i++) {
            String sceneId = "scene_" + i;
            Scene newScene = new Scene(sceneId);

            try {
                BufferedImage bgImage = ImageIO.read(getClass().getResource("/res/bg_" + i + ".png"));
                newScene.setBackgroundImage(bgImage);
            } catch (Exception e) {
                System.err.println("หารูปไม่เจอ");
            }

            scenes.put(sceneId, newScene);
        }

        // กำหนดลูกศรซ้าย-ขวา
        setupArrows("scene_1", null, "scene_2", imgLeftArrow, imgRightArrow);
        setupArrows("scene_2", "scene_1", "scene_6", imgLeftArrow, imgRightArrow);
        setupArrows("scene_3", null, "scene_4", imgLeftArrow, imgRightArrow);
        setupArrows("scene_4", "scene_3", "scene_5", imgLeftArrow, imgRightArrow);
        setupArrows("scene_5", "scene_4", "scene_6", imgLeftArrow, imgRightArrow);
        setupArrows("scene_6", "scene_5", "scene_7", imgLeftArrow, imgRightArrow);
        setupArrows("scene_7", "scene_6", "scene_8", imgLeftArrow, imgRightArrow);
        setupArrows("scene_8", "scene_7", null, imgLeftArrow, imgRightArrow);

        setupSpecificObjects();

        currentScene = scenes.get("scene_2");
    }

    public void setupSpecificObjects() {
        Item Candle = new Item("candle", 900, 400, 500, 500, "เทียนไข", "เทียนไขที่ยังไม่จุด", "Candle.png",
                "CandleStroke.png");
        Scene scene_2 = scenes.get("scene_2");
        if (scene_2 != null) {
            scene_2.addGameObject(Candle);
        }
        // //เอาไว้จัดการใส่ entities.Item เข้าไปในฉาก
        // //Ex
        // scenes.Scene scene1 = scenes.get("scene_1");
        // try {
        // BufferedImage imgWater =
        // ImageIO.read(getClass().getResourceAsStream("/res/Water.png"));

        // if (scene1 != null){
        // entities.Item waterBottle = new entities.Item("item_water", 0, 0, 0, 0, null,
        // null);
        // }
        // }
        // catch () {

        // }
    }

    public void setupArrows(String targetSceneId, String leftDestId, String rightDestId, BufferedImage imgLeft,
            BufferedImage imgRight) {

        Scene scene = scenes.get(targetSceneId);

        if (leftDestId != null) {
            int leftWidth = imgLeft.getWidth();
            int leftHeight = imgLeft.getHeight();

            Door leftArrow = new Door("left_" + targetSceneId, 50, 490, leftWidth, leftHeight, leftDestId, this,
                    imgLeft, 1650, 550);
            scene.addGameObject(leftArrow);
        }

        if (rightDestId != null) {
            int rightWidth = imgRight.getWidth();
            int rightHeight = imgRight.getHeight();

            int rightX = 1920 - rightWidth - 50;

            Door rightArrow = new Door("right_" + targetSceneId, rightX, 490, rightWidth, rightHeight, rightDestId,
                    this, imgRight, 150, 550);
            scene.addGameObject(rightArrow);
        }
    }

    public boolean isTransition() {
        return fadingOut || fadingIn;
    }

    public void startTransition(String targetScene, Player p, int spawnX, int spawnY) {
        if (!isTransition()) {
            this.nextSceneId = targetScene;
            this.pendingPlayer = p;
            this.spawnX = spawnX;
            this.spawnY = spawnY;
            this.fadingOut = true; // สั่งให้มืด
        }
    }

    public void update() {

        if (fadingOut) {
            fade += 0.05; // ความเร็วตอนมืดลง (ยิ่งเยอะยิ่งมืดไว)
            if (fade >= 1) {
                fade = 1;
                fadingOut = false;

                loadScene(nextSceneId);
                if (pendingPlayer != null) {
                    pendingPlayer.setX(spawnX);
                    pendingPlayer.setY(spawnY);
                }

                fadingIn = true;
            }
        } else if (fadingIn) {
            fade -= 0.05;
            if (fade <= 0) {
                fade = 0;
                fadingIn = false;
            }
        } else {
            if (getCurrentScene() != null) {
                currentScene.update();
            }
        }

    }

    public void render(Graphics2D g2d) {
        if (getCurrentScene() != null) {
            currentScene.render(g2d);
        }

        if (fade > 0){
            float safe = Math.max(0, Math.min(1, fade));

            g2d.setColor(new Color(0, 0, 0, safe));
            g2d.fillRect(0, 0, 1920, 1080);
        }
    }

}
