
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class FurnitureType {
    public String name;
    public BufferedImage image, useImage;
    public int health, useTime;
    public Item drop;
    public ItemType requiredTool;
    public boolean collision;
    public FurnitureInteractType type;
    public int ySortOffset;
    public Rectangle collArea;
}
