
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class CreateUI extends JFrame{
    BufferedImage icon;
    public CreateUI(){
        loadIcon();
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setTitle("Helius");
        setIconImage(icon);
        setVisible(true);
        
    }

    private void loadIcon() {
        try {
            icon = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/grass_tile.png"));
        } catch (Exception e) {
        }
    }
}
