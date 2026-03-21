package system;

import java.awt.image.BufferedImage;

public class DialogueLine {
    public String speackerName, text;
    public BufferedImage leftImage, rightImage;

    public DialogueLine(String speackerName, String text, BufferedImage leftImage, BufferedImage rightImage) {
        this.speackerName = speackerName;
        this.text = text;
        this.leftImage = leftImage;
        this.rightImage = rightImage;
    }
}
