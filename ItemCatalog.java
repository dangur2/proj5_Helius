
public class ItemCatalog {

    private final Item[] item;

    public ItemCatalog() {
        item = new Item[24];
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
            item[10] = new Item("Stone sword", ItemType.SWORD, "/assets/Sprites/hotbar_stoneSword.png", "/assets/Sprites/held_stoneSword.png", 1, 0, 2);
            item[11] = new Item("Stone pickaxe", ItemType.PICKAXE, "/assets/Sprites/hotbar_stonePickaxe.png", "/assets/Sprites/held_stonePickaxe.png", 1, 0, 1);
            item[12] = new Item("Stone axe", ItemType.AXE, "/assets/Sprites/hotbar_stoneAxe.png", "/assets/Sprites/held_stoneAxe.png", 1, 0, 1);
            item[13] = new Item("Stone hoe", ItemType.HOE, "/assets/Sprites/hotbar_stoneHoe.png", "/assets/Sprites/held_stoneHoe.png", 1, 0, 1);
            item[14] = new Item("Stone shovel", ItemType.SHOVEL, "/assets/Sprites/hotbar_stoneShovel.png", "/assets/Sprites/held_stoneShovel.png", 1, 0, 1);
            item[15] = new Item("Furnace", ItemType.FURNITURE, "/assets/CraftingSprites/furnace.png", "/assets/CraftingSprites/furnace.png", 1, 0, 1);
            item[16] = new Item("Chest", ItemType.FURNITURE, "/assets/CraftingSprites/chest.png", "/assets/CraftingSprites/chest.png", 1, 0, 1);
            item[17] = new Item("Copper bar", ItemType.MATERIAL, "/assets/Resource/copperBar.png", "/assets/Resource/copperBar.png", 1, 0, 0);
            item[18] = new Item("Copper sword", ItemType.SWORD, "/assets/Sprites/hotbar_copperSword.png", "/assets/Sprites/held_copperSword.png", 1, 0, 4);
            item[19] = new Item("Copper pickaxe", ItemType.PICKAXE, "/assets/Sprites/hotbar_copperPickaxe.png", "/assets/Sprites/held_copperPickaxe.png", 1, 0, 2);
            item[20] = new Item("Copper axe", ItemType.AXE, "/assets/Sprites/hotbar_copperAxe.png", "/assets/Sprites/held_copperAxe.png", 1, 0, 2);
            item[21] = new Item("Copper hoe", ItemType.HOE, "/assets/Sprites/hotbar_copperHoe.png", "/assets/Sprites/held_copperHoe.png", 1, 0, 2);
            item[22] = new Item("Copper shovel", ItemType.SHOVEL, "/assets/Sprites/hotbar_copperShovel.png", "/assets/Sprites/held_copperShovel.png", 1, 0, 2);
            item[23] = new Item("Bridge", ItemType.FURNITURE, "/assets/CraftingSprites/bridge.png", "/assets/Resource/bridge_floor.png", 1, 0, 1);
        } catch (Exception e) {
        }
    }

    public Item getItem(int i) {
        return item[i];
    }
    public Item getByName(String name){
        for (Item i : item) {
            if (i.getItemName().equals(name)) {
                return i;
            }
        }
        return null;
    }

    public int getSize() {
        return item.length;
    }
}
