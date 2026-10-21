
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

public class WorldSpawn {

    private final TileManager tm;
    private final ArrayList<GroundItem> groundItems = new ArrayList<>();
    private final ArrayList<Item> itemList = new ArrayList<>();
    

    public WorldSpawn(TileManager tm) {
        this.tm = tm;
        createItemList();
        generateItems(35);
    }

    private void createItemList() {
        itemList.add(new Item("red berry", ItemType.CONSUMABLE, "/assets/Sprites/redberry.png","/assets/Sprites/redberry.png", 1,5,0));
        itemList.add(new Item("purple berry", ItemType.CONSUMABLE, "/assets/Sprites/purpleberry.png","/assets/Sprites/redberry.png", 1,10,0));
        itemList.add(new Item("yellow berry", ItemType.CONSUMABLE, "/assets/Sprites/yellowberry.png","/assets/Sprites/redberry.png", 1,15,0));
        itemList.add(new Item("pear", ItemType.CONSUMABLE, "/assets/Sprites/pear.png","/assets/Sprites/redberry.png", 1,12,0));
        itemList.add(new Item("red apple", ItemType.CONSUMABLE, "/assets/Sprites/redapple.png","/assets/Sprites/redberry.png", 1,7,0));
        itemList.add(new Item("green apple", ItemType.CONSUMABLE, "/assets/Sprites/greenapple.png","/assets/Sprites/redberry.png", 1,12,0));
    }

    private void generateItems(int amount) {
        Random r = new Random();
        for (int i = 0; i < amount; i++) {
            groundItems.add(new GroundItem(itemList.get(r.nextInt(0,itemList.size())), tm.findFreePosition()));
        }
    }

    public void handleCollision(Player player, boolean ePressed, GamePanel ge) {
        for (GroundItem i : groundItems) {
            if (i.getBounds().intersects(player.getCollison()) && ePressed) {
                ge.playSound(SoundEffect.PICKUP);
                player.getInventory().addItem(i.getItem());
                groundItems.remove(i);
                break;
            }
        }
    }

    public void draw(Graphics2D g2, Camera camera) {
        for (GroundItem x : groundItems) {
            g2.drawImage(x.getItem().getHotbarIcon(), (int) x.getBounds().getX() - (int) camera.getTx(), (int) x.getBounds().getY() - (int) camera.getTy(), null);
        }
    }
}
