
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class ChestView {

    private Chest chest;
    private BufferedImage itemSlot, exitButton, chestBackground, leftClickIcon, rightClickIcon;
    private final ArrayList<Rectangle> chestViewButtons = new ArrayList<>();
    private final Inventory inventory;
    private final int hoverDisplayX = 71, hoverDisplayY = 34;
    private int hoverX, hoverY, hoverSlot, selectedSlot, prevSlot;
    private boolean displayHover, holdingItem;
    private Item selectedItem, heldItem;
    private final ArrayList<Rectangle> inventoryButtons;
    private final InventoryViewManager inventoryViewManager;

    public ChestView(Inventory inventory, ArrayList<Rectangle> inventoryButtons, InventoryViewManager inventoryViewManager) {
        this.inventory = inventory;
        this.inventoryButtons = inventoryButtons;
        this.inventoryViewManager = inventoryViewManager;
        
        loadSprite();
    }

    public void LeftClick(int mouseX, int mouseY) {
        if (chest.getExitButton().contains(mouseX,mouseY)) {
            inventoryViewManager.closeChest();
        }
        for (int i = 0; i < chestViewButtons.size(); i++) {
            if (chestViewButtons.get(i).contains(mouseX, mouseY)) {
                selectedSlot = i;
                if (!holdingItem) { //plockar upp en item
                    prevSlot = selectedSlot;
                    selectedItem = getItem(selectedSlot);
                    removeItem(prevSlot);
                    holdingItem = true;
                    break;
                }
                if (holdingItem) {
                    if (getItem(selectedSlot) == null) { //sätter en tom inventoryslot till den item du håller i
                        setItem(selectedSlot, selectedItem);
                        selectedItem = null;
                        holdingItem = false;
                    } else if (getItem(selectedSlot) != null) { //adderar itemstacks, ifall du håller i en item och trycker på samma item i en annan stack
                        if (getItem(selectedSlot).getItemName().equals(selectedItem.getItemName())) {
                            getItem(selectedSlot).increaseQuantity(selectedItem.getQuantity());
                            holdingItem = false;
                            selectedItem = null;
                        } else { //byter items, om du håller i en item och trycker på en annan item
                            heldItem = getItem(selectedSlot);
                            setItem(selectedSlot, selectedItem);
                            selectedItem = heldItem;
                        }
                    }
                }
            }
        }
    }

    public void Hover(int mouseX, int mouseY) {
        hoverX = mouseX;
        hoverY = mouseY;
        displayHover = false;
        for (int i = 0; i < chestViewButtons.size(); i++) {
            if (chestViewButtons.get(i).contains(new Point(mouseX, mouseY))) {
                System.out.println("slot " + i);
                hoverSlot = i;
                displayHover = true;
            }
        }
    }

    public void RightClick(int mouseX, int mouseY) {
        for (int i = 0; i < chestViewButtons.size(); i++) {
            if (chestViewButtons.get(i).contains(mouseX, mouseY)) {
                selectedSlot = i;
                if (!holdingItem && getItem(selectedSlot) != null) {
                    prevSlot = selectedSlot;
                    selectedItem = new Item(getItem(selectedSlot));
                    selectedItem.setOneQuantity();
                    getItem(prevSlot).decreaseQuantity();
                    if (getItem(prevSlot).getQuantity() < 1) {
                        removeItem(prevSlot);
                    }
                    holdingItem = true;
                } else {
                    if (getItem(selectedSlot) != null && getItem(selectedSlot).getItemName().equals(selectedItem.getItemName())) {
                        getItem(prevSlot).decreaseQuantity();
                        selectedItem.increaseQuantity(1);
                        if (getItem(prevSlot).getQuantity() < 1) {
                            removeItem(prevSlot);
                        }
                    }
                }
            }
        }
    }

    private void setItem(int slot, Item item) {
        if (slot < 36) {
            inventory.setItem(slot, item);
        }
        if (slot >= 36) {
            chest.getChestInventory().setItem(item, slot-36);
        }
    }

    private Item getItem(int slot) {
        if (slot < 36) {
            return inventory.getItem(slot);
        }
        if (slot >= 36) {
            return chest.getChestInventory().getItem(slot - 36);
        }
        return null;
    }

    private void removeItem(int slot) {
        if (slot < 36) {
            inventory.removeItem(slot);
            return;
        }
        if (slot >= 36) {
            chest.getChestInventory().removeItem(slot - 36);
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(chestBackground, 191, 20, null);
        g2.drawImage(exitButton, (int) chest.getExitButton().getX(), (int) chest.getExitButton().getY(), null);
        for (int i = 0; i < chestViewButtons.size(); i++) {
            g2.drawImage(itemSlot, (int) chestViewButtons.get(i).getX(), (int) chestViewButtons.get(i).getY(), null);
        }
        for (int i = 0; i < chestViewButtons.size(); i++) {
            Item item = getItem(i);
            if (item != null) {
                g2.setFont(new Font("MONOSPACED", Font.BOLD, 11));
                g2.setColor(Color.white);
                g2.drawString(Integer.toString(item.getQuantity()), (int) chestViewButtons.get(i).getX(), (int) chestViewButtons.get(i).getY() + 10);
                g2.drawImage(item.getHotbarIcon(), (int) chestViewButtons.get(i).getX() + 7, (int) chestViewButtons.get(i).getY() + 5, null);
            }
        }
        if (selectedItem != null) {
            g2.drawImage(selectedItem.getHotbarIcon(), hoverX, hoverY, null);
            g2.drawString(Integer.toString(selectedItem.getQuantity()), hoverX, hoverY);
        }
        if (displayHover) {
            Item item = getItem(hoverSlot);
            if (item == null) {
                return;
            }
            String itemDescription = item.getHoverDescription();

            g2.setFont(new Font("MONOSPACED", Font.BOLD, 15));
            FontMetrics textSize = g2.getFontMetrics();
            int textWidth = Math.max(leftClickIcon.getWidth() + rightClickIcon.getWidth() + 70, Math.max(textSize.stringWidth(item.getItemName()), textSize.stringWidth(itemDescription)));
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

    public void setChest(Chest currChest) {
        this.chest = currChest;

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
