
import java.awt.Point;



public class PlacedResource {
    
    private final ResourceType resource;
    private int currentHealth;
    private final int dropAmount;
    private int ySort;
    private Point collArea;

    public PlacedResource(ResourceType resource) {
        this.resource = resource;
        this.currentHealth = resource.health;
        this.dropAmount = resource.dropAmount;
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
}


