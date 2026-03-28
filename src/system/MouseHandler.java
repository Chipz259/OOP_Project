package system;
import entities.*;

import java.awt.event.*;
import java.awt.*;

public class MouseHandler extends MouseAdapter {
    private GamePanel gamePanel;
    public MouseHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // ==========================================
    // 1. จัดการตอน "คลิกเมาส์"
    // ==========================================
    @Override
    public void mousePressed(MouseEvent e) {

        // --- 1. เช็คเฟดหน้าจอ ---
        if (gamePanel.fadeTransition != null && gamePanel.fadeTransition.isFading()) {
            return;
        }

        // --- 2. เช็คหน้าต่างพูดคุย (Overlay) ---
        if (gamePanel.sceneManager.getOverlay() != null && gamePanel.sceneManager.getOverlay().isActive()) {
            gamePanel.sceneManager.getOverlay().handleMouseClick();
            return;
        }

        // --- 3. เช็คคลิกกระเป๋า ---
        if (gamePanel.mainPlayer != null && gamePanel.mainPlayer.getInventory() != null) {
            // ดึง getWidth() กับ getHeight() ผ่าน gamePanel
            boolean clickedInventory = gamePanel.mainPlayer.getInventory().handleClick(e.getX(), e.getY(), gamePanel.getWidth(), gamePanel.getHeight());
            if (clickedInventory == true) {
                return; // คลิกโดน = หยุดการทำงาน
            }
        }

        // --- 4. เช็คคลิกไอเทมในฉาก ---
        if (gamePanel.sceneManager.getCurrentScene() != null) {
            for (GameObject obj : gamePanel.sceneManager.getCurrentScene().getObjectsInScene()) {

                if (obj.getHitbox().contains(e.getPoint()) && obj.isVisible() && obj instanceof Interactable
                        && ((Interactable) obj).isInteractable()) {

                    int playerCenter = gamePanel.mainPlayer.getX() + (gamePanel.mainPlayer.getWidth() / 2);
                    int objCenter = obj.getX() + (obj.getWidth() / 2);

                    int distance = Math.abs(playerCenter - objCenter);

                    if (distance < 250) {
                        ((Interactable) obj).onInteract(gamePanel.mainPlayer);
                        gamePanel.targetItem = null;
                        break;
                    } else {
                        // ถ้าคลิกไกลเกินจะแสดงการแจ้งเตือน
                        System.out.println("ระบบ: กำลังเดินไปเก็บ " + obj);
                        gamePanel.targetItem = obj;
                        break;
                    }
                }
            }
        }
    }

    // ==========================================
    // 2. จัดการตอน "ขยับเมาส์" (Hover)
    // ==========================================
    @Override
    public void mouseMoved(MouseEvent e) {
        boolean isHoveringAnyItem = false;

        if (gamePanel.sceneManager != null && gamePanel.sceneManager.getCurrentScene() != null) {

            for (GameObject obj : gamePanel.sceneManager.getCurrentScene().getObjectsInScene()) {

                if (!obj.isVisible()) continue;

                if (obj instanceof Interactable) {
                    Interactable interactObj = (Interactable) obj;

                    if (obj.getHitbox().contains(e.getPoint()) && interactObj.isInteractable() && obj.isVisible()) {
                        isHoveringAnyItem = true;
                        if (obj instanceof Item) {
                            ((Item) obj).setHovered(true);
                        }
                        else if (obj instanceof scenes.Door) {
                            ((scenes.Door) obj).setIsHovered(true);
                        }
                        else if (obj instanceof NPC) {
                            ((NPC) obj).setHovered(true);
                        }
                    } else {
                        if (obj instanceof Item) {
                            ((Item) obj).setHovered(false);
                        }
                        else if (obj instanceof scenes.Door) {
                            ((scenes.Door) obj).setIsHovered(false);
                        }
                        else if (obj instanceof NPC) {
                            ((NPC) obj).setHovered(false);
                        }
                    }
                }
            }
        }

        // สลับรูปเมาส์ (ต้องเรียก setCursor ผ่าน gamePanel)
        if (isHoveringAnyItem == true) {
            gamePanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } else {
            gamePanel.setCursor(Cursor.getDefaultCursor());
        }
        if (gamePanel.mainPlayer != null && gamePanel.mainPlayer.getInventory() != null) {
            gamePanel.mainPlayer.getInventory().handleHover(e.getX(), e.getY(), gamePanel.getWidth(), gamePanel.getHeight());
        }
    }
}