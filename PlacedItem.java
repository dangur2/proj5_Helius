
import java.awt.Rectangle;
import java.awt.geom.Point2D;

public class PlacedItem {

    private final Item item;
    private final Rectangle bounds;

    public PlacedItem(Item item, Point2D location){
        this.item = item;
        this.bounds = new Rectangle((int) location.getX(), (int) location.getY(), 32,32);
    }

    public Item getItem() {
        return item;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
