
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ItemRecipe {
    public String name;
    public BufferedImage grayedSprite, sprite;
    public ArrayList<RecipeIngredient> itemRequirment = new ArrayList<>();
    public Item craftedItem;
    public String itemDescription;
}
