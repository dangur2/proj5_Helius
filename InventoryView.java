
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class InventoryView {

    private final int inventoryX = 191, inventoryY = 150, hoverDisplayX = 71, hoverDisplayY = 34;
    private BufferedImage itemSlot, leftClickIcon, rightClickIcon;
    private final ArrayList<Rectangle> inventoryButtonsCollision = new ArrayList<>();
    private final int itemButtonsRow = 3, itemButtonsCol = 9;
    private final SlotInteraction slotInteraction;
    private final SlotInteractionView slotInteractionView;

    public InventoryView(Inventory inventory, SlotInteraction slotInteraction, SlotInteractionView slotInteractionView) {
        this.slotInteraction = slotInteraction;
        this.slotInteractionView = slotInteractionView;
        slotInteractionView.setInventory(inventory);
        loadSprites();
        loadItemButtons();
    }

    public void setSelectItem(Item selectItem){
        slotInteraction.setSelectedItem(selectItem);
        if(slotInteraction.getSelectedItem() != null){
            slotInteraction.setHoldingItem(true);
        }
    }

    public void LeftClick(int mouseX, int mouseY, boolean holdingShift) {
        if (holdingShift) {
            slotInteraction.shiftClick(mouseX, mouseY, inventoryButtonsCollision, slotInteraction.getHoverSlot());
            return;
        }
        slotInteraction.LeftClick(mouseX, mouseY, inventoryButtonsCollision);
    }

    public void RightClick(int mouseX, int mouseY) {
        slotInteraction.RightClick(mouseX, mouseY, inventoryButtonsCollision);
    }

    private void loadItemButtons() {
        for (int i = 0; i < 9; i++) {
            inventoryButtonsCollision.add(new Rectangle(inventoryX + 6 + (i * 49), inventoryY + 176, 46, 46));
        }
        for (int i = 0; i < itemButtonsRow; i++) {
            for (int j = 0; j < itemButtonsCol; j++) {
                inventoryButtonsCollision.add(new Rectangle(inventoryX + 6 + (j * 49), inventoryY + 26 + (i * 49), 46, 46));
            }
        }
    }
    public ArrayList<Rectangle> getInventoryButtons(){
        return inventoryButtonsCollision;
    }

    private void loadSprites() {
        try {
            itemSlot = ImageIO.read(getClass().getResourceAsStream("/assets/Background/itemSlot.png"));
            leftClickIcon = ImageIO.read(getClass().getResourceAsStream("/assets/Background/leftClickIcon.png"));
            rightClickIcon = ImageIO.read(getClass().getResourceAsStream("/assets/Background/RightClickIcon.png"));
        } catch (Exception e) {
        }
    }

    public void Hover(int mouseX, int mouseY) {
        slotInteraction.Hover(mouseX, mouseY, inventoryButtonsCollision);
    }

    public void draw(Graphics2D g2) {
        slotInteractionView.drawSlots(g2, itemSlot, inventoryButtonsCollision);
        slotInteractionView.drawHoverItem(g2, slotInteraction.getSelectedItem(), slotInteraction.getHoverX(), slotInteraction.getHoverY());
        slotInteractionView.drawHover(g2, slotInteraction.isDisplayHover(), slotInteraction.getHoverSlot(), leftClickIcon, rightClickIcon, slotInteraction.getHoverX(), slotInteraction.getHoverY(), hoverDisplayX, hoverDisplayY);
    }
}
