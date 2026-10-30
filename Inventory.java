
public class Inventory {

    private final Item[] inventory;

    public Inventory(Item[] inventory) {
        this.inventory = inventory;
        generateStarterItems();
        
    }

    private void generateStarterItems() {
        addItem(new Item("Sword", ItemType.SWORD, "/assets/Sprites/hotbar_sword.png", "/assets/Sprites/held_sword.png", 1, 0, 5));
        addItem(new Item("Pickaxe", ItemType.PICKAXE, "/assets/Sprites/hotbar_pickaxe.png", "/assets/Sprites/held_pickaxe.png", 1, 0, 1));
        addItem(new Item("Axe", ItemType.AXE, "/assets/Sprites/hotbar_axe.png", "/assets/Sprites/held_axe.png", 1, 0, 1));
        addItem(new Item("Hoe", ItemType.HOE, "/assets/Sprites/hotbar_hoe.png", "/assets/Sprites/held_hoe.png", 1, 0, 1));
        addItem(new Item("Shovel", ItemType.SHOVEL, "/assets/Sprites/hotbar_shovel.png", "/assets/Sprites/held_shovel.png", 1, 0, 1));
        addItem(new Item("Shove2l", ItemType.SHOVEL, "/assets/Sprites/hotbar_shovel.png", "/assets/Sprites/held_shovel.png", 1, 0, 1));
        addItem(new Item("Sho1e2l", ItemType.SHOVEL, "/assets/Sprites/hotbar_shovel.png", "/assets/Sprites/held_shovel.png", 1, 0, 1));
        addItem(new Item("Sh1o1e2l", ItemType.SHOVEL, "/assets/Sprites/hotbar_shovel.png", "/assets/Sprites/held_shovel.png", 1, 0, 1));
        addItem(new Item("Sh5o1e2l", ItemType.SHOVEL, "/assets/Sprites/hotbar_shovel.png", "/assets/Sprites/held_shovel.png", 1, 0, 1));
    }

    public void addItem(Item x) {
        //increase quantity first if item exists, then adds a new item
        for (Item inv : inventory) {
            if (inv != null && inv.getItemName().equals(x.getItemName())) {
                inv.increaseQuantity(x.getQuantity());
                return;
            }
        }
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) {
                inventory[i] = x;
                return;
            }
        }
    }
    public void setItem(int x, Item item){
        if (item != null) {
            inventory[x] = item;
        }
    }

    public void removeItem(int x) {
        if (x >= 0 && x <= 36) {
            inventory[x] = null;
        }
    }

    public int useItem(int x) {
        //decreases quantity first if item exist, remove secondly
        if (inventory[x] != null) {
            switch (inventory[x].getItemType()) {
                case ItemType.CONSUMABLE -> {
                    int heal = inventory[x].use();
                    inventory[x].decreaseQuantity();
                    if (inventory[x].getQuantity() == 0) {
                        inventory[x] = null;
                    }
                    return heal;

                }
                case ItemType.SWORD -> {
                    return inventory[x].getDmg();
                }
                case ItemType.PICKAXE -> {
                    return inventory[x].getDmg();
                }
                case ItemType.AXE -> {
                    return inventory[x].getDmg();
                }
                case ItemType.HOE -> {
                    return inventory[x].getDmg();
                }
                case ItemType.SHOVEL -> {
                    return inventory[x].getDmg();
                }
            }
        }

        return 0;
    }

    public void addCopyOf(Item x) {
        addItem(new Item(x));
    }

    public Item getItem(int x) {
        if (this.inventory[x] == null) {
            return null;
        }
        return this.inventory[x];
    }

    public boolean isEmpty(int x) {
        return this.inventory[x] == null;
    }
}
