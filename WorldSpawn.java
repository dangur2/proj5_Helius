
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
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

        generateItemsSpawns(30);
        generateResourceSpawns(30);
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
        for (PlacedResource j : groundResourceSpawn) {
            if (col == j.getBounds().getX() && row == j.getBounds().getY()) {
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

    private Point2D findSpawnPosition() {
        for (int i = 0; i < 100; i++) {
            Point2D spawn = tm.findFreePosition();
            if (!isOccupied((int) spawn.getX(), (int) spawn.getY())) {
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
                        currentFurniture = new Furnace(furnitureCatalog.getFurniture(0), furnaceRecipeCatalog);
                    }
                    case "Chest" -> {
                        currentFurniture = new Chest(furnitureCatalog.getFurniture(1));
                    }
                }
            }
            if (currentFurniture != null) {
                placedFurniture.put(new Point(x * gp.tileSize, y * gp.tileSize), currentFurniture);
                tm.setIsBlocked((x * gp.tileSize), (y * gp.tileSize));
                return true;
            }
        }
        return false;

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
            if (i.getResource().requiredTool == playerTool && i.getBounds().getX() / 32 == targetCol && i.getBounds().getY() / 32 == targetRow) {
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
        for (Point i : placedFurniture.keySet()) {
            if (placedFurniture.get(i).getFurniture().requiredTool == playerTool && i.getX() / 32 == targetCol && i.getY() / 32 == targetRow) {
                placedFurniture.get(i).setHealth(-toolDamage);
                if (placedFurniture.get(i).getHealth() < 1) {
                    gp.playSound(SoundEffect.RESOURCEBREAK);
                    dropFurniture(placedFurniture.get(i), targetCol, targetRow);
                    tm.removeIsBlocked((int) i.getX(), (int) i.getY());
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

    public void draw(Graphics2D g2, Camera camera) {
        for (PlacedResource i : groundResourceSpawn) {
            g2.drawImage(i.getResource().image, (int) i.getBounds().getX() - (int) camera.getTx(), (int) i.getBounds().getY() - (int) camera.getTy(), null);
        }
        for (PlacedItem i : resourceDrop) {
            g2.drawImage(i.getItem().getHeldIcon(), (int) i.getBounds().getX() - (int) camera.getTx(), (int) i.getBounds().getY() - (int) camera.getTy(), null);
        }
        for (PlacedItem i : groundItemsSpawn) {
            g2.drawImage(i.getItem().getHeldIcon(), (int) i.getBounds().getX() - (int) camera.getTx(), (int) i.getBounds().getY() - (int) camera.getTy(), null);
        }
        for (Point i : placedFurniture.keySet()) {
            placedFurniture.get(i).draw(g2, camera, (int) i.getX(), (int) i.getY());
        }

    }
}
