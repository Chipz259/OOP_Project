package scenes;

import Minigame.FinalBossFightPackage.FinalBossFight;
import Minigame.JigsawPackage.JigsawFrame;
import Minigame.KonKlongPackage.KonKlong;
import Minigame.RotateNarigaPackage.RotateNariga;
import Minigame.RotateYanPackage.RotateYan;
import Minigame.UnlockBoxPackage.UnlockBox;
import entities.*;
import system.*;
import ui.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.*;

public class SceneManager {
    public int retryMode = 1;
    private FadeTransition fadeTransition;
    private String nextSceneId;
    private Player pendingPlayer;
    private int spawnX, spawnY;
    private HashMap<String, Scene> scenes;
    private Scene currentScene;
    private DialogueOverlay overlay;
    private SceneTitleOverlay titleOverlay;
    private GamePanel gamePanel;
    private BufferedImage girlIdle, girlTalk, mainIdle, mainIdle2, mainTalk, evilIdle, evilTalk, npc3Idle, npc3Talk, npc2Idle, npc2Talk, dadIdle, dadTalk;
    private boolean isFirstTimeScene3 = true, isFirstTimeScene6 = true, isFirstTimeScene11 = true , isFirstTimeScene14 = true, isFirstTimeScene12 = true, isFirstTimeScene16 = true, isFirstTimeScene18 = true, isFirstTimeScene17 = true, isFirstTimeScene19 = true;
    public static String[] ritualItems = {"", "", "", ""};
    private String[] ritualItemNames = {"", "", "", ""};
    private Item[] ritualSlots = new Item[4];
    private final String[] RITUAL_ANSWERS = {"knife", "holyWater", "kafak", "rosary"};
    private int chestopen = 0;
    private boolean hasSpokenAllItems = false;

    public SceneManager(Player player) {
        scenes = new HashMap<>();
        this.pendingPlayer = player;
        initScenes(); // สั่งสร้างและประกอบฉากทันทีที่เปิดเกม
    }

    public void setFadeTransition(FadeTransition fadeTransition) {
        this.fadeTransition = fadeTransition;
    }

