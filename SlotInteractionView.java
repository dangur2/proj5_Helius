
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SlotInteractionView {
    private Inventory inventory;
    private Chest chest;

    public void setInventory(Inventory inventory){
        this.inventory = inventory;
    }
    public void setChest(Chest chest){
        this.chest = chest;
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


    public void drawSlots(Graphics2D g2, BufferedImage slot, ArrayList<Rectangle> list){
        for (int i = 0; i < list.size(); i++) {
            g2.drawImage(slot, (int) list.get(i).getX(), (int) list.get(i).getY(), null);
            Item item = getItem(i);
            if (item != null) {
                g2.setFont(new Font("MONOSPACED", Font.BOLD, 11));
                g2.setColor(Color.white);
                g2.drawString(Integer.toString(item.getQuantity()), (int) list.get(i).getX(), (int) list.get(i).getY() + 10 );
                g2.drawImage(item.getHotbarIcon(), (int) list.get(i).getX() + 7, (int) list.get(i).getY() + 5, null);
            }
        }
    }
    public void drawHover(Graphics2D g2, boolean displayHover, int hoverSlot, BufferedImage leftClickIcon, BufferedImage rightClickIcon, int hoverX, int hoverY, int hoverDisplayX, int hoverDisplayY){
        if (displayHover) {
            Item item = getItem(hoverSlot);
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
    public void drawCraftingHover(Graphics2D g2, boolean hasItemRequirement, ArrayList<Rectangle> list, boolean displayHover, int hoverX, int hoverY, int hoverSlot, int hoverDisplayX, int hoverDisplayY, ItemRecipe item, BufferedImage highlight){
        if (displayHover) {
            if (item == null) {
                return;
            }
            String itemDescription = item.itemDescription;

            //highlight item
            g2.drawImage(highlight, (int) list.get(hoverSlot).getX()+ 7, (int) list.get(hoverSlot).getY()+5, 32 , 32, null);

            g2.setFont(new Font("MONOSPACED", Font.BOLD, 15));
            FontMetrics textSize = g2.getFontMetrics();
            
            int textWidth = Math.max(textSize.stringWidth(item.name),textSize.stringWidth(itemDescription));
            int requirmentWidth = 0;
            for (int i = 0; i < item.itemRequirement.size();i++){
                requirmentWidth = textSize.stringWidth(item.itemRequirement.get(i).getItem().getItemName());
            }
            textWidth = Math.max(textWidth, requirmentWidth);
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
                if (!hasItemRequirement) {
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
    public void drawHoverItem(Graphics2D g2, Item selectedItem, int hoverX, int hoverY){
        if (selectedItem != null) {
            g2.drawImage(selectedItem.getHotbarIcon(), hoverX, hoverY, null);
            g2.drawString(Integer.toString(selectedItem.getQuantity()), hoverX, hoverY);
        }
    }
}
