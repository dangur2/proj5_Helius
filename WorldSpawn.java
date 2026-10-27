
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public class WorldSpawn {

    private final ResourceCatalog rc = new ResourceCatalog();
    private final ItemCatalog ic = new ItemCatalog();
    private final TileManager tm;
    private final Random r = new Random();
    private final ArrayList<PlacedItem> groundItemsSpawn = new ArrayList<>();
    private final ArrayList<PlacedResource> groundResourceSpawn = new ArrayList<>();

    public WorldSpawn(TileManager tm) {
        this.tm = tm;

        generateItemsSpawns(50);
        generateResourceSpawns(50);
    }

    private boolean isOccupied(int col, int row) {
        for (PlacedItem i : groundItemsSpawn) {
            if (col == i.getBounds().getX() && row == i.getBounds().getY()) {
                    return true;
                }
        }
        for (PlacedResource j : groundResourceSpawn) {
                if (col == j.getBounds().getX() && row == j.getBounds().getY()) {
                    return true;
                }
            }
        return false;
    }

    private Point2D findSpawnPosition() {
        for (int i = 0; i < 100; i++) {
            Point2D spawn = tm.findFreePosition();
            if (!isOccupied((int) spawn.getX(), (int) spawn.getY())) {
                return new Point((int)spawn.getX(),(int)spawn.getY());
            }
        }
        return null;
    }

    private void generateResourceSpawns(int amount) {
        for (int i = 0; i < amount; i++) {
            PlacedResource resource = new PlacedResource(rc.getResource(r.nextInt(0, rc.getSize())), findSpawnPosition());
            groundResourceSpawn.add(resource);
            for (PlacedResource z : groundResourceSpawn) {
                System.out.println("x " + z.getBounds().getX());
                System.out.println("y " + z.getBounds().getY());
            }
        }
    }

    private void generateItemsSpawns(int amount) {
        for (int i = 0; i < amount; i++) {
            PlacedItem item = new PlacedItem(ic.getItem(r.nextInt(0, ic.getSize())), findSpawnPosition());
            groundItemsSpawn.add(item);
        }
    }

    public boolean hitResource(int targetCol, int targetRow, ItemType playerTool, int toolDamage) {
        for (PlacedResource i : groundResourceSpawn) {
            if (i.getResource().requiredTool == playerTool && i.getBounds().getX()/32 == targetCol && i.getBounds().getY()/32 == targetRow) {
                i.setHealth(-toolDamage);
                System.out.println(i.getHealth());
                if (i.getHealth() < 1) {
                    dropItem(i, targetCol, targetRow);
                    groundResourceSpawn.remove(i);
                    break;
                }
                return true;
            }
        }
        return false;
    }

    private void dropItem(PlacedResource resource, int dropCol, int dropRow) {
        groundItemsSpawn.add(new PlacedItem(resource.getResource().drop, new Point(dropCol + r.nextInt(1, 3), dropRow + r.nextInt(1, 3))));
    }

    public void handleCollision(Player player, boolean ePressed, GamePanel ge) {
        for (PlacedItem i : groundItemsSpawn) {
            if (i.getBounds().intersects(player.getCollison()) && ePressed) {
                ge.playSound(SoundEffect.PICKUP);
                player.getInventory().addCopyOf(i.getItem());
                groundItemsSpawn.remove(i);
                break;
            }
        }
    }

    public void draw(Graphics2D g2, Camera camera) {
        for (PlacedResource i : groundResourceSpawn) {
            g2.drawImage(i.getResource().image, (int) i.getBounds().getX() - (int) camera.getTx(), (int) i.getBounds().getY() - (int) camera.getTy(), null);
        }
        for (PlacedItem i : groundItemsSpawn) {
            g2.drawImage(i.getItem().getHotbarIcon(), (int) i.getBounds().getX() - (int) camera.getTx(), (int) i.getBounds().getY() - (int) camera.getTy(), null);
        }
        
    }
}
