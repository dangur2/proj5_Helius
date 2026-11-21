
import java.awt.Graphics2D;
import java.awt.Point;

public class Bridge extends PlacedFurniture{
    public Bridge(FurnitureType type, int x, int y) {
        super(type, new Point(x,y));
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Chest CurrentChest() {
        return null;
    }

    @Override
    public void Interact(Player player, Item selectedItem) {

    }

    @Override
    public void tick(double dt) {
  
    }

    @Override
    public void draw(Graphics2D g2, Camera camera, int x, int y) {
        g2.drawImage(getImage(), (int) x - (int) camera.getTx(), (int) y - (int) camera.getTy(), null);
    }
}
