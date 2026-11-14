
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ResourceType {
    public String name;
    public BufferedImage image;
    public int health;
    public Item drop;
    public int dropAmount;
    public ItemType requiredTool;
    public int ySortOffset;
    public Rectangle collArea;
    public ArrayList<Integer> spawnableTiles = new ArrayList<>();
}
