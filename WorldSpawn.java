
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public class WorldSpawn {

    private final ResourceCatalog resourceCatalog;
    private final RandomSpawnCatalog randomSpawnCatalog;
    private final FurnitureCatalog furnitureCatalog;
    private final TileManager tm;
    private final Random r = new Random();
    private final ArrayList<PlacedItem> groundItemsSpawn = new ArrayList<>();
    private final ArrayList<PlacedResource> groundResourceSpawn = new ArrayList<>();
    private final ArrayList<PlacedItem> resourceDrop = new ArrayList<>();
    private final ArrayList<PlacedFurniture> placedFurniture = new ArrayList<>();
    private final GamePanel gp;


    public WorldSpawn(TileManager tm, GamePanel gp, ItemCatalog itemCatalog) {
        this.tm = tm;
        this.gp = gp;
        this.resourceCatalog = new ResourceCatalog(itemCatalog);
        this.randomSpawnCatalog = new RandomSpawnCatalog(itemCatalog);
        this.furnitureCatalog = new FurnitureCatalog(itemCatalog);

        generateItemsSpawns(30);
        generateResourceSpawns(30);
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
        for (PlacedFurniture z : placedFurniture) {
                if (col == z.getBounds().getX() && row == z.getBounds().getY()) {
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
    public void PlaceFurniture(int x, int y, Item selectedItem){
        if(!isOccupied(x, y)){
            System.out.println("hej1");
            FurnitureType currentFurniture = null;
            for (int i = 0; i < furnitureCatalog.getSize(); i++) {
                if (selectedItem.getItemName().equals(furnitureCatalog.getFurniture(i).name)) {
                    currentFurniture = furnitureCatalog.getFurniture(i);
                    System.out.println("hej2");
                }
            }
            if (currentFurniture != null) {
                System.out.println("hej3");
                placedFurniture.add(new PlacedFurniture(currentFurniture, new Point(x*32,y*32)));
                tm.setIsBlocked(x*32,y*32);
            }
        }
        
    }
    private void generateResourceSpawns(int amount) {
        for (int i = 0; i < amount; i++) {
            PlacedResource resource = new PlacedResource(resourceCatalog.getResource(r.nextInt(0, resourceCatalog.getSize())), findSpawnPosition());
            tm.setIsBlocked((int) resource.getBounds().getX(), (int) resource.getBounds().getY());
            groundResourceSpawn.add(resource);
        }
    }

    private void generateItemsSpawns(int amount) {
        for (int i = 0; i < amount; i++) {
            PlacedItem item = new PlacedItem(randomSpawnCatalog.getItem(r.nextInt(0, randomSpawnCatalog.getSize())), findSpawnPosition());
            groundItemsSpawn.add(item);
        }
    }

    public boolean hitResource(int targetCol, int targetRow, ItemType playerTool, int toolDamage) {
        for (PlacedResource i : groundResourceSpawn) {
            if (i.getResource().requiredTool == playerTool && i.getBounds().getX()/32 == targetCol && i.getBounds().getY()/32 == targetRow) {
                i.setHealth(-toolDamage);
                if (i.getHealth() < 1) {
                    gp.playSound(SoundEffect.RESOURCEBREAK);
                    dropItem(i, targetCol, targetRow);
                    tm.removeIsBlocked((int) i.getBounds().getX(), (int) i.getBounds().getY());
                    groundResourceSpawn.remove(i);
                    break;
                }
                return true;
            }
        }
        return false;
    }

    private void dropItem(PlacedResource resource, int dropCol, int dropRow) {
        for(int i = 0; i < resource.getDropAmount(); i++){
            resourceDrop.add(new PlacedItem(resource.getResource().drop, new Point(dropCol*32+r.nextInt(-32,32), dropRow*32+r.nextInt(-32,32))));
        }
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
        for (PlacedItem i : resourceDrop) {
            if (i.getBounds().intersects(player.getCollison())) {
                ge.playSound(SoundEffect.PICKUP);
                player.getInventory().addCopyOf(i.getItem());
                resourceDrop.remove(i);
                break;
            }
        }
    }

    public void draw(Graphics2D g2, Camera camera) {
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        for (PlacedResource i : groundResourceSpawn) {
            
            g2.drawImage(i.getResource().image, (int) i.getBounds().getX() - (int) camera.getTx(), (int) i.getBounds().getY() - (int) camera.getTy(), null);
        }
        for(PlacedItem i : resourceDrop){
            g2.drawImage(i.getItem().getHeldIcon(), (int) i.getBounds().getX() - (int) camera.getTx(), (int) i.getBounds().getY() - (int) camera.getTy(), null);
        }
        for (PlacedItem i : groundItemsSpawn) {
            g2.drawImage(i.getItem().getHeldIcon(), (int) i.getBounds().getX() - (int) camera.getTx(), (int) i.getBounds().getY() - (int) camera.getTy(), null);
        }
        for (PlacedFurniture i : placedFurniture) {
            g2.drawImage(i.getFurniture().image, (int) i.getBounds().getX() - (int) camera.getTx(), (int) i.getBounds().getY() - (int) camera.getTy(), null);
        }
        
    }
}
