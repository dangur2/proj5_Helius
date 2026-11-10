
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class ChestView {

    private Chest chest;
    private BufferedImage itemSlot, exitButton, chestBackground, leftClickIcon, rightClickIcon;
    private final ArrayList<Rectangle> chestViewButtons = new ArrayList<>();
    private final int hoverDisplayX = 71, hoverDisplayY = 34;
    private final ArrayList<Rectangle> inventoryButtons;
    private final InventoryViewManager inventoryViewManager;
    private final SlotInteraction slotInteraction;
    private final SlotInteractionView slotInteractionView;

    public ChestView(Inventory inventory, ArrayList<Rectangle> inventoryButtons, InventoryViewManager inventoryViewManager, SlotInteraction slotInteraction, SlotInteractionView slotInteractionView) {
        this.slotInteraction = slotInteraction;
        this.slotInteractionView = slotInteractionView;
        this.inventoryButtons = inventoryButtons;
        this.inventoryViewManager = inventoryViewManager;

        slotInteraction.setInventory(inventory);
        slotInteractionView.setInventory(inventory);
        loadSprite();
    }

    public void LeftClick(int mouseX, int mouseY, boolean holdingShift) {
        if (holdingShift) {
            slotInteraction.shiftClick(mouseX, mouseY, chestViewButtons, slotInteraction.getHoverSlot());
            return;
        }
        slotInteraction.LeftClick(mouseX, mouseY, chestViewButtons);
        if (chest.getExitButton().contains(mouseX,mouseY)) {
            inventoryViewManager.closeChest();
        }
    }
    public void closeSprite(){
        chest.closeChest();
    }

    public void Hover(int mouseX, int mouseY) {
        slotInteraction.Hover(mouseX, mouseY, chestViewButtons);
    }

    public void RightClick(int mouseX, int mouseY) {
        slotInteraction.RightClick(mouseX, mouseY, chestViewButtons);
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(chestBackground, 191, 20, null);
        g2.drawImage(exitButton, (int) chest.getExitButton().getX(), (int) chest.getExitButton().getY(), null);
        slotInteractionView.drawSlots(g2, itemSlot, chestViewButtons);
        slotInteractionView.drawHoverItem(g2, slotInteraction.getSelectedItem(), slotInteraction.getHoverX(), slotInteraction.getHoverY());
        slotInteractionView.drawHover(g2, slotInteraction.isDisplayHover(), slotInteraction.getHoverSlot(), leftClickIcon, rightClickIcon, slotInteraction.getHoverX(), slotInteraction.getHoverY(), hoverDisplayX, hoverDisplayY);
    }

    public void setChest(Chest currChest) {
        this.chest = currChest;
        slotInteraction.setChest(chest);
        slotInteractionView.setChest(chest);

        chestViewButtons.clear();
        for (Rectangle i : inventoryButtons) {
            this.chestViewButtons.add(new Rectangle((int) i.getX(), (int) i.getY() + 10, (int) i.getWidth(), (int) i.getHeight()));
        }
        this.chestViewButtons.addAll(chest.getChestButtons());
    }

    private void loadSprite() {
        try {
            itemSlot = ImageIO.read(getClass().getResourceAsStream("/assets/Background/itemSlot.png"));
            exitButton = ImageIO.read(getClass().getResourceAsStream("/assets/Background/exit.png"));
            chestBackground = ImageIO.read(getClass().getResourceAsStream("/assets/Background/chestBackground.png"));
            leftClickIcon = ImageIO.read(getClass().getResourceAsStream("/assets/Background/leftClickIcon.png"));
            rightClickIcon = ImageIO.read(getClass().getResourceAsStream("/assets/Background/RightClickIcon.png"));
        } catch (Exception e) {
        }
    }
}
