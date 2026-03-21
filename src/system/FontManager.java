package system;
import java.awt.*;
import java.io.*;

public class FontManager {
    public static Font customFont;

    static {
        try {
            InputStream is = FontManager.class.getResourceAsStream("/res/Font/DSNSM__.TTF");
            customFont = Font.createFont(Font.TRUETYPE_FONT, is);
            System.out.println("โหลดได้จ้าา");
        }
        catch (IOException | FontFormatException ex) {
            ex.printStackTrace();
        }
    }
}