    public FadeTransition getFadeTransition() {
        return this.fadeTransition;
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
            managePlayBGM(sceneId);
            System.out.println("ระบบ: เปลี่ยนเป็นฉาก -> " + sceneId);

            for (entities.GameObject obj : currentScene.getObjectsInScene()) {
                if (obj instanceof Item) {
                    ((Item) obj).setHovered(false);
                } else if (obj instanceof Door) {
                    ((Door) obj).setIsHovered(false);
                } else if (obj instanceof NPC) { //
                    ((NPC) obj).setHovered(false);
                }
            }

            if (titleOverlay != null) {
                if (sceneId.equals("scene_12") && isFirstTimeScene12) {}
                else if (sceneId.equals("scene_14") && isFirstTimeScene14) {}
                else if (sceneId.equals("scene_16") && isFirstTimeScene16) {}
                else if (sceneId.equals("scene_18") && isFirstTimeScene18) {}
                else if (sceneId.equals("scene_6") && isFirstTimeScene6) {}
                else if (sceneId.equals("scene_19") && isFirstTimeScene19) {}
                else {
                    String thName = getSceneDisplayName(sceneId);
                    titleOverlay.showTitle(thName);
                }
            }
            checkSceneEnterEvents(sceneId);
        } else {
            System.out.println("ไม่พบฉาก");
        }
    }
    public void checkSceneEnterEvents(String sceneId) {
        if (sceneId.equals("scene_1")) {

        }
        else if (sceneId.equals("scene_2")) {

        }
        else if (sceneId.equals("scene_3")) {

            if (isFirstTimeScene3) {
                DialogueLine[] scene3Intro = {
                        new DialogueLine("ตุลย์", "เห้อ… เหนื่อยจัง แถมยังรู้สึกแปลก ๆ", null, mainIdle2),
                        new DialogueLine("ตุลย์", "แต่คงไม่มีอะไรหรอกมั้ง… อาจจะแค่คิดมากไป", null, mainIdle2),
                        new DialogueLine("ตุลย์", "นอนสักตื่น เดี๋ยวก็คงดีขึ้น", null, mainIdle2)
                };
                overlay.setCharacterTransform(50, 0, 706, 941, 1200, 0, 706, 941);
                overlay.startDialogue(scene3Intro, () -> {
                });

                isFirstTimeScene3 = false;
            }

        }
        else if (sceneId.equals("scene_4")) {

        }
        else if (sceneId.equals("scene_5")) {

        }
        else if (sceneId.equals("scene_6")) {
            if (isFirstTimeScene6) {
                DialogueLine[] scene3Intro = {
                        new DialogueLine("ตุลย์", "สมุดบันทึกของพ่อหรอ…", null, mainIdle2)
                };
                overlay.setCharacterTransform(50, 0, 706, 941, 1200, 0, 706, 941);
                overlay.startDialogue(scene3Intro, () -> {
                });

                isFirstTimeScene6 = false;
            }
        }
        else if (sceneId.equals("scene_11")) {
            if (isFirstTimeScene11) {
                DialogueLine[] scene11Intro = {
                        new DialogueLine("ตุลย์", "กาฝากไม้คูณตายพราย…", null, mainIdle2),
                        new DialogueLine("ตุลย์", "ฉันน่าจะต้องหาต้นไม้ที่เหมือนกับในสมุดบันทึกนะ…", null, mainIdle2),
                        new DialogueLine("ตุลย์", "จากที่ดู… ต้นนั้นน่าจะพอมีจุดสังเกตุอยู่", null, mainIdle2)
                };
                overlay.setCharacterTransform(50, 0, 706, 941, 1200, 0, 706, 941);
                overlay.startDialogue(scene11Intro, () -> {
                });
            }
            isFirstTimeScene11 = false;
        }
        else if (sceneId.equals("scene_12")) {
            if (isFirstTimeScene12) {
                isFirstTimeScene12 = false;
            }
        }
        else if (sceneId.equals("scene_14")) {
            if (isFirstTimeScene14) {
                DialogueLine[] scene3Intro = {
                        new DialogueLine("ตุลย์", "เกือบไปแล้ว… นี่มันเกิดอะไรขึ้น ! !", null, mainIdle2),
                        new DialogueLine("ตุลย์", "ตอนนี้คงต้องทำยันต์ป้องกันไว้ที่ประตูก่อน", null, mainIdle2)
                };
                overlay.setCharacterTransform(50, 0, 706, 941, 1200, 0, 706, 941);
                overlay.startDialogue(scene3Intro, () -> {
                });

                isFirstTimeScene14 = false;
            }
        }
        else if (sceneId.equals("scene_16")) {
            if (isFirstTimeScene16) {
                isFirstTimeScene16 = false;
            }
        }
        else if (sceneId.equals("scene_17")) {
            if (isFirstTimeScene17) {
                DialogueLine[] scene17Intro = {
                        new DialogueLine("ตุลย์", "กล่องหล่นลงลงมาได้ยังไงกันนะ", null, mainIdle2),
                        new DialogueLine("ตุลย์", "เก็บขึ้นมาดีกว่า...", null, mainIdle2)
                };
                overlay.setCharacterTransform(50, 0, 706, 941, 1200, 0, 706, 941);
                overlay.startDialogue(scene17Intro, () -> {
                });

                isFirstTimeScene17 = false;
            }
        }
        else if (sceneId.equals("scene_18")) {
            if (isFirstTimeScene18) {
                DialogueLine[] scene18Intro = {
                        new DialogueLine("ตุลย์", "นี่มัน… กล่องอะไรน่ะ", null, mainIdle2),
                        new DialogueLine("ตุลย์", "มีอะไรอยู่ในกล่องด้วย ลองหารหัสมาเปิดดีกว่า", null, mainIdle2)
                };
                overlay.setCharacterTransform(50, 0, 706, 941, 1200, 0, 706, 941);
                overlay.startDialogue(scene18Intro, () -> {
                });

                isFirstTimeScene18 = false;
            }
        }
        else if (sceneId.equals("scene_19")) {
            if (isFirstTimeScene19) {
                isFirstTimeScene19 = false;
            }
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
            URL boxUrl = getClass().getResource("/res/NPC/Textbox.png");
            if (boxUrl != null) imgDialogBox = ImageIO.read(boxUrl);
        } catch (IOException e) {
            System.err.println("โหลดรูปภาพไม่สำเร็จ");
        }

        overlay = new DialogueOverlay(FontManager.pspimpdeedIIIFont, imgDialogBox);
        titleOverlay = new SceneTitleOverlay(FontManager.pspimpdeedIIIFont);

        // สร้างฉากเปล่าๆ ทั้ง 8 ฉาก
        for (int i = 1; i <= 22; i++) {
            String sceneId = "scene_" + i;
            Scene newScene = new Scene(sceneId);

            try {
                BufferedImage bgImage = ImageIO.read(getClass().getResource("/res/bg/bg_" + i + ".png"));
                newScene.setBackgroundImage(bgImage);
            } catch (Exception e) {
                System.err.println("หารูปไม่เจอ");
            }

            scenes.put(sceneId, newScene);
        }

        // กำหนดลูกศรซ้าย-ขวา
        setupArrows("scene_1", null, "scene_2", imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);
        setupArrows("scene_2", "scene_1", null, imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);
        setupArrows("scene_12", null, "scene_13", imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);
        setupArrows("scene_13", "scene_12", "scene_3", imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);
        setupArrows("scene_3", "scene_4", null, imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);
        setupArrows("scene_4", "scene_5", "scene_3", imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);
        setupArrows("scene_5", null, "scene_4", imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);
        setupArrows("scene_14", null, "scene_15", imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);
        setupArrows("scene_15", "scene_14", null, imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);
        setupArrows("scene_16", "scene_17", null, imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);
        setupArrows("scene_17", null, "scene_16", imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);
        setupArrows("scene_18", null, null, imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);
        setupArrows("scene_6", "scene_7", "scene_8", imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);
        setupArrows("scene_7", null, "scene_6", imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);
        setupArrows("scene_8", "scene_6", "scene_9", imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);
        setupArrows("scene_9", "scene_8", "scene_10", imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);
        setupArrows("scene_10", "scene_9", "scene_11", imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);
        setupArrows("scene_11", "scene_10", null, imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);
        setupArrows("scene_19", null, "scene_20", imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);
        setupArrows("scene_20", "scene_19", "scene_21", imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);
        setupArrows("scene_21", "scene_20", "scene_22", imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);
        setupArrows("scene_22", "scene_21", null, imgLeftArrow, imgRightArrow, imgLeftHover, imgRightHover);

        scenes.put("qte_choke", new SceneQTE_Choke("qte_choke", this, this.pendingPlayer));

        setupSpecificObjects();

        for (entities.GameObject obj : scenes.get("scene_13").getObjectsInScene()) {
            if (obj instanceof Door && obj.getID().equals("right_scene_13")) {
                ((Door) obj).setSpawnX(1550);
                break;
            }
        }
        currentScene = scenes.get("scene_2");
    }

    public Item createRitualSlot(int index, int intendedCenterX, int intendedBottomY, String hint) {
        int objW = 100;
        int objH = 100;
        int actualDrawX = intendedCenterX - (objW / 2);
        int actualDrawY = intendedBottomY - objH + 40;
        ritualSlots[index] = new Item("slot_" + index, actualDrawX, actualDrawY, 100, 100, "แท่นที่ " + (index + 1), hint, "slot_empty.png", "slot_hover.png") {
            @Override
            public void onInteract(Player p) {
                // ถ้าช่องนี้ไม่ว่าง
                if (ritualItems[index].equals("") == false) {
                    String thaiName = ritualItemNames[index];
                    overlay.startDialogue(new DialogueLine[]{
                            new DialogueLine("พระเอก", "ฉันวาง["+ thaiName +"]ไว้ตรงนี้แล้ว", null, mainTalk)
                    }, null);
                    return;
                }

                // ถ้าช่องนี้ว่าง
                int selIdx = p.getInventory().getSelectedSlot();
                if (selIdx != -1) {
                    if (p.getInventory().getSlots()[selIdx] != null) {
                        Item inHand = p.getInventory().getSlots()[selIdx];

                        ritualItems[index] = inHand.getObjectId();
                        ritualItemNames[index] = inHand.getItemName();

                        p.getInventory().removeSelectedItem();

                        if (isAllSlotsFilled() == true) {
                            checkRitual(p);
                        }
                    }
                }
            }
        };
        return ritualSlots[index];
    }
    private void checkRitual(Player p) {
        boolean isCorrect = true;
        for (int i = 0; i < RITUAL_ANSWERS.length; i = i + 1) {
            if (ritualItems[i].equals(RITUAL_ANSWERS[i]) == false) {
                isCorrect = false;
            }
        }

        if (isCorrect == true) {
            // ถ้าถูก
            overlay.startDialogue(new DialogueLine[]{
                    new DialogueLine("ตุลย์", "พิธีกรรมสมบูรณ์แบบ... แสงสว่างจ้าออกมาจากแท่นแล้ว!", null, mainTalk)
            }, () -> {
                system.ObjectiveManager.getInstance().advanceObjective();
                if (fadeTransition != null && !fadeTransition.isFading()) {
                    fadeTransition.executeFade(500, 0, 500, () -> {
                        startGhostAndBossSequence();
                    });
                }
            });
        } else {
            // ผิด
            overlay.startDialogue(new DialogueLine[]{
                    new DialogueLine("ตุลย์", "ดูเหมือนจะไม่ใช่นะ ลองวางใหม่อีกที", null, mainIdle2)
            }, () -> {
                // คืนของ
                for (int i = 0; i < ritualItems.length; i = i + 1) {
                    String id = ritualItems[i];
                    if (id.equals("") == false) {
                        Item itemBack = new Item(id, 0, 0, 100, 100, ritualItemNames[i], "", id + ".png", "");
                        p.getInventory().addItem(itemBack);

                        ritualItems[i] = "";
                        ritualItemNames[i] = "";
                    }
                }

                for (int j = 0; j < ritualSlots.length; j = j + 1) {
                    if (ritualSlots[j] != null) {
                        ritualSlots[j].changeImage(ritualSlots[j].getX(), ritualSlots[j].getY(), 100, 100, "slot_empty.png", "slot_empty.png");
                    }
                }
            });
        }
    }

    // เช็คจำนวนของที่วาง
    private boolean isAllSlotsFilled() {
        int count = 0;
        for (int i = 0; i < ritualItems.length; i = i + 1) {
            if (ritualItems[i].equals("") == false) {
                count = count + 1;
            }
        }

        if (count == 4) {
            return true;
        } else {
            return false;
        }
    }

    public Item createPickUpItem(String id, int x, int y, int w, int h, String name, String desc, String img, String hoverImg) {
        return new Item(id, x, y, w, h, name, desc, img, hoverImg) {
            @Override
            public void onInteract(Player p) {
                if (this.isCollected()) {
                    return;
                }
                p.getInventory().addItem(this);
                this.setCollected(true);
                this.setVisible(false);

                DialogueLine[] script = {
                        new DialogueLine("ระบบ", "คุณได้รับ [" + desc + "]", null, null)
                };

                overlay.setCharacterTransform(0, 0, 0, 0, 0, 0, 0, 0);
                overlay.startDialogue(script, null);
            }
        };
    }

    public void startGhostAndBossSequence() {
        this.retryMode = 2;

        AudioManager.stopMusic();
        AudioManager.playSFX("src/res/sound/MinigameBossBefore.wav", -5.0f);
        ui.MainGameFrame mainFrame = (ui.MainGameFrame) SwingUtilities.getWindowAncestor(SceneManager.this.getGamePanel());
        CutsceneGhost cutsceneGhost = new CutsceneGhost(mainFrame, "/res/bg/Ghost.png", () -> {
            if (fadeTransition != null && !fadeTransition.isFading()) {
                fadeTransition.executeFade(200, 0, 200, () -> {

                    //จะถูกเรียกตอนที่หน้าจอมืดสนิทพอดี
                    FinalBossFight bossFight = new FinalBossFight(mainFrame,
                            () -> {
                                if (fadeTransition != null && !fadeTransition.isFading()) {
                                    fadeTransition.executeFade(500, 0, 500, () -> {
                                        loadScene("scene_19");
                                        DialogueLine[] winScript = {
                                                new DialogueLine("ตุลย์", "แฮ่ก... แฮ่ก... จบสักทีนะ", null, mainTalk)
                                        };
                                        overlay.startDialogue(winScript, () -> {
                                        });
                                    });
                                } else {
                                    //กันพัง
                                    loadScene("scene_19");
                                }
                            },
                            () -> {}
                    );
                    AudioManager.stopAllSFX();
                    mainFrame.openMinigame(bossFight);

                });
            } else {
                // กันเหนียว ถ้าระบบเฟดไม่ว่าง ให้โหลดบอสไฟต์เลยไม่ต้องรอจอมืด
                FinalBossFight bossFight = new FinalBossFight(mainFrame,
                        () -> {
                            loadScene("scene_19");
                            DialogueLine[] winScript = {
                                    new DialogueLine("ตุลย์", "สำเร็จ ! ! ! มันจบแล้ว", null, mainTalk)
                            };
                            overlay.startDialogue(winScript, () -> {});
                        },
                        () -> {
                        }
                );
                mainFrame.openMinigame(bossFight);
            }
        });

        mainFrame.openCutscene(cutsceneGhost);
    }

    public void startEndingSequence() {
        ui.MainGameFrame mainFrame = (ui.MainGameFrame) SwingUtilities.getWindowAncestor(SceneManager.this.getGamePanel());
        if (fadeTransition != null && !fadeTransition.isFading()) {
            fadeTransition.executeFade(700, 0, 700, () -> {

                String[] endCutScene = {
                        "/res/bg/End1.png",
                        "/res/bg/End2.png",
                        "/res/bg/End3.png",
                        "/res/bg/End4.png"
                };

                CutsceneEnd cutsceneEnd = new CutsceneEnd(endCutScene, () -> {
                    fadeTransition.executeFade(700, 0, 700, () -> {
                        mainFrame.closeCutscene();

                        CutsceneEndCredit cutsceneEndCredit = new CutsceneEndCredit("/res/bg/EndCredit.jpg");
                        mainFrame.openCutscene(cutsceneEndCredit);
                        javax.swing.Timer creditTime = new javax.swing.Timer(5000, e ->{
                            fadeTransition.executeFade(700, 0, 700, () ->{
                                mainFrame.closeCutscene();
                                mainFrame.setIsStartGame(false); // จะได้กด Resume ไม่ได้
                                resetManagerStates();
                                //ส่งกลับหน้าเมนู
                                mainFrame.returnToMainMenu(false);
                            });
                        });
                        creditTime.setRepeats(false);
                        creditTime.start();
                    });
                });
                mainFrame.openCutscene(cutsceneEnd);
            });
        }
    }

    private Item createStoryItem(String id, int x, int y, int w, int h, String name, String desc, String img, String hoverImg, DialogueLine[] customScript) {

        return new Item(id, x, y, w, h, name, desc, img, hoverImg) {
            @Override
            public void onInteract(Player p) {
                if (this.isCollected()) {
                    return;
                }
                overlay.setCharacterTransform(50, 0, 706, 941, 1200, 0, 706, 941);
                overlay.startDialogue(customScript, () -> {
                    if (p.getInventory().addItem(this)) {
                        System.out.println("ระบบ: เก็บ " + name + " เข้ากระเป๋าแล้ว");
                        this.setCollected(true);
                        this.setVisible(false);
                    } else {
                        System.out.println("ระบบ: กระเป๋าเต็ม!");
                    }
                });
            }
        };
    }

    public void setupSpecificObjects() {

        try {
            URL urlGirlIdle = getClass().getResource("/res/NPC/NPC_ girl.png");
            URL urlGirlTalk = getClass().getResource("/res/NPC/NPC_ girl_talk.png");

            URL urlMainIdle = getClass().getResource("/res/NPC/Main_character.png");
            URL urlMainIdle2 = getClass().getResource("/res/NPC/Main_character2.png");
            URL urlMainTalk = getClass().getResource("/res/NPC/Main_character_talk.png");

            URL urlEvilIdle = getClass().getResource("/res/NPC/Evil_charactor.png");
            URL urlEvilTalk = getClass().getResource("/res/NPC/Evil_talk.png");

            URL urlNpc3Idle = getClass().getResource("/res/NPC/NPC3_ charactor.png");
            URL urlNpc3Talk = getClass().getResource("/res/NPC/NPC3_talk.png");

            URL urlNpc2Idle = getClass().getResource("/res/NPC/NPC2_character.png");
            URL urlNpc2Talk = getClass().getResource("/res/NPC/NPC2_talk.png");

            URL urlDadIdle = getClass().getResource("/res/NPC/Dad.png");
            URL urlDadTalk = getClass().getResource("/res/NPC/Dad_talk.png");

            if (urlGirlIdle != null) girlIdle = ImageIO.read(urlGirlIdle);
            if (urlGirlTalk != null) girlTalk = ImageIO.read(urlGirlTalk);
            if (urlMainIdle != null) mainIdle = ImageIO.read(urlMainIdle);
            if (urlMainIdle2 != null) mainIdle2 = ImageIO.read(urlMainIdle2);
            if (urlMainTalk != null) mainTalk = ImageIO.read(urlMainTalk);
            if (urlEvilIdle != null) evilIdle = ImageIO.read(urlEvilIdle);
            if (urlEvilTalk != null) evilTalk = ImageIO.read(urlEvilTalk);
            if (urlNpc3Idle != null) npc3Idle = ImageIO.read(urlNpc3Idle);
            if (urlNpc3Talk != null) npc3Talk = ImageIO.read(urlNpc3Talk);
            if (urlNpc2Idle != null) npc2Idle = ImageIO.read(urlNpc2Idle);
            if (urlNpc2Talk != null) npc2Talk = ImageIO.read(urlNpc2Talk);
            if (urlDadIdle != null) dadIdle = ImageIO.read(urlDadIdle);
            if (urlDadTalk != null) dadTalk = ImageIO.read(urlDadTalk);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //scene_1
        Item Candle = createPickUpItem("candle", 625, 567, 100, 100, "เทียนไข", "เทียนไข", "candle.png", "candleHover.png");
        Item Water = createPickUpItem("water", 1165, 700, 100, 100, "ขวดน้ำ", "ขวดน้ำ kmitl", "water.png", "waterHover.png");
        DialogueLine[] rosaryScript = {
                new DialogueLine("ระบบ", "คุณได้รับ [ลูกประคำ]", null, null),
                new DialogueLine("ตุลย์", "ลูกประคำ...", null, mainTalk),
                new DialogueLine("ตุลย์", "มันมาอยู่ตรงนี้ตั้งแต่เมื่อไหร่กันนะ", null, mainTalk)
        };
        Item Rosary = createStoryItem("rosary", 425, 550, 100, 100, "ลูกประคำ", "ลูกประคำ", "rosary.png", "rosaryHover.png", rosaryScript);
        DialogueLine[] flowerScript = {
                new DialogueLine("ระบบ", "คุณได้รับ [ดอกไม้จันทน์]", null, null),
                new DialogueLine("ตุลย์", "ถึงเวลาที่ต้องไปอำลาพ่อแล้วสินะ...", null, mainTalk)
        };
        Item Flower = createStoryItem("flower", 665, 620, 110, 110, "ดอกไม้จันทน์", "ดอกไม้จันทน์", "flower.png", "flowerHover.png", flowerScript);
        //scene_2
        Item Daddy_Pic = new Item("daddyPic", 842, 520, 230, 350, "รูปภาพพ่อ", "             รูปภาพพ่อ", "fatherPicture.PNG", "fatherPicture.PNG") {

            private boolean isFlower = false;

            @Override
            public void onInteract(Player p) {

                if (isFlower) return;

                if (p.getInventory().isItemSelected("flower")) {
                    
                    DialogueLine[] flowerScript = {
                            new DialogueLine("ตุลย์", "ขอให้ไปสู่สุคตินะครับคุณพ่อ", null, mainTalk),
                    };
                    overlay.setCharacterTransform(50, 0, 706, 941, 1200, 0, 706, 941);
                    overlay.startDialogue(flowerScript, () -> {
                    });
                    system.ObjectiveManager.getInstance().advanceObjective();
                    p.getInventory().removeSelectedItem();
                    isFlower = true;
                    if (fadeTransition != null && !fadeTransition.isFading()) {
                        fadeTransition.executeFade(700, 0, 500, () -> {
                            loadScene("scene_12");
                            DialogueLine[] PlayerScript = {
                                    new DialogueLine("ตุลย์", "บรรยากาศมันแปลกๆ… เหมือนทุกคนกำลังปิดบังอะไร", null, mainIdle2),
                                    new DialogueLine("ตุลย์", "ช่างมันก่อน… ฉันอาจจะแค่เหนื่อยจากการเดินทาง", null, mainIdle2),
                                    new DialogueLine("ตุลย์", "กลับไปนอนก่อนเถอะ… พรุ่งนี้ค่อยคิด", null, mainIdle2)
                            };
                            overlay.setCharacterTransform(50, 0, 706, 941, 1200, 0, 706, 941);
                            overlay.startDialogue(PlayerScript, () -> {
                            });
                        });
                    }

                } else {
                    DialogueLine[] flowerScript = {
                            new DialogueLine("ตุลย์", "ฉันน่าจะต้องไปเอาดอกไม้จันทน์มาวางตรงนี้นะ", null, mainTalk),
                    };
                    overlay.setCharacterTransform(50, 0, 706, 941, 1200, 0, 706, 941);
                    overlay.startDialogue(flowerScript, () -> {
                    });
                }
            }

            @Override
            public boolean isInteractable() {
                return !isFlower;
            }
        };
        //scene_5
        Item Bed = new Item("bed", 110, 490, 842, 315, "เตียง", "เตียงนะจ๊ะ", "bed.png", "bed.png") {
            @Override
            public void onInteract(Player p) {
                startQTETransition("qte_choke");
            }
        };

        //scene_6
        Item ChestOpen = new Item("chestOpen", 1122, 425, 239, 250, "กล่อง", "กล่องพ่อ", "chest.png", "chestHover.png") {
            @Override
            public void onInteract(Player p) {
                ui.DiaryUi.getInstance().openDiary();
                if (chestopen == 0) {
                    system.ObjectiveManager.getInstance().advanceObjective();
                    chestopen++;
                }
            }
        };

        //scene_8
        Item PictureFrame = new Item("PictureFrame", 1418, 508, 67, 85, "รูปภาพครอบครัว", "รูปภาพครอบครัว", "PictureFrame.png", "PictureFrame.png") {

            @Override
            public void onInteract(Player p) {
                ui.MainGameFrame mainFrame = (ui.MainGameFrame) SwingUtilities.getWindowAncestor(SceneManager.this.getGamePanel());
                ImageViewer viewer = new ImageViewer(mainFrame, "/res/familyPic .PNG");
                mainFrame.openMinigame(viewer);
            }
        };
        PictureFrame.setVisible(false);

        Item EmptyPicture = new Item("minigameJigsaw", 1418, 508, 67, 85, "กรอบรูป", "กรอบรูป", "EmptyPicture.png", "EmptyPicture.png") {

            private boolean isSolved = false;

            @Override
            public void onInteract(Player p) {
                if (!isSolved) {
                    ui.MainGameFrame mainFrame = (ui.MainGameFrame) SwingUtilities.getWindowAncestor(SceneManager.this.getGamePanel());
                    JigsawFrame minigame = new JigsawFrame(mainFrame, () -> {
                        isSolved = true;
                        DialogueLine[] EmptyPictureScript = {
                                new DialogueLine("ตุลย์", "รูปนี้มัน... ครอบครัวของเรานี่นา", null, mainTalk),
                        };
                        overlay.setCharacterTransform(50, 0, 706, 941, 1200, 0, 706, 941);
                        overlay.startDialogue(EmptyPictureScript, () -> {});
                        this.setVisible(false);
                        PictureFrame.setVisible(true);
                    });
                    mainFrame.openMinigame(minigame);
                }
            }
        };

        Item Knife2 = createPickUpItem("knife", 400, 530, 70, 70, "มีดอาคม", "มีดอาคม", "knife.png", "knife.png");
        Knife2.setVisible(false);

        Item miniGameClock = new Item("miniGameClock", 340, 220, 169, 593, "นาฬิกา", "", "picClock.png", "picClock.png") {
            private boolean[] isSolved = {false};
            private int[] savedAngles = {0, 90};

            @Override
            public void onInteract(Player p) {
                if (!isSolved[0]) {
                    ui.MainGameFrame mainFrame = (ui.MainGameFrame) SwingUtilities.getWindowAncestor(SceneManager.this.getGamePanel());
                    RotateNariga minigame = new RotateNariga(mainFrame, savedAngles, isSolved, () -> {

                        this.changeImage(334, 223, 195, 593,"picClockOpen.png", "picClockOpen.png");
                        // โชว์มีดอาคม
                        Knife2.setVisible(true);
                    });
                    mainFrame.openMinigame(minigame);
                    AudioManager.stopMusic();
                }
            }

            @Override
            public boolean isInteractable() {
                return !isSolved[0];
            }
        };

        //scene_11
        Item kaFak1 = new Item("kaFak1", 10, 200, 424, 600,"ต้นกาฝาก", "", "kaFak1.png", "kaFak1.png") {
            @Override
            public void onInteract(Player p) {
            }
        };

        Item kaFak2 = new Item("kaFak2", 925, 245, 382, 548,"ต้นกาฝาก", "", "kaFak2.png", "kaFak2.png") {
            @Override
            public void onInteract(Player p) {
            }
        };

        Item kaFak3 = new Item("kaFak4", 1200, 290, 358, 508,"ต้นกาฝาก", "", "kaFak4.png", "kaFak4.png") {
            @Override
            public void onInteract(Player p) {
            }
        };

        Item kaFak4 = new Item("kaFak3", 625, 315, 323, 462,"ต้นกาฝาก", "", "kaFak3.png", "kaFak3.png") {
            @Override
            public void onInteract(Player p) {
            }
        };

        Item kaFak5 = new Item("kaFak5", 1500, 200, 421, 598,"ต้นกาฝาก", "", "kaFak5.png", "kaFak5.png") {
            private boolean isPlucked = false;

            @Override
            public void onInteract(Player p) {
                if (isPlucked) {
                    overlay.startDialogue(new DialogueLine[] {
                            new DialogueLine("ตุลย์", "ฉันเด็ดกาฝากมาแล้ว ไม่ต้องเอาไปเยอะหรอก", null, mainTalk)
                    }, null);
                    return;
                }

                Item Parasite = createPickUpItem("kafak", 500, 700, 100, 100, "กาฝากไม้คูณตายพราย", "กาฝากไม้คูณตายพราย", "kafak.png", "kafakHover.png");
                boolean success = p.getInventory().addItem(Parasite);

                if (success) {
                    isPlucked = true;
                    DialogueLine[] script = {
                            new DialogueLine("ระบบ", "คุณได้รับ [กาฝากไม้คูณตายพราย]", null, null)
                    };
                    overlay.setCharacterTransform(0, 0, 0, 0, 0, 0, 0, 0);
                    overlay.startDialogue(script, null);
                } else {
                    System.out.println("กระเป๋าเต็ม");
                }
            }
            @Override
            public boolean isInteractable() {
                return !isPlucked;
            }
        };

        //scene_15
        Item Door = new Item("miniGameYan", 1850, 365, 72, 708,"ประตู", "", "DoorPic.png", "DoorPic.png") {
            private boolean isSolved = false;

            @Override
            public void onInteract(Player p) {
                ui.MainGameFrame mainFrame = (ui.MainGameFrame) SwingUtilities.getWindowAncestor(SceneManager.this.getGamePanel());
                RotateYan minigame = new RotateYan(mainFrame, () -> {
                    isSolved = true;
                    if (fadeTransition != null && !fadeTransition.isFading()) {
                        fadeTransition.executeFade(700, 0, 500, () -> {
                            loadScene("scene_16");
                            system.ObjectiveManager.getInstance().advanceObjective();
                            DialogueLine[] PlayerScript = {
                                    new DialogueLine("ตุลย์", "!!!!", null, mainIdle2),
                                    new DialogueLine("ตุลย์", "นั่นเสียงอะไรดังมาจากห้องนอนน่ะ", null, mainIdle2)
                            };
                            overlay.setCharacterTransform(50, 0, 706, 941, 1200, 0, 706, 941);
                            overlay.startDialogue(PlayerScript, () -> {
                            });
                        });
                    };
                });
                mainFrame.openMinigame(minigame);
                AudioManager.stopMusic();
            }

            @Override
            public boolean isInteractable() {
                return !isSolved;
            }

        };

        //scene_17
        Item fallenChest = new Item("fallenChest", 900, 715, 214, 90,"กล่องที่ตก", "กล่อง", "fallenBox.png", "fallenBox.png") {

            @Override
            public void onInteract(Player p) {
                if (fadeTransition != null && !fadeTransition.isFading()) {
                    fadeTransition.executeFade(700, 0, 500, () -> {
                        loadScene("scene_18");
                        system.ObjectiveManager.getInstance().advanceObjective();
                        DialogueLine[] PlayerScript = {
                                new DialogueLine("ตุลย์", "....ทำไมมันถึงตกละเนี่ย", null, mainTalk),
                                new DialogueLine("ตุลย์", "ของพ่อรึป่าวนะ...", null, mainTalk),
                                new DialogueLine("ตุลย์", "ในนี้มีอะไรกัน", null, mainTalk),
                        };
                        overlay.setCharacterTransform(50, 0, 706, 941, 1200, 0, 706, 941);
                        overlay.startDialogue(PlayerScript, () -> {
                        });
                    });
                };
            }

        };

        Item Chest = new Item("Chest", 1125, 502, 214, 90,"กล่อง", "กล่อง", "Box.png", "Box.png") {

            private boolean isSolved = false;

            @Override
            public void onInteract(Player p) {
                if (!isSolved) {
                    ui.MainGameFrame mainFrame = (ui.MainGameFrame) SwingUtilities.getWindowAncestor(SceneManager.this.getGamePanel());
                    UnlockBox minigame = new UnlockBox(mainFrame, () -> {
                        if (fadeTransition != null && !fadeTransition.isFading()) {
                            fadeTransition.executeFade(700, 0, 500, () -> {
                                isSolved = true;
                                System.out.println("เย้เล่นผ่านแย้วววววว");

                                loadScene("scene_6");
                                DialogueLine[] PlayerScript = {
                                        new DialogueLine("ตุลย์", "นี่มัน...สมุดบันทึกนี่นา", null, mainTalk),
                                        new DialogueLine("ตุลย์", "พ่อเขียนอะไรไว้กัน", null, mainTalk),
                                        new DialogueLine("ตุลย์", "มีเลขในนี้รึป่าวนะ เผื่อเป็นเศรษฐีพันล้าน อิอิ", null, mainTalk),
                                };
                                overlay.setCharacterTransform(50, 0, 706, 941, 1200, 0, 706, 941);
                                overlay.startDialogue(PlayerScript, () -> {
                                });
                            });
                        };

                    });
                    mainFrame.openMinigame(minigame);
                }
            }

        };

        Item Locker = new Item("locker", 1287, 625, 166, 83, "ลิ้นชัก", "ลิ้นชักว่าวพ่อ", "StorageDrawer.png", "StorageDrawer.png") {
            @Override
            public void onInteract(Player p) {
                ui.MainGameFrame mainFrame = (ui.MainGameFrame) SwingUtilities.getWindowAncestor(SceneManager.this.getGamePanel());
                KonKlong minigame = new KonKlong(mainFrame);
                mainFrame.openMinigame(minigame);
            }
        };






        NPC npcGirl = new NPC("Girl", "เด็กสาวปริศนา", 580, 530, 170, 333, "/res/NPC/NPC1_a.PNG", 12, 622, 1299);
        NPC evil = new NPC("Evil", "ผู้ใหญ่บ้าน",  150, 525, 190, 368, "/res/NPC/Evil_sheet.PNG", 12, 622, 1299);
        NPC npc3 = new NPC("Npc3", "คุณตา", 1350, 530, 170, 333, "/res/NPC/NPC3_sheet.PNG", 12, 622, 1299);
        NPC npc2 = new NPC("Npc2", "คุณยาย", 1500, 535, 170, 333, "/res/NPC/NPC2_sheet.PNG", 12, 622, 1299);
        NPC dadGhost = new NPC("dadGhost", "พ่อ", 1350, 528, 175, 365, "/res/NPC/Dad_sheet.PNG", 12, 622, 1299);
        NPC playerSit = new NPC("PlayerSit", "", 842, 535, 170, 333, "/res/NPC/Sit_sheet.PNG", 12, 622, 1299);

        DialogueLine[] npcGirlScript = {
                new DialogueLine("เด็กสาวในหมู่บ้าน", "เดินทางมาเหนื่อยไหม", girlTalk, mainIdle),
                new DialogueLine("ตุลย์", "ก็นิดหน่อยครับ", girlIdle, mainTalk),
                new DialogueLine("เด็กสาวในหมู่บ้าน", "อืม… พักผ่อนให้สบายแล้วกันนะคืนนี้", girlTalk, mainIdle)
        };
        npcGirl.setVNDialogue(npcGirlScript, overlay);
        npcGirl.setDialogTransform(50, 0, 706, 941, 1200, 0, 706, 941);

        DialogueLine[] evilScript = {
                new DialogueLine("ผู้ใหญ่บ้าน", "กลับมาแล้วเหรอ… ไม่เห็นหน้านานเลยนะ", evilTalk, mainIdle),
                new DialogueLine("ตุลย์", "ครับ… ที่นี่มันดูเปลี่ยนไปนะ", evilIdle, mainTalk),
                new DialogueLine("ผู้ใหญ่บ้าน", "ก็แค่… ทุกอย่างกำลังจะเข้าที่เข้าทางของมันแล้ว", evilTalk, mainIdle),
                new DialogueLine("ผู้ใหญ่บ้าน", "เดี๋ยวเอ็งก็เข้าใจ… เหมือนกับคนอื่นๆ ที่นี่", evilTalk, mainIdle)
        };
        evil.setVNDialogue(evilScript, overlay);
        evil.setDialogTransform(50, 0, 900, 941, 1200, 0, 706, 941);

        DialogueLine[] npc3Script = {
                new DialogueLine("คุณตา", "ตฤนนี่… ไปเร็วผิดปกติไปหน่อยนะ", npc3Talk, npc2Idle),
                new DialogueLine("คุณยาย", "อืม… บางอย่างมันก็มาถึงเวลาแล้ว", npc3Idle, npc2Talk),
                new DialogueLine("คุณตา", "แต่เขาก็ฝืนมานานแล้วนี่", npc3Talk, npc2Idle),
                new DialogueLine("คุณยาย", "ก็เลยเหนื่อยไง… คนเราถ้าฝืนมากไป มันก็ต้องพัก", npc3Idle, npc2Talk)
        };
        npc3.setVNDialogue(npc3Script, overlay);
        npc3.setDialogTransform(50, 0, 706, 941, 1200, 0, 706, 941);

        DialogueLine[] npc2Script = {
                new DialogueLine("คุณตา", "ตฤนนี่… ไปเร็วผิดปกติไปหน่อยนะ", npc3Talk, npc2Idle),
                new DialogueLine("คุณยาย", "อืม… บางอย่างมันก็มาถึงเวลาแล้ว", npc3Idle, npc2Talk),
                new DialogueLine("คุณตา", "แต่เขาก็ฝืนมานานแล้วนี่", npc3Talk, npc2Idle),
                new DialogueLine("คุณยาย", "ก็เลยเหนื่อยไง… คนเราถ้าฝืนมากไป มันก็ต้องพัก", npc3Idle, npc2Talk)
        };
        npc2.setVNDialogue(npc2Script, overlay);
        npc2.setDialogTransform(50, 0, 706, 941, 1200, 0, 706, 941);

        DialogueLine[] dadScript = {
                new DialogueLine("ตุลย์", "พ่อ… นั่นพ่อหรอ", dadIdle, mainTalk),
                new DialogueLine("ตุลย์", "ผมทำสำเร็จแล้ว ทุกอย่างมันจบแล้วนะ", dadIdle, mainTalk),
                new DialogueLine("พ่อ", "พ่อรู้… พ่อเชื่ออยู่แล้ว ว่าเอ็งจะทำมันได้สำเร็จ", dadTalk, mainIdle),
                new DialogueLine("ตุลย์", "ผมน่าจะกลับมาให้ไวกว่านี้… ไม่งั้นพ่อคง…", dadIdle, mainTalk),
                new DialogueLine("พ่อ", "ไม่เป็นไร… แค่นี้ก็พอแล้ว", dadTalk, mainIdle),
                new DialogueLine("พ่อ", "พ่อภูมิใจในตัวเอ็งมากเลยนะ คงหมดห่วงแล้วล่ะ", dadTalk, mainIdle),
                new DialogueLine("ตุลย์", "…ขอบคุณนะพ่อ สำหรับทุกอย่าง", dadIdle, mainTalk),
                new DialogueLine("ตุลย์", "ถึงเวลาที่พ่อต้องไปแล้วหรอ…", dadIdle, mainTalk),
                new DialogueLine("พ่อ", "อืม… จากนี้ไปก็ใช้ชีวิตของเอ็งให้ดีนะลูก", dadTalk, mainIdle)
        };
        dadGhost.setVNDialogue(dadScript, overlay, () -> {
            startEndingSequence();
        });
        dadGhost.setDialogTransform(50, 0, 746, 1000, 1200, 0, 706, 941);

        //พิธีกรรม
        Item slot0 = createRitualSlot(0, 959, 466, "วางของชิ้นที่หนึ่ง");
        Item slot1 = createRitualSlot(1, 959, 653, "วางของชิ้นที่สอง");
        Item slot2 = createRitualSlot(2, 776, 545, "วางของชิ้นที่สาม");
        Item slot3 = createRitualSlot(3, 1143, 545, "วางของชิ้นสุดท้าย");

        //เพิ่มของเข้า Scenes
        Scene scene_1 = scenes.get("scene_1");
        Scene scene_2 = scenes.get("scene_2");
        Scene scene_4 = scenes.get("scene_4");
        Scene scene_5 = scenes.get("scene_5");
        Scene scene_6 = scenes.get("scene_6");
        Scene scene_7 = scenes.get("scene_7");
        Scene scene_8 = scenes.get("scene_8");
        Scene scene_11 = scenes.get("scene_11");
        Scene scene_15 = scenes.get("scene_15");
        Scene scene_17 = scenes.get("scene_17");
        Scene scene_18 = scenes.get("scene_18");
        Scene scene_22 = scenes.get("scene_22");

        if (scene_1 != null) {
            scene_1.addGameObject(Daddy_Pic);
            scene_1.addGameObject(npcGirl);
            scene_1.addGameObject(evil);
            scene_1.addGameObject(npc3);
            scene_1.addGameObject(npc2);
        }
        if (scene_2 != null) {
            scene_2.addGameObject(Flower);
        }
        if (scene_5 != null) {
            scene_5.addGameObject(Bed);
        }
        if (scene_6 != null) {
            scene_6.addGameObject(ChestOpen);
            scene_6.addGameObject(Rosary);
        }
        if (scene_7 != null) {
            scene_7.addGameObject(slot0);
            scene_7.addGameObject(slot1);
            scene_7.addGameObject(slot2);
            scene_7.addGameObject(slot3);
            scene_7.addGameObject(Candle);
        }
        if (scene_8 != null) {
            scene_8.addGameObject(Water);
            scene_8.addGameObject(miniGameClock);
            scene_8.addGameObject(Knife2);
            scene_8.addGameObject(EmptyPicture);
            scene_8.addGameObject(PictureFrame);
        }
        if (scene_11 != null) {
            scene_11.addGameObject(kaFak1);
            scene_11.addGameObject(kaFak2);
            scene_11.addGameObject(kaFak3);
            scene_11.addGameObject(kaFak4);
            scene_11.addGameObject(kaFak5);
        }
        if (scene_15 != null) {
            scene_15.addGameObject(Door);
        }
        if (scene_17 != null) {
            scene_17.addGameObject(fallenChest);
        }
        if (scene_18 != null) {
            scene_18.addGameObject(Chest);
            scene_18.addGameObject(Locker);
        }
        if (scene_22 != null) {
            scene_22.addGameObject(dadGhost);
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
            case "scene_12" : return "หน้าเมรุ";
            case "scene_13" : return "ด้านข้างเมรุ";
            case "scene_14" : return "ห้องนอน";
            case "scene_15" : return "ห้องโถง";
            case "scene_16" : return "ห้องโถง";
            case "scene_17" : return "ห้องนอน";
            case "scene_18" : return "ห้องนอน";
            case "scene_19" : return "ห้องทำพิธี";
            case "scene_20" : return "ห้องนอน";
            case "scene_21" : return "ห้องโถง";
            case "scene_22" : return "หน้าบ้าน";
            case "qte_choke" : return null;
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
                    imgLeft, 1550, 530);
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
                    this, imgRight, 200, 530);
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
        this.retryMode = 1;
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
        if (getCurrentScene() != null) {
            currentScene.update();
        }
        if (overlay != null && overlay.isActive()) {
            overlay.update();
        }

        if (!hasSpokenAllItems && pendingPlayer != null && overlay != null && !overlay.isActive()) {
            Inventory inv = pendingPlayer.getInventory();

            // เช็คว่ามีของสำหรับทำพิธีครบ 4 ชิ้น (มีด, น้ำมนต์, กาฝาก, ลูกประคำ)
            if (inv.hasItem("knife") && inv.hasItem("holyWater") && inv.hasItem("kafak") && inv.hasItem("rosary")) {

                hasSpokenAllItems = true;
                DialogueLine[] allItemsScript = {
                        new DialogueLine("ตุลย์", "ของทำพิธีน่าจะครบแล้วล่ะ...", null, mainIdle2),
                        new DialogueLine("ตุลย์", "รีบเอาไปวางที่แท่นพิธีในห้องกันเถอะ", null, mainTalk)
                };

                overlay.setCharacterTransform(50, 0, 706, 941, 1200, 0, 706, 941);
                overlay.startDialogue(allItemsScript, null);
            }
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

    private void managePlayBGM(String sceneID) {
        if (sceneID.equals("scene_2") && !gamePanel.getIsStartGame()) {
            AudioManager.playMusic("src/res/sound/PlayingMusicBG.wav", -15.0f);
            System.out.println("เข้าเงื่อนไข เริ่มเกมใหม่");
            return;
        }
        switch (sceneID) {
            case "scene_1", "scene_2" -> AudioManager.resumeBGMusic("src/res/sound/PlayingMusicBG.wav", -15.0f);
            case "scene_12", "scene_13", "scene_4" -> AudioManager.resumeBGMusic("src/res/sound/BGM2.wav", 0.0f);
            case "scene_3" -> AudioManager.playSFX("src/res/sound/StartCar.wav", 0.0f);
            case "qte_choke" -> AudioManager.stopMusic();
            case "scene_14", "scene_15", "scene_17", "scene_18", "scene_6", "scene_8", "scene_7"
                    -> AudioManager.resumeBGMusic("src/res/sound/BGM14.wav", 0.0f);
            case "scene_16" -> {
                AudioManager.playSFX("src/res/sound/ItemDropSound.wav", 10.0f);
                AudioManager.resumeBGMusic("src/res/sound/BGM14.wav", 0.0f);
            }
            case "scene_9", "scene_10", "scene_11" -> AudioManager.resumeBGMusic("src/res/sound/BGM9.wav", 0.0f);
            default -> System.out.println("ระบบ PhayBGM at SceneManager : ยังไม่ได้ตั้งค่า " + sceneID);
        }
    }

    public void setGamePanel(GamePanel gp) {
        this.gamePanel = gp;
    }

    public GamePanel getGamePanel() {
        return this.gamePanel;
    }

    public void resetManagerStates() {
        isFirstTimeScene3 = true;
        isFirstTimeScene6 = true;
        isFirstTimeScene11 = true;
        isFirstTimeScene12 = true;
        isFirstTimeScene14 = true;
        isFirstTimeScene16 = true;
        isFirstTimeScene17 = true;
        isFirstTimeScene18 = true;
        isFirstTimeScene19 = true;

        retryMode = 1;
        chestopen = 0;

        //ล้างรายชื่อของในแท่นพิธีกรรมให้กลับมาว่างเปล่า
        for (int i = 0; i < ritualItems.length; i++) {
            ritualItems[i] = "";
            ritualItemNames[i] = "";
        }

        if (scenes != null) {
            scenes.clear();
            initScenes();
        }
    }
}

