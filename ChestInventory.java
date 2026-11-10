public class ChestInventory {
    private final Item[] chestInventory = new Item[27];

    public void addItem(Item item){
        for (Item i : chestInventory) {
            if (i != null && i.getItemName().equals(item.getItemName())) {
                i.increaseQuantity(item.getQuantity());
                return;
            }
        }
        for (int i = 0; i < chestInventory.length; i++) {
            if (chestInventory[i] == null) {
                chestInventory[i] = item;
                return;
            }
        }
    }
    public void setItem(Item item, int slot){
        if (item != null) {
            chestInventory[slot] = item;
        }
    }
    public Item getItem(int slot){
        if (slot < chestInventory.length) {
            return chestInventory[slot];
        }
        return null;
    }
    public void removeItem(int slot){
        if (slot < chestInventory.length) {
            chestInventory[slot] = null;
        }
    }
    public boolean isEmpty(){
        for (int i = 0; i < chestInventory.length; i++) {
            if(chestInventory[i] != null) {
                return false;
            }
        }
        return true;
    }
}
