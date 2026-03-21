package scenes;

import entities.Item;
import entities.NPC;
import entities.Player;
import entities.GamePanel;
import system.AudioManager;
import system.FadeTransition;
import system.DialogueLine;
import ui.DialogueOverlay;
import ui.SceneTitleOverlay;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class SceneManager {
    private FadeTransition fadeTransition;
    private String nextSceneId;
    private Player pendingPlayer;
    private int spawnX, spawnY;
    private HashMap<String, Scene> scenes;
    private Scene currentScene;
    private DialogueOverlay overlay;
    private SceneTitleOverlay titleOverlay;

    public SceneManager(Player player) {
        scenes = new HashMap<>();
        this.pendingPlayer = player;
        initScenes(); // สั่งสร้างและประกอบฉากทันทีที่เปิดเกม
    }
    public void setFadeTransition(FadeTransition fadeTransition) {
        this.fadeTransition = fadeTransition;
    }
    public FadeTransition getFadeTransition(){
        return  this.fadeTransition;
    }

    public DialogueOverlay getOverlay() {
        return overlay;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    // ระบบสลับฉาก
    public void loadScene(String sceneId) {
        if (scenes.containsKey(sceneId)) {
            currentScene = scenes.get(sceneId);
            playBGMusic(sceneId);
            System.out.println("ระบบ: เปลี่ยนเป็นฉาก -> " + sceneId);

            if (titleOverlay != null) {
                String thName = getSceneDisplayName(sceneId);
                titleOverlay.showTitle(thName);
            }
    }

    // ระบบประกอบร่างฉาก
    public void initScenes() {
            BufferedImage imgLeftArrow = null;
            BufferedImage imgRightArrow = null;
            BufferedImage imgLeftHover = null;
            BufferedImage imgRightHover = null;
            BufferedImage imgDialogBox = null;

            // โหลดรูปลูกศร
            try {
                imgLeftArrow = ImageIO.read(getClass().getResource("/res/Left_Default.png"));
                imgRightArrow = ImageIO.read(getClass().getResource("/res/Right_Default.png"));
                imgLeftHover = ImageIO.read(getClass().getResource("/res/Left_Hover.png"));
                imgRightHover = ImageIO.read(getClass().getResource("/res/Right_Hover.png"));
                URL boxUrl = getClass().getResource("/res/Rectangle251.png");
                if (boxUrl != null) imgDialogBox = ImageIO.read(boxUrl);
            } catch (IOException e) {
                System.err.println("โหลดรูปภาพไม่สำเร็จ");
            }

            overlay = new DialogueOverlay(GamePanel.customFont, imgDialogBox);
            titleOverlay = new SceneTitleOverlay(GamePanel.customFont);

            // สร้างฉากเปล่าๆ ทั้ง 8 ฉาก
            for (int i = 1; i <= 11; i++) {
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
            setupArrows("scene_1", null, "scene_2", imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);
            setupArrows("scene_2", "scene_1", "scene_3", imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);
            setupArrows("scene_3", "scene_4", null, imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);
            setupArrows("scene_4", "scene_5", "scene_3", imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);
            setupArrows("scene_5", null, "scene_4", imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);
            setupArrows("scene_6", "scene_7", "scene_8", imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);
            setupArrows("scene_7", null, "scene_6", imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);
            setupArrows("scene_8", "scene_6", "scene_9", imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);
            setupArrows("scene_9", "scene_8", "scene_10", imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);
            setupArrows("scene_10", "scene_9", "scene_11", imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);
            setupArrows("scene_11", "scene_10", null, imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);

            scenes.put("qte_choke", new SceneQTE_Choke("qte_choke", this, this.pendingPlayer));

            setupSpecificObjects();

            for (entities.GameObject obj : scenes.get("scene_2").getObjectsInScene()) {
                if (obj instanceof Door && obj.getID().equals("right_scene_2")) {
                    ((Door) obj).setSpawnX(1550);
                    break;
                }
            }
        }
        currentScene = scenes.get("scene_2");
    }

    public void setupSpecificObjects() {
        Item Candle = new Item("candle", 900, 700, 100, 100, "เทียนไข", "เทียนไขที่ยังไม่จุด", "candle.png", "candleStroke.png");
        Item Water = new Item("water", 300, 700, 100, 100, "ขวดน้ำ", "ขวดน้ำ kmitl", "waterBottle.png", "CandleStroke.png");
        Item Candle2 = new Item("candle2", 600, 700, 100, 100, "เทียนไข2", "เทียนไขที่ยังไม่จุด", "candle.png", "candleStroke.png");

        Item Bed = new Item("bed", 900, 700, 100, 100, "เตียง", "เตียงนะจ๊ะ", "candle.png", "candleStroke.png") {
            @Override
            public void onInteract(Player p) {
                //this.setVisible(false);
                startQTETransition("qte_choke");
            }
        };
        Item Daddy = new Item("daddy", 900, 700, 100, 100, "แด๊ดดี้", "พ่อเองงับ", "daddy.png", "candleStroke.png") {
            @Override
            public void onInteract(Player p) {
//                this.setVisible(false);
                system.ObjectiveManager.getInstance().advanceObjective();
            }
        };

        BufferedImage girlIdle = null, girlTalk = null, mainIdle = null, mainTalk = null;
        try {
            URL urlGirlIdle = getClass().getResource("/res/NPC/NPC_ girl.png");
            URL urlGirlTalk = getClass().getResource("/res/NPC/NPC_ girl_talk.png");
            URL urlMainIdle = getClass().getResource("/res/NPC/Main_character.png");
            URL urlMainTalk = getClass().getResource("/res/NPC/Main_character_talk.png");

            if (urlGirlIdle != null) girlIdle = ImageIO.read(urlGirlIdle);
            if (urlGirlTalk != null) girlTalk = ImageIO.read(urlGirlTalk);
            if (urlMainIdle != null) mainIdle = ImageIO.read(urlMainIdle);
            if (urlMainTalk != null) mainTalk = ImageIO.read(urlMainTalk);
        } catch (Exception e) {
            e.printStackTrace();
        }

        NPC npcGirl = new NPC("Girl", 1300, 550, 150, 313, "/res/NPC/NPC1_a.PNG", 12, 622, 1299);

        DialogueLine[] npcGirlScript = {
            new DialogueLine("น้ำตาล", "พ่อตายแล้วน้าฮือๆๆๆ", girlTalk, mainIdle),
            new DialogueLine("พระเอก", "อุก้ะๆๆๆๆๆๆ", girlIdle, mainTalk),
            new DialogueLine("น้ำตาล", "ไปงานศพด้วยจ้าเพื่อน", girlTalk, mainIdle)
        };
        npcGirl.setVNDialogue(npcGirlScript, overlay);
        npcGirl.setDialogTransform(50, 0, 706, 941, 1200, 0, 706, 941);

        Scene scene_1 = scenes.get("scene_1");
        Scene scene_2 = scenes.get("scene_2");
        Scene scene_5 = scenes.get("scene_5");
        if (scene_1 != null) {
            scene_1.addGameObject(Daddy);
            scene_1.addGameObject(npcGirl);
        }
        if (scene_2 != null) {
            scene_2.addGameObject(Candle);
            scene_2.addGameObject(Candle2);
            scene_2.addGameObject(Water);
        }
        if (scene_5 != null) {
            scene_5.addGameObject(Bed);
        }
    }

    private String getSceneDisplayName(String sceneId) {
        if (sceneId == null) return "";
        switch (sceneId) {
            case "scene_1" : return "หน้าเมรุ";
            case "scene_2" : return "ด้านข้างเมรุ";
            case "scene_3" : return "หน้าบ้าน";
            case "scene_4" : return "โถงบ้าน";
            case "scene_5" : return "ห้องนอน";
            case "scene_6" : return "ห้องนอน";
            case "scene_7" : return "ห้องทำพิธี";
            case "scene_8" : return "โถงบ้าน";
            case "scene_9" : return "หน้าบ้าน";
            case "scene_10" : return "ป่า";
            case "scene_11" : return "ป่า";
            default: return  sceneId;
        }
    }

    public void setupArrows(String targetSceneId, String leftDestId, String rightDestId, BufferedImage imgLeft, BufferedImage imgRight, BufferedImage imgLeftHover, BufferedImage imgRightHover) {

        Scene scene = scenes.get(targetSceneId);

        if (leftDestId != null) {
            int leftWidth = imgLeft.getWidth();
            int leftHeight = imgLeft.getHeight();
            String leftName = getSceneDisplayName(leftDestId);

            Door leftArrow = new Door("left_" + targetSceneId, 50, 490, leftWidth, leftHeight, leftName, leftDestId,  this,
                    imgLeft, 1550, 550);
            leftArrow.setVisible(false);
            leftArrow.setHoverSpite(imgLeftHover);
            scene.addGameObject(leftArrow);
        }

        if (rightDestId != null) {
            int rightWidth = imgRight.getWidth();
            int rightHeight = imgRight.getHeight();
            String rightName = getSceneDisplayName(rightDestId);

            int rightX = 1920 - rightWidth - 50;

            Door rightArrow = new Door("right_" + targetSceneId, rightX, 490, rightWidth, rightHeight, rightName, rightDestId ,
                    this, imgRight, 200, 550);
            rightArrow.setVisible(false);
            rightArrow.setHoverSpite(imgRightHover);
            scene.addGameObject(rightArrow);
        }
    }

    public void startTransition(String targetScene, Player p, int spawnX, int spawnY) {
        if (fadeTransition != null && !fadeTransition.isFading()) {

            fadeTransition.executeFade(200, 0, 200, () -> {
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

    public void startQTETransition(String targetScene) {
        if (fadeTransition != null && !fadeTransition.isFading()) {
            fadeTransition.executeFade(200, 200, 0, () -> {
                system.ObjectiveManager.getInstance().advanceObjective();
                loadScene(targetScene);
            });
        }
        else if (fadeTransition == null) {
            loadScene(targetScene);
        }
    }


    public void update() {
        if (titleOverlay != null) {
            titleOverlay.update();
        }
        if (overlay != null && overlay.isActive()) {
            overlay.update();
        }
        else if (getCurrentScene() != null) {
            currentScene.update();
        }

    }

    public void render(Graphics2D g2d) {
        if (getCurrentScene() != null) {
            currentScene.render(g2d);
        }

        if (titleOverlay != null) {
            titleOverlay.render(g2d, 1920);
        }

        if (overlay != null && overlay.isActive()) {
            overlay.render(g2d, 1920, 1080);
        }
    }

    private void playBGMusic(String sceenID) {
        if (sceenID.equals("scene_1")) {
            AudioManager.playMusic("src/res/sound/UIABg.wav", 0.0f);
        }
        else {
            System.out.println("น้องโหลดเพลงไม่ขึ้นจ้าาา");
        }
    }
}
