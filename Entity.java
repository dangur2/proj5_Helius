
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    public double speed;
    public double x,y;
    public double worldX,worldY;
    public BufferedImage up1,up2,up3,up4,down1,down2,down3,down4,left1,left2,left3,left4,right1,right2,right3,right4,idle1,idle2,idle3,idle4,attRight1,attRight2,attRight3,attRight4,attLeft1,attLeft2,attLeft3,attLeft4,tileHighLight;
    public enum Direction{UP,DOWN,RIGHT,LEFT,IDLE,LOST};
    public Direction direction;
    public enum attackDirection{RIGHT,LEFT};
    public attackDirection attDir;
    public int playerSizeX = 32;
    public int playerSizeY = 32;
    public Rectangle collArea,attArea;
    public boolean playerCollision = false;

    public int spriteCounter = 0;
    public int walkNum = 1, attNum = 1;

}
