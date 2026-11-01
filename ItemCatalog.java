
public class ItemCatalog {

    private final Item[] item;

    public ItemCatalog() {
        item = new Item[15];
        loadItems();
    }

    private void loadItems() {
        try {
            item[0] = new Item("Red berry", ItemType.CONSUMABLE, "/assets/Resource/redberry.png", "/assets/Resource/redberry_floor.png", 1, 5, 0);
            item[1] = new Item("Purple berry", ItemType.CONSUMABLE, "/assets/Resource/purpleberry.png", "/assets/Resource/purpleberry_floor.png", 1, 10, 0);
            item[2] = new Item("Yellow berry", ItemType.CONSUMABLE, "/assets/Resource/yellowberry.png", "/assets/Resource/yellowberry_floor.png", 1, 15, 0);
            item[3] = new Item("Pear", ItemType.CONSUMABLE, "/assets/Resource/pear.png", "/assets/Resource/pear_floor.png", 1, 12, 0);
            item[4] = new Item("Red apple", ItemType.CONSUMABLE, "/assets/Resource/redapple.png", "/assets/Resource/redapple_floor.png", 1, 7, 0);
            item[5] = new Item("Green apple", ItemType.CONSUMABLE, "/assets/Resource/greenapple.png", "/assets/Resource/greenapple_floor.png", 1, 12, 0);
            item[6] = new Item("Stick", ItemType.MATERIAL, "/assets/Resource/stick.png", "/assets/Resource/stick_floor.png", 1, 0, 0);
            item[7] = new Item("Rock", ItemType.MATERIAL, "/assets/Resource/rock.png", "/assets/Resource/rock_floor.png", 1, 0, 0);
            item[8] = new Item("Log", ItemType.MATERIAL, "/assets/Resource/log.png", "/assets/Resource/log_floor.png", 1, 0, 0);
            item[9] = new Item("Copper nugget", ItemType.MATERIAL, "/assets/Resource/copperOre.png", "/assets/Resource/copperOre_floor.png", 1, 0, 0);
            item[10] = new Item("Sword", ItemType.SWORD, "/assets/Sprites/hotbar_sword.png", "/assets/Sprites/held_sword.png", 1, 0, 5);
            item[11] = new Item("Pickaxe", ItemType.PICKAXE, "/assets/Sprites/hotbar_pickaxe.png", "/assets/Sprites/held_pickaxe.png", 1, 0, 1);
            item[12] = new Item("Axe", ItemType.AXE, "/assets/Sprites/hotbar_axe.png", "/assets/Sprites/held_axe.png", 1, 0, 1);
            item[13] = new Item("Hoe", ItemType.HOE, "/assets/Sprites/hotbar_hoe.png", "/assets/Sprites/held_hoe.png", 1, 0, 1);
            item[14] = new Item("Shovel", ItemType.SHOVEL, "/assets/Sprites/hotbar_shovel.png", "/assets/Sprites/held_shovel.png", 1, 0, 1);
        } catch (Exception e) {
        }
    }

    public Item getItem(int i) {
        return item[i];
    }

    public int getSize() {
        return item.length;
    }
}
