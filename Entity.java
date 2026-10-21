
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    public double speed;
    public double x,y;
    public double worldX,worldY;
    public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2,idle1,idle2,lost1,lost2,attUp,attDown,attRight,attLeft;
    public enum Direction{UP,DOWN,RIGHT,LEFT,IDLE,LOST};
    public Direction direction;
    public enum attackDirection{UP,DOWN,RIGHT,LEFT};
    public attackDirection attDir;
    public int playerSizeX = 32;
    public int playerSizeY = 32;
    public Rectangle collArea,attArea;
    public boolean playerCollision = false;

    public int spriteCounter = 0;
    public int spriteNum = 1;

}
