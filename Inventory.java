
import java.util.ArrayList;

public class Inventory {

    private ArrayList<Item> inventory = new ArrayList<>();

    public Inventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
        addItem(new Item("sword", ItemType.SWORD, "/assets/Sprites/sword.png", 1, 0, 5));
        addItem(new Item("pickaxe", ItemType.SWORD, "/assets/Sprites/pickaxe.png", 1, 0, 5));
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
        if (inventory.get(x) != null) {
            inventory.remove(x);
        }
    }

    public int useItem(Item x) {
        //decreases quantity first if item exist, remove secondly
        for (Item y : inventory) {
            if (x != null) {
                switch (x.getItemType()) {
                    case ItemType.CONSUMABLE -> {
                        if (y.getItemName().equals(x.getItemName())) {
                            x.decreaseQuantity();
                            if (x.getQuantity() == 0) {
                                inventory.remove(x);
                            }
                            return x.use();
                        }
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
                }
            }
        }
        return 0;
    }

    public Item getItem(int x) {
        return this.inventory.get(x);
    }

    public int getInventorySize() {
        return this.inventory.size();
    }
}
