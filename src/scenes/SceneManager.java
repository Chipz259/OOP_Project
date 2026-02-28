package scenes;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class SceneManager {
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
            }
            catch (Exception e) {
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
            Door leftArrow = new Door("left_" + targetSceneId, 50, 490, 100, 100, leftDestId, this, imgLeft);
            scene.addGameObject(leftArrow);
        }

        if (rightDestId != null) {
            Door rightArrow = new Door("right_" + targetSceneId, 1770, 490, 100, 100, rightDestId, this, imgRight);
            scene.addGameObject(rightArrow);
        }
    }

    public void update() {
        if (getCurrentScene() != null) {
            currentScene.update();
        }

    }

    public void render(Graphics2D g2d) {
        if (getCurrentScene() != null) {
            currentScene.render(g2d);
        }
    }

}
