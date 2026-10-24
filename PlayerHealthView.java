
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class PlayerHealthView {

    private BufferedImage healthBarSprite, staminaBarSprite;


    public PlayerHealthView() {
        loadSprites();
    }
    private void loadSprites() {
        try {
            healthBarSprite = ImageIO.read(getClass().getResourceAsStream("/assets/Font/healthBar.png"));
            staminaBarSprite = ImageIO.read(getClass().getResourceAsStream("/assets/Font/staminaBar.png"));
        } catch (Exception e) {
        }
    }

    public void draw(Graphics2D g2, int playerHealth, int playerStamina) {
        g2.setFont(new Font("MONOSPACED", Font.BOLD, 16));
        
        //healthbar + 3d text
        double fillHealth = ((double) playerHealth / 100) * (double) 146;
        g2.drawImage(healthBarSprite, 10, 20,null);
        g2.setColor(Color.red);
        g2.fillRect(12, 22, (int) fillHealth, 18);

        g2.setColor(Color.black);
        g2.drawString("Health", 12, 18);
        g2.setColor(Color.white);
        g2.drawString("Health", 11, 18);
        
        //staminabar + 3d text
        double fillStamina = ((double) playerStamina / 100) * (double) 146;
        g2.drawImage(staminaBarSprite, 10, 60,null);
        g2.setColor(Color.blue);
        g2.fillRect(12, 62, (int) fillStamina, 18);

        g2.setColor(Color.black);
        g2.drawString("Stamina", 12, 58);
        g2.setColor(Color.white);
        g2.drawString("Stamina", 11, 58);
        
        
    }
}
