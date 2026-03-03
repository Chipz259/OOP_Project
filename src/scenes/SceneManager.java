package scenes;

import entities.Item;
import entities.Player;
import system.FadeTransition;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class SceneManager {
    private FadeTransition fadeTransition;

    private String nextSceneId;
    private Player pendingPlayer;
    private int spawnX, spawnY;

    private HashMap<String, Scene> scenes;
    private Scene currentScene;

    public SceneManager(Player player) {
        scenes = new HashMap<>();
        this.pendingPlayer = player;
        initScenes(); // สั่งสร้างและประกอบฉากทันทีที่เปิดเกม
    }

    public void setFadeTransition(FadeTransition fadeTransition) {
        this.fadeTransition = fadeTransition;
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

        scenes.put("qte_choke", new SceneQTE_Choke("qte_choke", this, this.pendingPlayer));

        setupSpecificObjects();

        currentScene = scenes.get("scene_2");
    }

    public void setupSpecificObjects() {
        Item Candle = new Item("candle", 900, 700, 100, 100, "เทียนไข", "เทียนไขที่ยังไม่จุด", "candle.png", "candleStroke.png");
        Item Water = new Item("water", 300, 700, 100, 100, "ขวดน้ำ", "ขวดน้ำ kmitl", "waterBottle.png", "CandleStroke.png");
        Item Candle2 = new Item("candle2", 600, 700, 100, 100, "เทียนไข2", "เทียนไขที่ยังไม่จุด", "candle.png", "candleStroke.png");
        Item Bed = new Item("bed", 900, 700, 100, 100, "เตียง", "เตียงนะจ๊ะ", "candle.png", "candleStroke.png") {
            @Override
            public void onInteract(Player p) {
                // 🌟 เมื่อกดเตียง สั่ง Fade จอมืดแล้ววาร์ปไปฉาก qte_choke ทันที!
                //this.setVisible(false);
                startTransition("qte_choke", p, 0, 0);
            }
        };

        Scene scene_2 = scenes.get("scene_2");
        Scene scene_4 = scenes.get("scene_4");
        if (scene_2 != null) {
            scene_2.addGameObject(Candle);
            scene_2.addGameObject(Candle2);
            scene_2.addGameObject(Water);
        }
        if (scene_4 != null) {
            scene_4.addGameObject(Bed);
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

    public void startTransition(String targetScene, Player p, int spawnX, int spawnY) {
        if (fadeTransition != null && !fadeTransition.isFading()) {

            fadeTransition.executeFade(() -> {
                loadScene(targetScene);
                if (p != null) {
                    p.setX(spawnX);
                    p.setY(spawnY);
                }
            });

        }
        else if (fadeTransition == null) {
            loadScene(targetScene);
            if (p != null) {
                p.setX(spawnX);
                p.setY(spawnY);
            }
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
