
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
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

    public CraftingView(Inventory inventory, ItemCatalog itemCatalog, InventoryViewManager inventoryViewManager) {
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
        if (selectedItem != null) {
            g2.setFont(new Font("MONOSPACED", Font.BOLD, 11));
            g2.drawImage(selectedItem.getHotbarIcon(), hoverX, hoverY, null);
            g2.drawString(Integer.toString(selectedItem.getQuantity()), hoverX, hoverY);
        }
        if (displayHover) {
            ItemRecipe item = recipeCatalog.getRecipe(hoverSlot);
            if (item == null) {
                return;
            }
            String itemDescription = item.itemDescription;

            //highlight item
            g2.drawImage(highlight, (int) craftingButtons.get(hoverSlot).getX()+ 7, (int) craftingButtons.get(hoverSlot).getY()+5, 32 , 32, null);

            g2.setFont(new Font("MONOSPACED", Font.BOLD, 15));
            FontMetrics textSize = g2.getFontMetrics();
            int textWidth = Math.max(textSize.stringWidth(item.name), textSize.stringWidth(itemDescription));
            int textHeight = textSize.getHeight();
            g2.setColor(Color.black);
            g2.fillRoundRect((hoverX - 26 + hoverDisplayX / 2), (hoverY + 80 - hoverDisplayY / 2) - 46, textWidth + 17, (textHeight * 4) + 2, 9, 9);
            g2.setColor(new Color(36, 33, 52, 230));
            g2.fillRoundRect((hoverX - 25 + hoverDisplayX / 2), (hoverY + 80 - hoverDisplayY / 2) - 45, textWidth + 15, (textHeight) * 4, 9, 9);

            //3d text, resource cost
            for (int i = 0; i < item.itemRequirement.size(); i++) {
                g2.drawImage(item.itemRequirement.get(i).getItem().getHotbarIcon(), hoverX - 20 + hoverDisplayX / 2, (hoverY + 80 - hoverDisplayY) + (15 * i), 16,16, null);
                g2.setColor(Color.black);
                g2.setFont(new Font("MONOSPACED", Font.BOLD, 13));
                g2.drawString(Integer.toString(item.itemRequirement.get(i).getQuantity()), hoverX - 11 + hoverDisplayX / 2, (hoverY + 98 - hoverDisplayY) + (15 * i));
                g2.setColor(Color.white);
                g2.drawString(Integer.toString(item.itemRequirement.get(i).getQuantity()), hoverX - 10 + hoverDisplayX / 2, (hoverY + 98 - hoverDisplayY) + (15 * i));

                g2.setColor(Color.black);
                g2.drawString(item.itemRequirement.get(i).getItem().getItemName(), hoverX + hoverDisplayX / 2, (hoverY + 92 - hoverDisplayY) + (15 * i));
                g2.setColor(Color.white);
                if (!hasItemRequirement(hoverSlot, i)) {
                    g2.setColor(Color.red);
                }
                g2.drawString(item.itemRequirement.get(i).getItem().getItemName(), hoverX + 1 + hoverDisplayX / 2, (hoverY + 92 - hoverDisplayY) + (15 * i));
            }

            //3d text, item name
            g2.setFont(new Font("MONOSPACED", Font.BOLD, 15));
            g2.setColor(Color.black);
            g2.drawString(item.name, hoverX - 20 + hoverDisplayX / 2, hoverY + 72 - hoverDisplayY);
            g2.setColor(Color.white);
            g2.drawString(item.name, hoverX - 19 + hoverDisplayX / 2, hoverY + 72 - hoverDisplayY);

            //3d text, item use
            g2.setColor(Color.black);
            g2.drawString(itemDescription, hoverX - 20 + hoverDisplayX / 2, hoverY + 130 - hoverDisplayY);
            g2.setColor(Color.white);
            g2.drawString(itemDescription, hoverX - 19 + hoverDisplayX / 2, hoverY + 130 - hoverDisplayY);
        }
    }
}
