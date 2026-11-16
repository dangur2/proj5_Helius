
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class CraftingView {
    private BufferedImage highlight;
    private final RecipeCatalog recipeCatalog;
    private final ArrayList<Rectangle> craftingButtons = new ArrayList<>();
    private final Inventory inventory;
    private final int viewRow = 4, viewCol = 9, craftingX = 191, craftingY = 150, hoverDisplayX = 71, hoverDisplayY = 34;
    private int hoverSlot, hoverX, hoverY;
    private boolean displayHover;
    private Item selectedItem;
    private final InventoryViewManager inventoryViewManager;
    private final SlotInteraction slotInteraction;
    private final SlotInteractionView slotInteractionView;

    public CraftingView(Inventory inventory, ItemCatalog itemCatalog, InventoryViewManager inventoryViewManager, SlotInteraction slotInteraction, SlotInteractionView slotInteractionView) {
        this.slotInteraction = slotInteraction;
        this.slotInteractionView = slotInteractionView;
        this.inventory = inventory;
        this.recipeCatalog = new RecipeCatalog(itemCatalog);
        this.inventoryViewManager = inventoryViewManager;
        loadCraftingButtons();
        loadSprites();
    }

    private void loadSprites() {
        try {
            highlight = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/highlight_tile2.png"));
        } catch (Exception e) {
        }
    }

    private void loadCraftingButtons() {
        for (int i = 0; i < viewRow; i++) {
            for (int j = 0; j < viewCol; j++) {
                craftingButtons.add(new Rectangle(craftingX + 6 + (j * 49), craftingY + 26 + (i * 49), 46, 46));
            }
        }
    }

    public Item getSelectItem(){
        return selectedItem;
    }

    public void LeftClick(int mouseX, int mouseY) {
        ItemRecipe recipe = recipeCatalog.getRecipe(hoverSlot);
        for (int i = 0; i < recipe.itemRequirement.size(); i++) {
            if (craftingButtons.get(hoverSlot).contains(mouseX,mouseY) && hasAllItemRequirment(hoverSlot)) {
                inventory.useResources(recipeCatalog.getRecipe(hoverSlot).itemRequirement.get(i).getItem(), recipeCatalog.getRecipe(hoverSlot).itemRequirement.get(i).getQuantity());
                selectedItem = new Item(recipeCatalog.getRecipe(hoverSlot).craftedItem);
                selectedItem.increaseQuantity(recipeCatalog.getRecipe(hoverSlot).amount-1);
                inventoryViewManager.swapScreen(mouseX, mouseY);
                selectedItem = null;
            }
        }
    }

    public void Hover(int mouseX, int mouseY) {
        hoverX = mouseX;
        hoverY = mouseY;
        displayHover = false;
        for (int i = 0; i < recipeCatalog.getSize(); i++) {
            if (craftingButtons.get(i).contains(mouseX, mouseY)) {
                hoverSlot = i;
                displayHover = true;
            }
        }
    }

    private boolean hasItemRequirement(int slot, int index) {
        return inventory.contains(recipeCatalog.getRecipe(slot).itemRequirement.get(index).getItem(), recipeCatalog.getRecipe(slot).itemRequirement.get(index).getQuantity());
    }
    private boolean hasAllItemRequirment(int recipe){
        for (int i = 0; i < recipeCatalog.getRecipe(recipe).itemRequirement.size(); i++) {
            if (!hasItemRequirement(recipe,i)) {
                return false;
            }
        }
        return true;
    }

    public void draw(Graphics2D g2) {
        for (int i = 0; i < recipeCatalog.getSize(); i++) {
            if (hasAllItemRequirment(i)) {
                g2.drawImage(recipeCatalog.getRecipe(i).sprite, (int) craftingButtons.get(i).getX() + 7, (int) craftingButtons.get(i).getY() + 5, null);
            }
            else{
                g2.drawImage(recipeCatalog.getRecipe(i).grayedSprite, (int) craftingButtons.get(i).getX() + 7, (int) craftingButtons.get(i).getY() + 5, null);
            }
        }
        slotInteractionView.drawHoverItem(g2, selectedItem, hoverX, hoverY);
        slotInteractionView.drawCraftingHover(g2, hasAllItemRequirment(hoverSlot), craftingButtons, displayHover, hoverX, hoverY, hoverSlot, hoverDisplayX, hoverDisplayY, recipeCatalog.getRecipe(hoverSlot), highlight);
    }
}
