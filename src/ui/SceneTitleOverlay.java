package ui;

import java.awt.*;

public class SceneTitleOverlay {
    private String title = "";
    private int state = 0, alpha = 0, currentLineWidth = 0, maxLineWidth = 0, waitTime = 0;
    private Font font;
    private final int FADE_SPEED = 3;
    private final int LINE_SPEED = 8;
    private final int WALT_TIME = 130;

    public SceneTitleOverlay(Font font) {
        this.font = font;
    }

    public void showTitle(String newTitle) {
        if (newTitle == null || newTitle.isEmpty()) return;
        this.title = newTitle;
        this.alpha = 0;
        this.currentLineWidth = 0;
        this.waitTime = 0;
        this.state = 1;
    }

    public void update() {
        if (state == 1) {
            alpha += FADE_SPEED;
            currentLineWidth += LINE_SPEED;

            if (alpha >= 255) alpha = 255;
            if (currentLineWidth >= maxLineWidth) currentLineWidth = maxLineWidth;

            if (alpha == 255 && currentLineWidth == maxLineWidth) {
                state = 2;
            }
        }

        else if (state == 2) {
            waitTime++;
            if (waitTime >= WALT_TIME) {
                state = 3;
                waitTime = 0;
            }
        }
        else if (state == 3) {
            alpha -= FADE_SPEED;
            currentLineWidth -= LINE_SPEED;

            if (alpha <= 0) alpha = 0;
            if (currentLineWidth <= 0) currentLineWidth = 0;

            if (alpha == 0 && currentLineWidth == 0) {
                state = 0;
            }
        }
    }

    public void render(Graphics2D g2d, int screenWidth) {
        if (state == 0 || title.isEmpty()) return;

        g2d.setFont(font.deriveFont(Font.BOLD, 80f));
        FontMetrics fm = g2d.getFontMetrics();

        int textWidth = fm.stringWidth(title);
        maxLineWidth = textWidth + 120;

        int textX = (screenWidth - textWidth) / 2;
        int textY = 250;

        g2d.setColor(new Color(255, 255, 255, alpha));
        g2d.drawString(title, textX, textY);

        int LineY = textY + 25;
        int lineStartX = (screenWidth / 2) - (currentLineWidth / 2);
        int LineEndX = (screenWidth / 2) + (currentLineWidth / 2);

        g2d.setStroke(new BasicStroke(4));
        g2d.drawLine(lineStartX, LineY, LineEndX, LineY);

    }
}
