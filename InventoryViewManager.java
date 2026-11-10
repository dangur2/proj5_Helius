
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class InventoryViewManager {

    private final SlotInteraction slotInteraction;
    private final SlotInteractionView slotInteractionView;
    private final InventoryView inventoryView;
    private final CraftingView craftingView;
    private final ChestView chestView;
    private BufferedImage inventoryBackground, inventoryMenuButton;
    private final int inventoryX = 191, inventoryY = 165;
    private final ArrayList<Rectangle> menuButtonsCollision = new ArrayList<>();
    private final int menuButtons = 2;
    private boolean showCrafting = false;
    private boolean showChest = false;
    private boolean showInventory = false;
    private Chest currChest;
    private boolean holdingShift = false;

    public InventoryViewManager(Inventory inventory, ItemCatalog itemCatalog) {
        this.slotInteraction = new SlotInteraction();
        this.slotInteractionView = new SlotInteractionView();
        this.inventoryView = new InventoryView(inventory, slotInteraction, slotInteractionView);
        this.craftingView = new CraftingView(inventory, itemCatalog, this, slotInteraction, slotInteractionView);
        this.chestView = new ChestView(inventory, inventoryView.getInventoryButtons(),this, slotInteraction, slotInteractionView);

        loadSprites();
        loadMenuButtons();
    }
    public void bPressed(){
        if (currentlyDrawing()) {
            showCrafting = false;
            showInventory = false;
            closeChest();
        } else if (!currentlyDrawing()) {
            showInventory = true;
            showChest = false;
            showCrafting = false;
        }
    }
    public void setShiftPressed(boolean shiftPressed){
        holdingShift = shiftPressed;
    }

    public void swapScreen(int mouseX, int mouseY) {
        showInventory = true;
        showCrafting = false;
        inventoryView.setSelectItem(craftingView.getSelectItem());
        inventoryView.Hover(mouseX, mouseY);
    }
    public void closeChest(){
        showChest = false;
        if (currChest != null) {
            currChest.closeChest();
        }
        this.currChest = null;
    }
    public boolean currentlyDrawing(){
        return showInventory || showChest || showCrafting;
    }
    public void setChest(Chest currChest) {
        if (this.currChest == currChest) {
            return;
        }
        showChest = true;
        showInventory = false;
        showCrafting = false;
        this.currChest = currChest;
        chestView.setChest(currChest);
    }

    public void LeftClick(int mouseX, int mouseY) {
        if (!showChest) {
            for (int i = 0; i < menuButtonsCollision.size(); i++) {
                if (menuButtonsCollision.get(i).contains(new Point(mouseX, mouseY))) {
                    switch (i) {
                        case 0 -> {
                            showInventory = true;
                            showCrafting = false;
                        }
                        case 1 -> {
                            showCrafting = true;
                            showInventory = false;
                        }
                    }
                }
            }
        }

        if (showInventory) {
            inventoryView.LeftClick(mouseX, mouseY, holdingShift);
        }
        if (showCrafting) {
            craftingView.LeftClick(mouseX, mouseY);
        }
        if (showChest) {
            chestView.LeftClick(mouseX, mouseY, holdingShift);
        }
    }

    public void RightClick(int mouseX, int mouseY) {
        if (showInventory) {
            inventoryView.RightClick(mouseX, mouseY);
        }
        if (showChest) {
            chestView.RightClick(mouseX, mouseY);
        }
    }

    public void Hover(int mouseX, int mouseY) {
        if (showInventory) {
            inventoryView.Hover(mouseX, mouseY);
        }
        if (showCrafting) {
            craftingView.Hover(mouseX, mouseY);
        }
        if (showChest) {
            chestView.Hover(mouseX, mouseY);
        }
    }

    private void loadSprites() {
        try {
            inventoryBackground = ImageIO.read(getClass().getResourceAsStream("/assets/Background/inventory_background.png"));
            inventoryMenuButton = ImageIO.read(getClass().getResourceAsStream("/assets/Background/inventoryMenuButton.png"));
        } catch (Exception e) {
        }
    }

    private void loadMenuButtons() {
        for (int i = 0; i < menuButtons; i++) {
            menuButtonsCollision.add(new Rectangle(inventoryX + 6 + (i * 98), inventoryY - 17, 95, 17));
        }
    }

    public void draw(Graphics2D g2) {
        if (!showChest && (showCrafting || showInventory)) {
            g2.drawImage(inventoryBackground, inventoryX, inventoryY, null);
        }

        if (currChest == null) {
            for (Rectangle i : menuButtonsCollision) {
                g2.drawImage(inventoryMenuButton, (int) i.getX(), (int) i.getY(), null);
            }
            g2.setFont(new Font("MONOSPACED", Font.BOLD, 15));
            g2.setColor(Color.black);
            g2.drawString("Inventory", (int) menuButtonsCollision.get(0).getX() + 7, (int) menuButtonsCollision.get(0).getY() + 12);
            g2.setColor(Color.white);
            g2.drawString("Inventory", (int) menuButtonsCollision.get(0).getX() + 6, (int) menuButtonsCollision.get(0).getY() + 12);

            g2.setColor(Color.black);
            g2.drawString("Crafting", (int) menuButtonsCollision.get(1).getX() + 10, (int) menuButtonsCollision.get(1).getY() + 12);
            g2.setColor(Color.white);
            g2.drawString("Crafting", (int) menuButtonsCollision.get(1).getX() + 9, (int) menuButtonsCollision.get(1).getY() + 12);
        }
        if (showInventory) {
            inventoryView.draw(g2);
        }
        if (showCrafting) {
            craftingView.draw(g2);
        }
        if (showChest) {
            chestView.draw(g2);
        }
    }
}
