
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class InventoryView {

    private final int inventoryX = 191, inventoryY = 150, hoverDisplayX = 71, hoverDisplayY = 34;
    private BufferedImage itemSlot, leftClickIcon, rightClickIcon;
    private final ArrayList<Rectangle> inventoryButtonsCollision = new ArrayList<>();
    private final Inventory inventory;
    private int selectedSlot, prevSlot, hoverSlot, hoverX, hoverY;
    private final int itemButtonsRow = 3, itemButtonsCol = 9;
    private Item selectedItem, heldItem;
    private boolean displayHover, holdingItem;

    public InventoryView(Inventory inventory) {
        this.inventory = inventory;
        loadSprites();
        loadItemButtons();
    }

    public void LeftClick(int mouseX, int mouseY) {
        //undvik att döda mig för denna kod :3
        for (int i = 0; i < inventoryButtonsCollision.size(); i++) {
            if (inventoryButtonsCollision.get(i).contains(mouseX, mouseY)) {
                selectedSlot = i;
                if (!holdingItem) { //plockar upp en item
                    prevSlot = selectedSlot;
                    selectedItem = inventory.getItem(selectedSlot);
                    inventory.removeItem(prevSlot);
                    holdingItem = true;
                    break;
                }
                if (holdingItem) {
                    if (inventory.getItem(selectedSlot) == null) { //sätter en tom inventoryslot till den item du håller i
                        inventory.setItem(selectedSlot, selectedItem);
                        selectedItem = null;
                        holdingItem = false;
                    } else if (inventory.getItem(selectedSlot) != null) { //adderar itemstacks, ifall du håller i en item och trycker på samma item i en annan stack
                        if (inventory.getItem(selectedSlot).getItemName().equals(selectedItem.getItemName())) {
                            inventory.getItem(selectedSlot).increaseQuantity(selectedItem.getQuantity());
                            holdingItem = false;
                            selectedItem = null;
                        } else { //byter items, om du håller i en item och trycker på en annan item
                            heldItem = inventory.getItem(selectedSlot);
                            inventory.setItem(selectedSlot, selectedItem);
                            inventory.setItem(prevSlot, heldItem);
                            heldItem = null;
                            holdingItem = false;
                            selectedItem = null;
                        }
                    }
                }
            }
        }
    }

    public void RightClick(int mouseX, int mouseY) {
        for (int i = 0; i < inventoryButtonsCollision.size(); i++) {
            if (inventoryButtonsCollision.get(i).contains(mouseX, mouseY)) {
                selectedSlot = i;
                if (!holdingItem && inventory.getItem(selectedSlot) != null) {
                    prevSlot = selectedSlot;
                    selectedItem = new Item(inventory.getItem(selectedSlot));
                    selectedItem.setOneQuantity();
                    inventory.getItem(prevSlot).decreaseQuantity();
                    if (inventory.getItem(prevSlot).getQuantity() < 1) {
                        inventory.removeItem(prevSlot);
                    }
                    holdingItem = true;
                } else {
                    if (inventory.getItem(selectedSlot) != null && inventory.getItem(selectedSlot).getItemName().equals(selectedItem.getItemName())) {
                        inventory.getItem(prevSlot).decreaseQuantity();
                        selectedItem.increaseQuantity(1);
                        if (inventory.getItem(prevSlot).getQuantity() < 1) {
                            inventory.removeItem(prevSlot);
                        }
                    }
                }
            }
        }
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

    private void loadSprites() {
        try {
            itemSlot = ImageIO.read(getClass().getResourceAsStream("/assets/Background/itemSlot.png"));
            leftClickIcon = ImageIO.read(getClass().getResourceAsStream("/assets/Background/leftClickIcon.png"));
            rightClickIcon = ImageIO.read(getClass().getResourceAsStream("/assets/Background/RightClickIcon.png"));
        } catch (Exception e) {
        }
    }

    public void Hover(int mouseX, int mouseY) {
        hoverX = mouseX;
        hoverY = mouseY;
        displayHover = false;
        for (int i = 0; i < inventoryButtonsCollision.size(); i++) {
            if (inventoryButtonsCollision.get(i).contains(new Point(mouseX, mouseY))) {
                hoverSlot = i;
                displayHover = true;
            }
        }
    }

    public void draw(Graphics2D g2) {
        for (int i = 0; i < inventoryButtonsCollision.size(); i++) {
            g2.drawImage(itemSlot, (int) inventoryButtonsCollision.get(i).getX(), (int) inventoryButtonsCollision.get(i).getY(), null);
            Item invItem = inventory.getItem(i);
            if (invItem != null) {
                g2.setFont(new Font("MONOSPACED", Font.BOLD, 11));
                g2.setColor(Color.white);
                g2.drawString(Integer.toString(invItem.getQuantity()), (int) inventoryButtonsCollision.get(i).getX(), (int) inventoryButtonsCollision.get(i).getY() + 10);
                g2.drawImage(invItem.getHotbarIcon(), (int) inventoryButtonsCollision.get(i).getX() + 7, (int) inventoryButtonsCollision.get(i).getY() + 5, null);
            }
        }
        if (selectedItem != null) {
            g2.drawImage(selectedItem.getHotbarIcon(), hoverX, hoverY, null);
            g2.drawString(Integer.toString(selectedItem.getQuantity()), hoverX, hoverY);
        }
        if (displayHover) {
            Item item = inventory.getItem(hoverSlot);
            if (item == null) {
                return;
            }
            String itemDescription = item.getHoverDescription();

            g2.setFont(new Font("MONOSPACED", Font.BOLD, 15));
            FontMetrics textSize = g2.getFontMetrics();
            int textWidth = Math.max(leftClickIcon.getWidth() + rightClickIcon.getWidth()+70, Math.max(textSize.stringWidth(item.getItemName()), textSize.stringWidth(itemDescription)));
            int textHeight = textSize.getHeight();
            g2.setColor(Color.black);
            g2.fillRoundRect((hoverX - 46 + hoverDisplayX / 2), (hoverY - hoverDisplayY / 2) - 46, textWidth + 17, (textHeight + (leftClickIcon.getHeight() / 2) + 1) * 2, 9, 9);
            g2.setColor(new Color(36, 33, 52, 230));
            g2.fillRoundRect((hoverX - 45 + hoverDisplayX / 2), (hoverY - hoverDisplayY / 2) - 45, textWidth + 15, (textHeight + (leftClickIcon.getHeight() / 2)) * 2, 9, 9);

            //MouseIcons
            g2.drawImage(leftClickIcon, hoverX - 40 + hoverDisplayX / 2, hoverY - 23, null);
            g2.drawImage(rightClickIcon, hoverX + 15 + hoverDisplayX / 2, hoverY - 23, null);

            //3d text, mousebutton information
            g2.setColor(Color.black);
            g2.drawString("All", hoverX - 30 + hoverDisplayX / 2, hoverY - 12);
            g2.setColor(Color.white);
            g2.drawString("All", hoverX - 29 + hoverDisplayX / 2, hoverY - 12);

            g2.setColor(Color.black);
            g2.drawString("1x", hoverX + 24 + hoverDisplayX / 2, hoverY - 12);
            g2.setColor(Color.white);
            g2.drawString("1x", hoverX + 25 + hoverDisplayX / 2, hoverY - 12);

            //3d text, item name
            g2.setColor(Color.black);
            g2.drawString(item.getItemName(), hoverX - 40 + hoverDisplayX / 2, hoverY - 12 - hoverDisplayY);
            g2.setColor(Color.white);
            g2.drawString(item.getItemName(), hoverX - 39 + hoverDisplayX / 2, hoverY - 12 - hoverDisplayY);

            //3d text, item use
            g2.setColor(Color.black);
            g2.drawString(itemDescription, hoverX - 40 + hoverDisplayX / 2, hoverY + 5 - hoverDisplayY);
            g2.setColor(Color.white);
            g2.drawString(itemDescription, hoverX - 39 + hoverDisplayX / 2, hoverY + 5 - hoverDisplayY);
        }
    }
}
