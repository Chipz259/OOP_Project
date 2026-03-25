package system;
import java.awt.*;
import java.io.*;

public class FontManager {
    public static Font customFont;
    public static Font pspimpdeedIIIFont;
    public static Font pspimpdeed;

    static {
        try {
            InputStream is1 = FontManager.class.getResourceAsStream("/res/Font/DSNSM__.TTF");
            if (is1 != null) {
                customFont = Font.createFont(Font.TRUETYPE_FONT, is1);
            }

            InputStream is2 = FontManager.class.getResourceAsStream("/res/Font/PspimpdeedIII.ttf");
            if (is2 != null) {
                pspimpdeedIIIFont = Font.createFont(Font.TRUETYPE_FONT, is2);
            }

            InputStream is3 = FontManager.class.getResourceAsStream("/res/Font/Pspimpdeed.ttf");
            if (is3 != null) {
                pspimpdeed = Font.createFont(Font.TRUETYPE_FONT, is3);
            }
        }
        catch (IOException | FontFormatException ex) {
            ex.printStackTrace();
        }
    }
}

