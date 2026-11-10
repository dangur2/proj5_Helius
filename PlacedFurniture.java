
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class PlacedFurniture {

    private final FurnitureType furniture;
    private int currentHealth;

    public PlacedFurniture(FurnitureType furniture) {
        this.furniture = furniture;
        this.currentHealth = furniture.health;
    }

    public FurnitureType getFurniture() {
        return furniture;
    }

    public int getHealth() {
        return currentHealth;
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

    public abstract Chest CurrentChest();

    public abstract void Interact(Player player, Item selectedItem);

    public abstract void tick(double dt);

    public abstract void draw(Graphics2D g2, Camera camera, int x, int y);
}
