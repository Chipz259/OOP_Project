package system;
import java.awt.*;
import java.io.*;

public class FontManager {
    public static Font customFont;
    public static Font pspimpdeedIIIFont;
    public static Font pspimpdeed;
    public static Font monkeyRegular;
    public static Font monkeyBold;

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

            InputStream is4 = FontManager.class.getResourceAsStream("/res/Font/iannnnn-MONKEY Regular.ttf");
            if (is4 != null) {
                monkeyRegular = Font.createFont(Font.TRUETYPE_FONT, is4);
            }

            InputStream is5 = FontManager.class.getResourceAsStream("/res/Font/iannnnn-MONKEY Bold.ttf");
            if (is5 != null) {
                monkeyBold = Font.createFont(Font.TRUETYPE_FONT, is5);
            }
        }
        catch (IOException | FontFormatException ex) {
            ex.printStackTrace();
        }
    }
}

