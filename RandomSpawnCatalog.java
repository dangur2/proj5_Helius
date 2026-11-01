

public class RandomSpawnCatalog{

    private final Item[] groundItems;
    private final ItemCatalog itemCatalog;
    
    public RandomSpawnCatalog(ItemCatalog itemCatalog){
        groundItems = new Item[8];
        this.itemCatalog = itemCatalog;
        loadGroundItems();
    }

    private void loadGroundItems() {
        try {
            groundItems[0] = new Item(itemCatalog.getItem(0));
            groundItems[1] = new Item(itemCatalog.getItem(1));
            groundItems[2] = new Item(itemCatalog.getItem(2));
            groundItems[3] = new Item(itemCatalog.getItem(3));
            groundItems[4] = new Item(itemCatalog.getItem(4));
            groundItems[5] = new Item(itemCatalog.getItem(5));
            groundItems[6] = new Item(itemCatalog.getItem(6));
            groundItems[7] = new Item(itemCatalog.getItem(7));
        } catch (Exception e) {}
    }

    public Item getItem(int x){
        return groundItems[x];
    }
    public int getSize(){
        return groundItems.length;
    }
}
