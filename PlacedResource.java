
import java.awt.Point;
import java.awt.Rectangle;



public class PlacedResource {
    
    private final ResourceType resource;
    private int currentHealth;
    private final int dropAmount;
    private final int ySort;
    private Rectangle collArea;

    public PlacedResource(ResourceType resource, Point location) {
        this.resource = resource;
        this.currentHealth = resource.health;
        this.dropAmount = resource.dropAmount;
        this.collArea = new Rectangle((int) resource.collArea.getX()+(int)location.getX(), (int)resource.collArea.getY()+(int)location.getY(),(int)resource.collArea.getWidth(),(int)resource.collArea.getHeight());
        this.ySort = resource.ySortOffset;
    }

    public ResourceType getResource(){
        return resource;
    }
    public int getHealth(){
        return currentHealth;
    }
    public void setHealth(int x){
        this.currentHealth += x;
    }
    public int getDropAmount(){
        return dropAmount;
    }

    public int getySort() {
        return ySort;
    }

    public Rectangle getCollArea() {
        return collArea;
    }
    public void removeCollare(){
        this.collArea = null;
    }
}


