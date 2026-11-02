
import javax.imageio.ImageIO;

public class RecipeCatalog {

    private final ItemRecipe[] recipe;
    private final ItemCatalog itemCatalog;

    public RecipeCatalog(ItemCatalog itemCatalog) {
        this.recipe = new ItemRecipe[4];
        this.itemCatalog = itemCatalog;
        loadCraftingRecipies();
    }

    private void loadCraftingRecipies() {
        try {
            recipe[0] = new ItemRecipe();
            recipe[0].grayedSprite = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/grayedPickaxe.png"));
            recipe[0].sprite = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/pickaxe.png"));
            recipe[0].name = "Stone Pickaxe";
            recipe[0].craftedItem = new Item(itemCatalog.getItem(11));
            recipe[0].itemDescription = "Used to mine rocks";
            recipe[0].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(6),2));
            recipe[0].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(7),3));
            recipe[0].amount = 1;

            recipe[1] = new ItemRecipe();
            recipe[1].grayedSprite = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/grayedSword.png"));
            recipe[1].sprite = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/sword.png"));
            recipe[1].name = "Stone Sword";
            recipe[1].craftedItem = new Item(itemCatalog.getItem(10));
            recipe[1].itemDescription = "Used in combat";
            recipe[1].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(6),1));
            recipe[1].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(7),2));
            recipe[1].amount = 1;

            recipe[2] = new ItemRecipe();
            recipe[2].grayedSprite = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/grayedStick.png"));
            recipe[2].sprite = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/stick.png"));
            recipe[2].name = "3x Stick";
            recipe[2].craftedItem = new Item(itemCatalog.getItem(6));
            recipe[2].itemDescription = "Material";
            recipe[2].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(8),1));
            recipe[2].amount = 3;

            recipe[3] = new ItemRecipe();
            recipe[3].grayedSprite = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/grayedFurnace.png"));
            recipe[3].sprite = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/furnace.png"));
            recipe[3].name = "Furnace";
            recipe[3].craftedItem = new Item(itemCatalog.getItem(15));
            recipe[3].itemDescription = "Furniture";
            recipe[3].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(7),8));
            recipe[3].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(8),1));
            recipe[3].amount = 1;



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ItemRecipe getRecipe(int x){
        return recipe[x];
    }
    public int getSize(){
        return recipe.length;
    }
}
