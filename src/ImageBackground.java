import javax.swing.*;
import java.awt.*;

public class ImageBackground extends JPanel {
        private Image bgImage;

        public ImageBackground(String path) {
            this.bgImage = new ImageIcon(path).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        }
}
