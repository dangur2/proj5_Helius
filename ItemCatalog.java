

public class ItemCatalog{

    private final Item[] groundItems;
    
    public ItemCatalog(){
        groundItems = new Item[8];
        loadGroundItems();
    }

    private void loadGroundItems() {
        try {
            groundItems[0] = new Item("Red berry", ItemType.CONSUMABLE, "/assets/Resource/redberry.png","/assets/Resource/redberry_floor.png", 1,5,0);
            groundItems[1] = new Item("Purple berry", ItemType.CONSUMABLE, "/assets/Resource/purpleberry.png","/assets/Resource/purpleberry_floor.png", 1,10,0);
            groundItems[2] = new Item("Yellow berry", ItemType.CONSUMABLE, "/assets/Resource/yellowberry.png","/assets/Resource/yellowberry_floor.png", 1,15,0);
            groundItems[3] = new Item("Pear", ItemType.CONSUMABLE, "/assets/Resource/pear.png","/assets/Resource/pear_floor.png", 1,12,0);
            groundItems[4] = new Item("Red apple", ItemType.CONSUMABLE, "/assets/Resource/redapple.png","/assets/Resource/redapple_floor.png", 1,7,0);
            groundItems[5] = new Item("Green apple", ItemType.CONSUMABLE, "/assets/Resource/greenapple.png","/assets/Resource/greenapple_floor.png", 1,12,0);
            groundItems[6] = new Item("Stick", ItemType.MATERIAL, "/assets/Resource/stick.png","/assets/Resource/stick_floor.png", 1,0,0);
            groundItems[7] = new Item("Rock", ItemType.MATERIAL, "/assets/Resource/rock.png","/assets/Resource/rock_floor.png", 1,0,0);
        } catch (Exception e) {}
    }

    public Item getItem(int x){
        return groundItems[x];
    }
    public int getSize(){
        return groundItems.length;
    }
}
