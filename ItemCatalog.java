

public class ItemCatalog{

    private final Item[] groundItems;
    
    public ItemCatalog(){
        groundItems = new Item[6];
        loadGroundItems();
    }

    private void loadGroundItems() {
        try {
            groundItems[0] = new Item("red berry", ItemType.CONSUMABLE, "/assets/Resource/redberry.png","/assets/Resource/redberry.png", 1,5,0);
            groundItems[1] = new Item("purple berry", ItemType.CONSUMABLE, "/assets/Resource/purpleberry.png","/assets/Resource/redberry.png", 1,10,0);
            groundItems[2] = new Item("yellow berry", ItemType.CONSUMABLE, "/assets/Resource/yellowberry.png","/assets/Resource/redberry.png", 1,15,0);
            groundItems[3] = new Item("pear", ItemType.CONSUMABLE, "/assets/Resource/pear.png","/assets/Resource/redberry.png", 1,12,0);
            groundItems[4] = new Item("red apple", ItemType.CONSUMABLE, "/assets/Resource/redapple.png","/assets/Resource/redberry.png", 1,7,0);
            groundItems[5] = new Item("green apple", ItemType.CONSUMABLE, "/assets/Resource/greenapple.png","/assets/Resource/redberry.png", 1,12,0);
        } catch (Exception e) {}
    }

    public Item getItem(int x){
        return groundItems[x];
    }
    public int getSize(){
        return groundItems.length;
    }
}
