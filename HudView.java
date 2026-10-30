
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class HudView {

    //bry dig inte om alla dessa variabler :3
    private BufferedImage inventoryBackground, inventoryTooltip, tooltipDestroy, tooltipUse, hoverDisplay;
    private final Player player;
    private final int hotbarX = 191, hotbarY = 415;
    private final int column = 9;
    private final int slotSize = 46, slotSpacing = 49, highlightPadding = 6, numberPadding = 10, itemPaddingX = 5, itemPaddingY = 7;
    private final int tooltipPadding = 65, tooltipTextPaddingX = 35, tooltipTextPaddingY = 30, hoverDisplayX = 71, hoverDisplayY = 34 ;
    private int hoverX, hoverY;
    private final ArrayList<Rectangle> slots = new ArrayList<>();
    private final ArrayList<Rectangle> destroyTooltip = new ArrayList<>();
    private final ArrayList<Rectangle> useTooltip = new ArrayList<>();
    private boolean showTooltip, displayHover = false;
    private int selectedSlot, hoveredSlot;
    private final GamePanel gp;

    public HudView(Player player, GamePanel gp) {
        this.player = player;
        this.gp = gp;
        loadBackground();
        createSlots();
        createDestroyTooltip();
        createUseTooltip();
    }

    public ArrayList<Rectangle> getSlots() {
        return slots;
    }

    public void LeftClick(int x, int y) {
        if (showTooltip) {
            if (!destroyTooltip.get(selectedSlot).contains(x, y) && !useTooltip.get(selectedSlot).contains(x, y)) {
                showTooltip = false;
            }
        }
        //ta bort en item om du trycker på destroy
        if (destroyTooltip.get(selectedSlot).contains(x, y) && showTooltip) {
            player.getInventory().removeItem(selectedSlot);
            gp.playSound(SoundEffect.RESOURCEBREAK);
            showTooltip = false;
            return;
        }
        //använder en item och om den har heal, så healar den för den mängden och spelar ett ljud
        if (useTooltip.get(selectedSlot).contains(x, y) && showTooltip) {
            int heal = player.getInventory().useItem(selectedSlot);
            player.increaseHealth(heal);
            player.increaseStamina(heal);
            gp.playSound(SoundEffect.USE);
            showTooltip = false;
            return;
        }
        //vänsterklick på en slot gör den till selected slot som highlightar den slotten
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).contains(x, y)) {
                showTooltip = false;
                selectedSlot = i;
                return;
            }
        }
    }

    public Item getSelectedItem() {
        return player.getInventory().getItem(selectedSlot);
    }
    public void setSelectItem(int x){
        this.selectedSlot = x;
    }
    public int getSelectedSlot() {
        return selectedSlot;
    }

    public void RightClick(int x, int y) {
        //om du högerklickar en slot, oavsett om den är highlightad eller inte, sålänge den har en item, öppna tooltip på den itemen
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).contains(x, y)) {
                selectedSlot = i;
                if (slots.get(selectedSlot).contains(x, y)) {
                    showTooltip = !showTooltip;
                    if (showTooltip) {
                        displayHover = false;
                    }
                }
            }
        }
    }

    public void Hover(int x, int y) {
        hoverX = x;
        hoverY = y;
        displayHover = false;
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).contains(x, y)) {
                hoveredSlot = i;
                displayHover = true;
            }
        }
    }

    private void createDestroyTooltip() {
        for (int i = 0; i < column; i++) {
            destroyTooltip.add(new Rectangle(hotbarX + tooltipTextPaddingX + (i % column * slotSpacing), hotbarY - tooltipTextPaddingY, 61, 15));
        }
    }

    private void createUseTooltip() {
        for (int i = 0; i < column; i++) {
            useTooltip.add(new Rectangle(hotbarX + tooltipTextPaddingX + (i % column * slotSpacing), hotbarY - (tooltipTextPaddingY + 20), 61, 15));
        }
    }

    private void createSlots() {
        for (int i = 0; i < column; i++) {
            slots.add(new Rectangle(hotbarX + highlightPadding + (i % column * slotSpacing), hotbarY + highlightPadding, slotSize, slotSize));
        }
    }

    private void loadBackground() {
        try {
            inventoryBackground = ImageIO.read(getClass().getResourceAsStream("/assets/Background/hotbar_background.png"));
            inventoryTooltip = ImageIO.read(getClass().getResourceAsStream("/assets/Background/hotbarTooltip.png"));
            tooltipDestroy = ImageIO.read(getClass().getResourceAsStream("/assets/Font/tooltipDestroy.png"));
            tooltipUse = ImageIO.read(getClass().getResourceAsStream("/assets/Font/tooltipUse.png"));
        } catch (Exception e) {
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(inventoryBackground, hotbarX, hotbarY, null); //ritar backgrunden av hotbaren
        for (int i = 0; i < column; i++) {
            if (i == selectedSlot) { //ritar highlight runt en slot
                g2.setColor(Color.white);
                g2.drawRect(slots.get(i).x, slots.get(i).y, slotSize, slotSize);
                if (showTooltip) {
                    g2.drawImage(inventoryTooltip, slots.get(i).x, slots.get(i).y - tooltipPadding, 120, 60, null);
                    g2.drawImage(tooltipDestroy, destroyTooltip.get(i).x, destroyTooltip.get(i).y, null);
                    g2.drawImage(tooltipUse, useTooltip.get(i).x, useTooltip.get(i).y, null);
                }
            }
        }
        for (int i = 0; i < slots.size(); i++) { //ritar item med rätt siffra av mängd item + bilden av itemen. förlåt mig gud för alla magic numbers
            if (!player.getInventory().isEmpty(i)) {
                g2.setFont(new Font("MONOSPACED", Font.BOLD, 11));
                g2.setColor(Color.white);
                g2.drawString(Integer.toString(player.getInventory().getItem(i).getQuantity()), slots.get(i).x, slots.get(i).y + numberPadding); //quantity av item
                g2.drawImage(player.getInventory().getItem(i).getHotbarIcon(), slots.get(i).x + itemPaddingX, slots.get(i).y + itemPaddingY, null); //item ikonen
            }
        }
        if (displayHover) {
            Item item = player.getInventory().getItem(hoveredSlot);
            if(item == null){
                return;
            }
            String itemDescription = item.getHoverDescription();

            g2.setFont(new Font("MONOSPACED", Font.BOLD, 15));
            FontMetrics textSize = g2.getFontMetrics();
            int textWidth = Math.max(textSize.stringWidth(item.getItemName()), textSize.stringWidth(itemDescription));
            int textHeight = textSize.getHeight();
            g2.setColor(Color.black);
            g2.fillRoundRect((hoverX - 46 + hoverDisplayX / 2), (hoverY - hoverDisplayY / 2) - 46, textWidth + 17, (textHeight+1) * 2, 9, 9);
            g2.setColor(new Color(36, 33, 52, 230));
            g2.fillRoundRect((hoverX - 45 + hoverDisplayX / 2), (hoverY - hoverDisplayY / 2) - 45, textWidth + 15, (textHeight) * 2, 9, 9);

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
