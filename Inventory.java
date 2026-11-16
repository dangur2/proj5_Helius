
public class Inventory {

    private final Item[] inventory;

    public Inventory(Item[] inventory) {
        this.inventory = inventory;
        generateStarterItems();

    }

    private void generateStarterItems() {
        addItem(new Item("Stone sword", ItemType.SWORD, "/assets/Sprites/hotbar_stoneSword.png", "/assets/Sprites/held_stoneSword.png", 1, 0, 2));
        addItem(new Item("Stone pickaxe", ItemType.PICKAXE, "/assets/Sprites/hotbar_stonePickaxe.png", "/assets/Sprites/held_stonePickaxe.png", 1, 0, 1));
        addItem(new Item("Stone axe", ItemType.AXE, "/assets/Sprites/hotbar_stoneAxe.png", "/assets/Sprites/held_stoneAxe.png", 1, 0, 1));
        addItem(new Item("Stone hoe", ItemType.HOE, "/assets/Sprites/hotbar_stoneHoe.png", "/assets/Sprites/held_stoneHoe.png", 1, 0, 1));
        addItem(new Item("Stone shovel", ItemType.SHOVEL, "/assets/Sprites/hotbar_stoneShovel.png", "/assets/Sprites/held_stoneShovel.png", 1, 0, 1));
        addItem(new Item("Log", ItemType.MATERIAL, "/assets/Resource/log.png", "/assets/Resource/log_floor.png", 4, 0, 0));
        addItem(new Item("Rock", ItemType.MATERIAL, "/assets/Resource/rock.png", "/assets/Resource/rock_floor.png", 8, 0, 0));
        addItem(new Item("Copper nugget", ItemType.MATERIAL, "/assets/Resource/copperOre.png", "/assets/Resource/copperOre_floor.png", 4, 0, 0));
        addItem(new Item("Chest", ItemType.FURNITURE, "/assets/CraftingSprites/chest.png", "/assets/CraftingSprites/chest.png", 1, 0, 1));
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

    public boolean addItemShift(Item x, int incl, int exl) {
        if (x == null) {
            return false;
        }
        for (int i = incl; i < exl; i++) {
            Item item = inventory[i];
            if (item != null && item.getItemName().equals(x.getItemName())) {
                item.increaseQuantity(x.getQuantity());
                return true;
            }
        }

        for (int i = incl; i < exl; i++) {
            if (inventory[i] == null) {
                inventory[i] = x;
                return true;
            }
        }
        return false;

    }

    public void decreaseQuantity(int x) {
        if (x > 0 && x < inventory.length) {
            inventory[x].decreaseQuantity();
            if (inventory[x].getQuantity() < 1) {
                removeItem(x);
            }
        }
    }

    public void setItem(int x, Item item) {
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

    public void useResources(Item resource, int quantity) {
        for (int i = 0; i < inventory.length; i++) {
            if (getItem(i) != null && resource != null && quantity >= 1 && getItem(i).getItemName().equals(resource.getItemName()) && getItem(i).getQuantity() >= quantity) {
                for (int j = 0; j < quantity; j++) {
                    getItem(i).decreaseQuantity();
                    if (getItem(i).getQuantity() < 1) {
                        removeItem(i);
                    }
                }
            }
        }
    }

    public boolean contains(Item resource, int quantity) {
        for (int i = 0; i < inventory.length; i++) {
            if (getItem(i) != null && resource != null && getItem(i).getItemName().equals(resource.getItemName()) && getItem(i).getQuantity() >= quantity) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty(int x) {
        return this.inventory[x] == null;
    }
}
