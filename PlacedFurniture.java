

public class PlacedFurniture {
    private FurnitureType furniture;
    private int currentHealth;
    private int useTime;
    private boolean smelting;
    private double counter = 0;
    private FurnaceRecipe activeRecipe;
    private Item outputItem;

    public FurnitureType getFurniture(){
        return furniture;
    }
    public void setFurniture(FurnitureType furniture){
        this.furniture = furniture;
        this.useTime = furniture.useTime;
        this.currentHealth = furniture.health;
    }

    public int getHealth(){
        return currentHealth;
    }
    public void setHealth(int x){
        this.currentHealth += x;
    }
    public int getUseTime(){
        return useTime;
    }
    public void increaseUseTime(int x){
        this.useTime += x;
    }
    public void smelt(Item selectedItem, Inventory inventory, FurnaceRecipeCatalog furnaceRecipeCatalog){
        if (selectedItem != null && furnaceRecipeCatalog.getRecipeByName(selectedItem.getItemName()) != null) {
            activeRecipe = furnaceRecipeCatalog.getRecipeByName(selectedItem.getItemName());
            if (inventory.contains(activeRecipe.getResource(), activeRecipe.getResourcequantity()) && inventory.contains(activeRecipe.getFuel(), activeRecipe.getFuelquantity())) {
                smelting = true;
                inventory.useResources(activeRecipe.getResource(), activeRecipe.getResourcequantity());
                inventory.useResources(activeRecipe.getFuel(), activeRecipe.getFuelquantity());
            }
        }
    }

    public Item tick(double dt){
        if(smelting){
            counter += dt;
            if (counter > this.useTime) {
                outputItem = activeRecipe.getResult();
                smelting = false;
                counter = 0;
            }
        }
        return outputItem;
    }
    public boolean getSmelting(){
        return smelting;
    }
    public void clearDisplay(){
        outputItem = null;
    }
}
