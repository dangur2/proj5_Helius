
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class WorldSpawn {

    private final ResourceCatalog resourceCatalog;
    private final RandomSpawnCatalog randomSpawnCatalog;
    private final FurnitureCatalog furnitureCatalog;
    private final TileManager tm;
    private final Random r = new Random();
    private final ArrayList<PlacedItem> groundItemsSpawn = new ArrayList<>();
    private final HashMap<Point, PlacedResource> groundResourceSpawn = new HashMap<>();
    private final ArrayList<PlacedItem> resourceDrop = new ArrayList<>();
    private final HashMap<Point, PlacedFurniture> placedFurniture = new HashMap<>();
    private PlacedFurniture selectedFurniture;
    private final GamePanel gp;
    private final Player player;
    private final FurnaceRecipeCatalog furnaceRecipeCatalog;

    public WorldSpawn(TileManager tm, GamePanel gp, ItemCatalog itemCatalog, Player player) {
        this.tm = tm;
        this.gp = gp;
        this.resourceCatalog = new ResourceCatalog(itemCatalog);
        this.randomSpawnCatalog = new RandomSpawnCatalog(itemCatalog);
        this.furnitureCatalog = new FurnitureCatalog(itemCatalog);
        this.furnaceRecipeCatalog = new FurnaceRecipeCatalog(itemCatalog);
        this.player = player;

        generateItemsSpawns(50);
        generateResourceSpawns(40);
    }

    public void tick(double dt) {
        for (Point i : placedFurniture.keySet()) {
            placedFurniture.get(i).tick(dt);
        }
    }

    public void Interact(int targetCol, int targetRow, Item selectedItem) {
        selectedFurniture = placedFurniture.get(new Point(targetCol * gp.tileSize, targetRow * gp.tileSize));

        if (selectedFurniture == null) {
            return;
        }

        selectedFurniture.Interact(player, selectedItem);
    }

    private boolean isOccupied(int col, int row) {
        for (PlacedItem i : groundItemsSpawn) {
            if (col == i.getBounds().getX() && row == i.getBounds().getY()) {
                return true;
            }
        }
        for (Point j : groundResourceSpawn.keySet()) {
            if (col == j.getX() && row == j.getY()) {
                return true;
            }
        }
        for (Point z : placedFurniture.keySet()) {
            if (col == z.getX() && row == z.getY()) {
                return true;
            }
        }
        return false;
    }

    private Point findSpawnPosition(ArrayList<Integer> spawnableTiles) {
        for (int i = 0; i < 100; i++) {
            Point2D spawn = tm.findFreePosition();
            Tile selectedTile = tm.getTileNum((int)spawn.getX()/32, (int) spawn.getY()/32);
            if (!isOccupied((int) spawn.getX(), (int) spawn.getY()) && tm.canResourceSpawn(spawnableTiles, selectedTile)) {
                return new Point((int) spawn.getX(), (int) spawn.getY());
            }
        }
        return null;
    }

    public boolean PlaceFurniture(int x, int y, Item selectedItem) {
        PlacedFurniture currentFurniture = null;
        if (!isOccupied(x * gp.tileSize, y * gp.tileSize)) {
            if (selectedItem.getItemType().equals(ItemType.FURNITURE)) {
                switch (selectedItem.getItemName()) {
                    case "Furnace" -> {
                        currentFurniture = new Furnace(furnitureCatalog.getFurniture(0), furnaceRecipeCatalog,x*gp.tileSize,y*gp.tileSize);
                    }
                    case "Chest" -> {
                        currentFurniture = new Chest(furnitureCatalog.getFurniture(1),x*gp.tileSize,y*gp.tileSize);
                    }
                }
            }
            if (currentFurniture != null) {
                placedFurniture.put(new Point(x * gp.tileSize, y * gp.tileSize), currentFurniture);
                tm.setIsBlocked(currentFurniture.getCollArea());
                return true;
            }
        }
        return false;

    }

    private void generateResourceSpawns(int amount) {
        for (int i = 0; i < amount; i++) {
            ResourceType resource = resourceCatalog.getResource(r.nextInt(0,resourceCatalog.getSize()));
            Point currPoint = findSpawnPosition(resource.spawnableTiles);
            PlacedResource groundResource = new PlacedResource(resource, currPoint);
            groundResourceSpawn.put(currPoint, groundResource);
            tm.setIsBlocked(groundResource.getCollArea());
        }
    }

    private void generateItemsSpawns(int amount) {
        ArrayList<Integer> spawnableTiles = new ArrayList<>();
        spawnableTiles.addAll(List.of(0,4));
        for (int i = 0; i < amount; i++) {
            Item item = randomSpawnCatalog.getItem(r.nextInt(0,randomSpawnCatalog.getSize()));
            Point currPoint = findSpawnPosition(spawnableTiles);
            PlacedItem itemSpawn = new PlacedItem(item, currPoint);
            groundItemsSpawn.add(itemSpawn);
        }
    }

    public boolean hitResource(int targetCol, int targetRow, ItemType playerTool, int toolDamage) {
        Rectangle hitTile = new Rectangle(targetCol*gp.tileSize, targetRow*gp.tileSize, gp.tileSize, gp.tileSize);
        
        for (Point i : groundResourceSpawn.keySet()) {
            System.out.println(hitTile.getBounds());
            System.out.println(groundResourceSpawn.get(i).getCollArea());
            if (groundResourceSpawn.get(i).getResource().requiredTool == playerTool && hitTile.intersects(groundResourceSpawn.get(i).getCollArea())) {
                groundResourceSpawn.get(i).setHealth(-toolDamage);
                if (groundResourceSpawn.get(i).getHealth() < 1) {
                    gp.playSound(SoundEffect.RESOURCEBREAK);
                    dropItem(groundResourceSpawn.get(i), targetCol, targetRow);
                    tm.removeIsBlocked(groundResourceSpawn.get(i).getCollArea());
                    groundResourceSpawn.remove(i);
                    break;
                }
                return true;
            }
        }
        for (Point i : placedFurniture.keySet()) {
            if (placedFurniture.get(i).getFurniture().requiredTool == playerTool && hitTile.intersects(placedFurniture.get(i).getCollArea()) && placedFurniture.get(i).isEmpty()) {
                if(placedFurniture.get(i).isEmpty()){
                    placedFurniture.get(i).setHealth(-toolDamage);
                }
                if (placedFurniture.get(i).getHealth() < 1) {
                    gp.playSound(SoundEffect.RESOURCEBREAK);
                    dropFurniture(placedFurniture.get(i), targetCol, targetRow);
                    tm.removeIsBlocked(placedFurniture.get(i).getCollArea());
                    placedFurniture.remove(i);
                    break;
                }
                return true;
            }
        }
        return false;
    }

    private void dropItem(PlacedResource resource, int dropCol, int dropRow) {
        for (int i = 0; i < resource.getDropAmount(); i++) {
            resourceDrop.add(new PlacedItem(resource.getResource().drop, new Point(dropCol * 32 + r.nextInt(-32, 32), dropRow * 32 + r.nextInt(-32, 32))));
        }
    }

    private void dropFurniture(PlacedFurniture furniture, int dropCol, int dropRow) {
        resourceDrop.add(new PlacedItem(furniture.getFurniture().drop, new Point(dropCol * 32 + r.nextInt(-32, 32), dropRow * 32 + r.nextInt(-32, 32))));
    }

    public void handleCollision(Player player, boolean ePressed, GamePanel ge, int mouseX, int mouseY) {
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

    public Chest getChest() {
        if (selectedFurniture != null) {
            return selectedFurniture.CurrentChest();
        }
        return null;
    }
    public HashMap<Point, PlacedFurniture> getPlacedFurniture(){
        return placedFurniture;
    }
    public HashMap<Point, PlacedResource> getGroundResources(){
        return groundResourceSpawn;
    }

    public void draw(Graphics2D g2, Camera camera) {
        for (PlacedItem i : resourceDrop) {
            g2.drawImage(i.getItem().getHeldIcon(), (int) i.getBounds().getX() - (int) camera.getTx(), (int) i.getBounds().getY() - (int) camera.getTy(), null);
        }
        for (PlacedItem i : groundItemsSpawn) {
            g2.drawImage(i.getItem().getHeldIcon(), (int) i.getBounds().getX() - (int) camera.getTx(), (int) i.getBounds().getY() - (int) camera.getTy(), null);
        }
    }
}
