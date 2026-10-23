
import java.util.ArrayList;

public class Inventory {

    private ArrayList<Item> inventory = new ArrayList<>();

    public Inventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
        addItem(new Item("Sword", ItemType.SWORD, "/assets/Sprites/hotbar_sword.png", "/assets/Sprites/held_sword.png", 1, 0, 5));
        addItem(new Item("Pickaxe", ItemType.PICKAXE, "/assets/Sprites/hotbar_pickaxe.png", "/assets/Sprites/held_pickaxe.png", 1, 0, 1));
        addItem(new Item("Axe", ItemType.AXE, "/assets/Sprites/hotbar_axe.png", "/assets/Sprites/held_axe.png", 1, 0, 1));
        addItem(new Item("Hoe", ItemType.HOE, "/assets/Sprites/hotbar_hoe.png", "/assets/Sprites/held_hoe.png", 1, 0, 1));
        addItem(new Item("Shovel", ItemType.SHOVEL, "/assets/Sprites/hotbar_shovel.png", "/assets/Sprites/held_shovel.png", 1, 0, 1));
    }

    public void addItem(Item x) {
        //increase quantity first if item exists, then adds a new item
        for (Item y : inventory) {
            if (y.getItemName().equals(x.getItemName())) {
                y.increaseQuantity(x.getQuantity());
                return;
            }
        }
        inventory.add(x);
    }

    public void removeItem(int x) {
        if (x >= 0 && x <= inventory.size()) {
            inventory.remove(x);
        }
    }

    public int useItem(Item x) {
        //decreases quantity first if item exist, remove secondly
        if (x != null) {
            switch (x.getItemType()) {
                case ItemType.CONSUMABLE -> {
                    x.decreaseQuantity();
                    if (x.getQuantity() == 0) {
                        inventory.remove(x);
                    }
                    return x.use();

                }
                case ItemType.SWORD -> {
                    return x.getDmg();
                }
                case ItemType.PICKAXE -> {
                    return x.getDmg();
                }
                case ItemType.AXE -> {
                    return x.getDmg();
                }
                case ItemType.HOE -> {
                    return x.getDmg();
                }
            }
        }

        return 0;
    }
    public void addCopyOf(Item x){
        addItem(new Item(x));
    }
    public Item getItem(int x) {
        return this.inventory.get(x);
    }

    public int getInventorySize() {
        return this.inventory.size();
    }
}
