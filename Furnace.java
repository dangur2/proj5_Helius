
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class Furnace extends PlacedFurniture {

    private boolean smelting;
    private FurnaceRecipe activeRecipe;
    private Item outputItem;
    private double counter;
    private final FurnaceRecipeCatalog furnaceRecipeCatalog;

    public Furnace(FurnitureType type,  FurnaceRecipeCatalog furnaceRecipeCatalog,int x, int y){
        this.furnaceRecipeCatalog = furnaceRecipeCatalog;
        super(type, new Point(x,y));
    }

    @Override 
    public boolean isEmpty(){
        return outputItem == null && !smelting;
    }

    @Override
    public void tick(double dt) {
        if (smelting) {
            counter += dt;
            if (counter > activeRecipe.getTime()) {
                outputItem = activeRecipe.getResult();
                smelting = false;
                counter = 0;
            }
        }
    }
    @Override
    public void Interact(Player player, Item selectedItem) {
        if (outputItem != null) {
            player.getInventory().addCopyOf(outputItem);
            outputItem = null;
            return;
        }
        if (selectedItem != null && furnaceRecipeCatalog.getRecipeByName(selectedItem.getItemName()) != null) {
            activeRecipe = furnaceRecipeCatalog.getRecipeByName(selectedItem.getItemName());
            if (player.getInventory().contains(activeRecipe.getResource(), activeRecipe.getResourcequantity()) && player.getInventory().contains(activeRecipe.getFuel(), activeRecipe.getFuelquantity())) {
                smelting = true;
                player.getInventory().useResources(activeRecipe.getResource(), activeRecipe.getResourcequantity());
                player.getInventory().useResources(activeRecipe.getFuel(), activeRecipe.getFuelquantity());
            }
        }
    }

    @Override
    public void draw(Graphics2D g2, Camera camera, int x, int y) {
        if (smelting) {
            g2.drawImage(getUseImage(), x - (int) camera.getTx(), y - (int) camera.getTy(), null);
        } else {
            if (outputItem != null) {
                g2.setColor(Color.black);
                g2.fillRoundRect(x - (int) camera.getTx(), (y - (int) camera.getTy()) - 30, 32, 32, 9, 9);
                g2.setColor(new Color(36, 33, 52, 230));
                g2.fillRoundRect(x - (int) camera.getTx() + 1, (y - (int) camera.getTy()) - 29, 30, 30, 9, 9);
                g2.drawImage(outputItem.getHeldIcon(), x - (int) camera.getTx(), (y - (int) camera.getTy()) - 30, null);
            }
            g2.drawImage(getImage(), x - (int) camera.getTx(), y - (int) camera.getTy(), null);
        }
    }

    @Override
    public Chest CurrentChest() {
        return null;
    }
}
