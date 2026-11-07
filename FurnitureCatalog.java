import javax.imageio.ImageIO;

public class FurnitureCatalog {
    private final FurnitureType[] furniture;
    private final ItemCatalog itemCatalog;

    public FurnitureCatalog(ItemCatalog itemCatalog){
        furniture = new FurnitureType[2];
        this.itemCatalog = itemCatalog;
        loadFurniture();
    }

    private void loadFurniture() {
        try {
            furniture[0] = new FurnitureType();
            furniture[0].image = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/furnace.png"));
            furniture[0].useImage = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/useFurnace.png"));
            furniture[0].name = "Furnace";
            furniture[0].collision = true;
            furniture[0].health = 3;
            furniture[0].drop = itemCatalog.getItem(15);
            furniture[0].requiredTool = ItemType.PICKAXE;
            furniture[0].useTime = 3;
            furniture[0].type = FurnitureInteractType.FURNACE;

            furniture[1] = new FurnitureType();
            furniture[1].image = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/chest.png"));
            furniture[1].useImage = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/chest.png"));
            furniture[1].name = "Chest";
            furniture[1].collision = true;
            furniture[1].health = 3;
            furniture[1].drop = itemCatalog.getItem(16);
            furniture[1].requiredTool = ItemType.AXE;
            furniture[1].useTime = 0;
            furniture[1].type = FurnitureInteractType.CHEST;

        } catch (Exception e) {
        }
    }
    public FurnitureType getFurniture(int x){
        return furniture[x];
    }

    public int getSize(){
        return furniture.length;
    }
}
