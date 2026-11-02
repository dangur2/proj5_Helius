
import java.awt.Rectangle;
import java.awt.geom.Point2D;

public class PlacedFurniture {
    private final FurnitureType furniture;
    private final Rectangle bounds;
    private int currentHealth;
    private int useTime;

    public PlacedFurniture(FurnitureType furniture, Point2D location) {
        this.furniture = furniture;
        this.currentHealth = furniture.health;
        this.bounds = new Rectangle((int) location.getX(), (int) location.getY(), 32,32);
        this.useTime = furniture.useTime;
    }

    public FurnitureType getFurniture(){
        return furniture;
    }
    public Rectangle getBounds(){
        return bounds;
    }
    public int getHealth(){
        return currentHealth;
    }
    public void setHealth(int x){
        this.currentHealth += x;
    }
    public int getUseTime(){
        return useTime;
    }
    public void increaseUseTime(int x){
        this.useTime += x;
    }
}
