
import javax.imageio.ImageIO;

public class RecipeCatalog {

    private final ItemRecipe[] recipe;

    public RecipeCatalog() {
        this.recipe = new ItemRecipe[2];
        loadCraftingRecipies();
    }

    private void loadCraftingRecipies() {
        try {
            recipe[0] = new ItemRecipe();
            recipe[0].grayedSprite = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/grayedPickaxe.png"));
            recipe[0].sprite = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/pickaxe.png"));
            recipe[0].name = "Stone Pickaxe";
            recipe[0].craftedItem = new Item("Stone Pickaxe", ItemType.PICKAXE, "/assets/Sprites/hotbar_pickaxe.png", "/assets/Sprites/held_pickaxe.png", 1, 0, 1);
            recipe[0].itemDescription = "Used in mining rocks";
            recipe[0].itemRequirment.add(new RecipeIngredient("stick",2));
            recipe[0].itemRequirment.add(new RecipeIngredient("rock",3));

            recipe[1] = new ItemRecipe();
            recipe[1].grayedSprite = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/grayedSword.png"));
            recipe[1].sprite = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/sword.png"));
            recipe[1].name = "Stone Sword";
            recipe[1].craftedItem = new Item("Stone Sword", ItemType.SWORD, "/assets/Sprites/hotbar_sword.png", "/assets/Sprites/held_sword.png", 1, 0, 1);
            recipe[1].itemDescription = "Used in combat";
            recipe[1].itemRequirment.add(new RecipeIngredient("stick",1));
            recipe[1].itemRequirment.add(new RecipeIngredient("rock",2));

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
