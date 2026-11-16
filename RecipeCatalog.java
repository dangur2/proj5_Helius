
import javax.imageio.ImageIO;

public class RecipeCatalog {

    private final ItemRecipe[] recipe;
    private final ItemCatalog itemCatalog;


    public RecipeCatalog(ItemCatalog itemCatalog) {
        this.recipe = new ItemRecipe[13];
        this.itemCatalog = itemCatalog;
        loadCraftingRecipies();
    }

    private void loadCraftingRecipies() {
        try {
            recipe[0] = new ItemRecipe();
            recipe[0].grayedSprite = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/grayStonePickaxe.png"));
            recipe[0].sprite = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/stonePickaxe.png"));
            recipe[0].name = "Stone Pickaxe";
            recipe[0].craftedItem = new Item(itemCatalog.getItem(11));
            recipe[0].itemDescription = "Used to mine rocks";
            recipe[0].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(6),2));
            recipe[0].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(7),3));
            recipe[0].amount = 1;

            recipe[1] = new ItemRecipe();
            recipe[1].grayedSprite = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/grayStoneSword.png"));
            recipe[1].sprite = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/stoneSword.png"));
            recipe[1].name = "Stone Sword";
            recipe[1].craftedItem = new Item(itemCatalog.getItem(10));
            recipe[1].itemDescription = "Used in combat";
            recipe[1].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(6),1));
            recipe[1].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(7),2));
            recipe[1].amount = 1;

            recipe[2] = new ItemRecipe();
            recipe[2].grayedSprite = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/grayStoneAxe.png"));
            recipe[2].sprite = ImageIO.read(getClass().getResourceAsStream("/assets/Sprites/hotbar_stoneAxe.png"));
            recipe[2].name = "Stone axe";
            recipe[2].craftedItem = new Item(itemCatalog.getItem(12));
            recipe[2].itemDescription = "Used in woodcutting";
            recipe[2].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(6),2));
            recipe[2].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(7),3));
            recipe[2].amount = 1;

            recipe[3] = new ItemRecipe();
            recipe[3].grayedSprite = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/grayStoneHoe.png"));
            recipe[3].sprite = ImageIO.read(getClass().getResourceAsStream("/assets/Sprites/hotbar_stoneHoe.png"));
            recipe[3].name = "Stone hoe";
            recipe[3].craftedItem = new Item(itemCatalog.getItem(13));
            recipe[3].itemDescription = "Used in tilling";
            recipe[3].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(6),2));
            recipe[3].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(7),2));
            recipe[3].amount = 1;

            recipe[4] = new ItemRecipe();
            recipe[4].grayedSprite = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/grayStoneShovel.png"));
            recipe[4].sprite = ImageIO.read(getClass().getResourceAsStream("/assets/Sprites/hotbar_stoneShovel.png"));
            recipe[4].name = "Stone shovel";
            recipe[4].craftedItem = new Item(itemCatalog.getItem(14));
            recipe[4].itemDescription = "Used in digging";
            recipe[4].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(6),2));
            recipe[4].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(7),1));
            recipe[4].amount = 1;

            recipe[5] = new ItemRecipe();
            recipe[5].grayedSprite = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/grayedStick.png"));
            recipe[5].sprite = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/stick.png"));
            recipe[5].name = "3x Stick";
            recipe[5].craftedItem = new Item(itemCatalog.getItem(6));
            recipe[5].itemDescription = "A Material";
            recipe[5].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(8),1));
            recipe[5].amount = 3;

            recipe[6] = new ItemRecipe();
            recipe[6].grayedSprite = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/grayedFurnace.png"));
            recipe[6].sprite = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/furnace.png"));
            recipe[6].name = "Furnace";
            recipe[6].craftedItem = new Item(itemCatalog.getItem(15));
            recipe[6].itemDescription = "Can be placed";
            recipe[6].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(7),8));
            recipe[6].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(8),1));
            recipe[6].amount = 1;

            recipe[7] = new ItemRecipe();
            recipe[7].grayedSprite = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/grayedChest.png"));
            recipe[7].sprite = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/chest.png"));
            recipe[7].name = "Chest";
            recipe[7].craftedItem = new Item(itemCatalog.getItem(16));
            recipe[7].itemDescription = "Can be placed";
            recipe[7].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(6),8));
            recipe[7].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(9),3));
            recipe[7].amount = 1;

            recipe[8] = new ItemRecipe();
            recipe[8].grayedSprite = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/grayCopperPickaxe.png"));
            recipe[8].sprite = ImageIO.read(getClass().getResourceAsStream("/assets/Sprites/hotbar_copperPickaxe.png"));
            recipe[8].name = "Copper Pickaxe";
            recipe[8].craftedItem = new Item(itemCatalog.getItem(19));
            recipe[8].itemDescription = "Used to mine rocks";
            recipe[8].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(6),2));
            recipe[8].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(17),3));
            recipe[8].amount = 1;

            recipe[9] = new ItemRecipe();
            recipe[9].grayedSprite = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/grayCopperSword.png"));
            recipe[9].sprite = ImageIO.read(getClass().getResourceAsStream("/assets/Sprites/hotbar_copperSword.png"));
            recipe[9].name = "Copper Sword";
            recipe[9].craftedItem = new Item(itemCatalog.getItem(18));
            recipe[9].itemDescription = "Used in combat";
            recipe[9].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(6),1));
            recipe[9].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(17),2));
            recipe[9].amount = 1;

            recipe[10] = new ItemRecipe();
            recipe[10].grayedSprite = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/grayCopperAxe.png"));
            recipe[10].sprite = ImageIO.read(getClass().getResourceAsStream("/assets/Sprites/hotbar_copperAxe.png"));
            recipe[10].name = "Copper axe";
            recipe[10].craftedItem = new Item(itemCatalog.getItem(20));
            recipe[10].itemDescription = "Used in woodcutting";
            recipe[10].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(6),2));
            recipe[10].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(17),3));
            recipe[10].amount = 1;

            recipe[11] = new ItemRecipe();
            recipe[11].grayedSprite = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/grayCopperHoe.png"));
            recipe[11].sprite = ImageIO.read(getClass().getResourceAsStream("/assets/Sprites/hotbar_copperHoe.png"));
            recipe[11].name = "Copper hoe";
            recipe[11].craftedItem = new Item(itemCatalog.getItem(21));
            recipe[11].itemDescription = "Used in tilling";
            recipe[11].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(6),2));
            recipe[11].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(17),2));
            recipe[11].amount = 1;

            recipe[12] = new ItemRecipe();
            recipe[12].grayedSprite = ImageIO.read(getClass().getResourceAsStream("/assets/CraftingSprites/grayCopperShovel.png"));
            recipe[12].sprite = ImageIO.read(getClass().getResourceAsStream("/assets/Sprites/hotbar_copperShovel.png"));
            recipe[12].name = "Copper shovel";
            recipe[12].craftedItem = new Item(itemCatalog.getItem(22));
            recipe[12].itemDescription = "Used in digging";
            recipe[12].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(6),2));
            recipe[12].itemRequirement.add(new RecipeIngredient(itemCatalog.getItem(17),1));
            recipe[12].amount = 1;

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
