
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

    private final int inventoryX = 191, inventoryY = 150;
    private BufferedImage itemSlot, hoverDisplay;
    private final ArrayList<Rectangle> inventoryButtonsCollision = new ArrayList<>();
    private final ArrayList<Rectangle> hotbarButtonsCollision = new ArrayList<>();
    private final Inventory inventory;
    private int selectedSlot, prevSlot, hoverSlot;
    private final int menuButtons = 4, itemButtonsRow = 3, itemButtonsCol = 9;
    private Item selectedItem;
    private Item heldItem;
    private int hoverX, hoverY;
    private boolean displayHover, holdingItem;

    public InventoryView(Inventory inventory) {
        this.inventory = inventory;
        loadSprites();
        loadItemButtons();
    }

    public void LeftClick(int mouseX, int mouseY) {
        //undvik att döda mig för denna kod :3
        for (int i = 0; i < inventoryButtonsCollision.size(); i++) {
            if (inventoryButtonsCollision.get(i).contains(new Point(mouseX, mouseY))) {
                selectedSlot = i + 9;
                if (!holdingItem) {
                    prevSlot = selectedSlot;
                    selectedItem = inventory.getItem(selectedSlot);
                    inventory.removeItem(prevSlot);
                    holdingItem = true;
                    break;
                }
                if (holdingItem) {
                    if (inventory.getItem(selectedSlot) == null) {
                        inventory.setItem(selectedSlot, selectedItem);
                        selectedItem = null;
                        holdingItem = false;
                    } else if (inventory.getItem(selectedSlot) != null) {
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
        for (int i = 0; i < hotbarButtonsCollision.size(); i++) {
            if (hotbarButtonsCollision.get(i).contains(new Point(mouseX, mouseY))) {
                selectedSlot = i;
                if (!holdingItem) {
                    prevSlot = selectedSlot;
                    selectedItem = inventory.getItem(selectedSlot);
                    inventory.removeItem(prevSlot);
                    holdingItem = true;
                    break;
                }
                if (holdingItem) {
                    if (inventory.getItem(selectedSlot) == null) {
                        inventory.setItem(selectedSlot, selectedItem);
                        selectedItem = null;
                        holdingItem = false;
                    } else if (inventory.getItem(selectedSlot) != null) {
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

    private void loadItemButtons() {
        for (int i = 0; i < itemButtonsRow; i++) {
            for (int j = 0; j < itemButtonsCol; j++) {
                inventoryButtonsCollision.add(new Rectangle(inventoryX + 6 + (j * 49), inventoryY + 26 + (i * 49), 46, 46));
            }
        }
        for (int i = 0; i < 9; i++) {
            hotbarButtonsCollision.add(new Rectangle(inventoryX + 6 + (i * 49), inventoryY + 176, 46, 46));
        }
    }

    private void loadSprites() {
        try {
            itemSlot = ImageIO.read(getClass().getResourceAsStream("/assets/Background/itemSlot.png"));
            hoverDisplay = ImageIO.read(getClass().getResourceAsStream("/assets/Background/hoverDisplay.png"));
        } catch (Exception e) {
        }
    }

    public void Hover(int mouseX, int mouseY) {
        hoverX = mouseX;
        hoverY = mouseY;
        displayHover = false;
        for (int i = 0; i < inventoryButtonsCollision.size(); i++) {
            if (inventoryButtonsCollision.get(i).contains(new Point(mouseX, mouseY))) {
                hoverSlot = i + 9;
                displayHover = true;
            }
        }
        for (int i = 0; i < hotbarButtonsCollision.size(); i++) {
            if (hotbarButtonsCollision.get(i).contains(new Point(mouseX, mouseY))) {
                hoverSlot = i;
                displayHover = true;
            }
        }
    }

    public void draw(Graphics2D g2) {
        for (int i = 0; i < inventoryButtonsCollision.size(); i++) {
            g2.drawImage(itemSlot, (int) inventoryButtonsCollision.get(i).getX(), (int) inventoryButtonsCollision.get(i).getY(), null);
            Item invItem = inventory.getItem(i + 9);
            if (invItem != null) {
                g2.setFont(new Font("MONOSPACED", Font.BOLD, 11));
                g2.setColor(Color.white);
                g2.drawString(Integer.toString(invItem.getQuantity()), (int) inventoryButtonsCollision.get(i).getX(), (int) inventoryButtonsCollision.get(i).getY() + 10);
                g2.drawImage(invItem.getHotbarIcon(), (int) inventoryButtonsCollision.get(i).getX() + 7, (int) inventoryButtonsCollision.get(i).getY() + 5, null);
            }
        }
        for (int i = 0; i < hotbarButtonsCollision.size(); i++) {
            g2.drawImage(itemSlot, (int) hotbarButtonsCollision.get(i).getX(), (int) hotbarButtonsCollision.get(i).getY(), null);
            if (inventory.getItem(i) != null) {
                g2.setFont(new Font("MONOSPACED", Font.BOLD, 11));
                g2.setColor(Color.white);
                g2.drawString(Integer.toString(inventory.getItem(i).getQuantity()), (int) hotbarButtonsCollision.get(i).getX(), (int) hotbarButtonsCollision.get(i).getY() + 10);
                g2.drawImage(inventory.getItem(i).getHotbarIcon(), (int) hotbarButtonsCollision.get(i).getX() + 7, (int) hotbarButtonsCollision.get(i).getY() + 5, null);
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
            String effect = item.getHoverDescription();

            g2.setFont(new Font("MONOSPACED", Font.BOLD, 15));
            FontMetrics textSize = g2.getFontMetrics();
            int textWidth = Math.max(textSize.stringWidth(item.getItemName()), textSize.stringWidth(effect));
            int textHeight = textSize.getHeight();
            g2.setColor(new Color(36, 33, 52, 230));
            g2.fillRoundRect((hoverX - 45 + hoverDisplay.getWidth() / 2), (hoverY - hoverDisplay.getHeight() / 2) - 45, textWidth + 15, (textHeight) * 2, 9, 9);

            //3d text, item name
            g2.setColor(Color.black);
            g2.drawString(item.getItemName(), hoverX - 40 + hoverDisplay.getWidth() / 2, hoverY - 12 - hoverDisplay.getHeight());
            g2.setColor(Color.white);
            g2.drawString(item.getItemName(), hoverX - 39 + hoverDisplay.getWidth() / 2, hoverY - 12 - hoverDisplay.getHeight());

            //3d text, item use
            g2.setColor(Color.black);
            g2.drawString(effect, hoverX - 40 + hoverDisplay.getWidth() / 2, hoverY + 5 - hoverDisplay.getHeight());
            g2.setColor(Color.white);
            g2.drawString(effect, hoverX - 39 + hoverDisplay.getWidth() / 2, hoverY + 5 - hoverDisplay.getHeight());
        }

        //debug för itemknapparna
        // for (int i = 0; i < inventoryButtonsCollision.size(); i++) {
        //     Rectangle button = inventoryButtonsCollision.get(i);
        //     g2.setColor(Color.red);
        //     g2.drawRect((int) button.getX(), (int) button.getY(), (int) button.getWidth(), (int) button.getHeight());
        // }
        // for (int i = 0; i < hotbarButtonsCollision.size(); i++) {
        //     Rectangle button = hotbarButtonsCollision.get(i);
        //     g2.setColor(Color.red);
        //     g2.drawRect((int) button.getX(), (int) button.getY(), (int) button.getWidth(), (int) button.getHeight());
        // }
    }
}
