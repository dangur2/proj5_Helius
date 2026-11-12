
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class PlacedFurniture {

    private final FurnitureType furniture;
    private int currentHealth;
    private final int ySort;
    private final Rectangle collArea;

    public PlacedFurniture(FurnitureType furniture, Point location) {
        this.furniture = furniture;
        this.currentHealth = furniture.health;
        this.ySort = furniture.ySortOffset;
        this.collArea = new Rectangle((int)location.getX()+(int)furniture.collArea.getX(),(int)location.getY()+(int)furniture.collArea.getY(), (int)furniture.collArea.getWidth(),(int)furniture.collArea.getHeight());
    }

    public FurnitureType getFurniture() {
        return furniture;
    }

    public int getHealth() {
        return currentHealth;
    }

    public int getYsort(){
        return ySort;
    }
    public Rectangle getCollArea(){
        return collArea;
    }

    public void setHealth(int x) {
        this.currentHealth += x;
    }

    public BufferedImage getImage() {
        return furniture.image;
    }

    public BufferedImage getUseImage() {
        return furniture.useImage;
    }
    public abstract boolean isEmpty();

    public abstract Chest CurrentChest();

    public abstract void Interact(Player player, Item selectedItem);

    public abstract void tick(double dt);

    public abstract void draw(Graphics2D g2, Camera camera, int x, int y);
}
