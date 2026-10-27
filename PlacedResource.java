
import java.awt.Rectangle;
import java.awt.geom.Point2D;

public class PlacedResource {
    
    private final ResourceType resource;
    private final Rectangle bounds;
    private int currentHealth;

    public PlacedResource(ResourceType resource, Point2D location) {
        this.resource = resource;
        this.currentHealth = resource.health;
        this.bounds = new Rectangle((int) location.getX(), (int) location.getY(), 32,32);
    }

    public ResourceType getResource(){
        return resource;
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
}


