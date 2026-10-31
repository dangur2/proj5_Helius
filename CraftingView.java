
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class CraftingView {
    private final RecipeCatalog craftingRecipe = new RecipeCatalog();
    private final ArrayList<Rectangle> craftingButtons = new ArrayList<>();
    private final Inventory inventory;
    private final int viewRow = 4, viewCol = 9, craftingX = 191, craftingY = 150, hoverDisplayX = 71, hoverDisplayY = 34;
    private int hoverSlot, hoverX, hoverY;
    private boolean displayHover;

    public CraftingView(Inventory inventory){
        this.inventory = inventory;
        loadCraftingButtons();
    }
    private void loadCraftingButtons() {
        for (int i = 0; i < viewRow; i++) {
            for (int j = 0; j < viewCol; j++) {
                craftingButtons.add(new Rectangle(craftingX + 6 + (j*49), craftingY + 26 + (i * 49), 46, 46));
            }
        }
    }
    public void LeftClick(int mouseX, int mouseY){

    }
    public void Hover(int mouseX, int mouseY){
        hoverX = mouseX;
        hoverY = mouseY;
        displayHover = false;
        for (int i = 0; i < craftingRecipe.getSize(); i++) {
            if(craftingButtons.get(i).contains(mouseX,mouseY)){
                hoverSlot = i;
                displayHover = true;
            }
        }
    }
    public void draw(Graphics2D g2){
        for (int i = 0; i < craftingRecipe.getSize(); i++) {
            g2.drawImage(craftingRecipe.getRecipe(i).grayedSprite, (int) craftingButtons.get(i).getX()+7, (int) craftingButtons.get(i).getY()+5, null);
        }
        if (displayHover) {
            ItemRecipe item = craftingRecipe.getRecipe(hoverSlot);
            if (item == null) {
                return;
            }
            String itemDescription = item.itemDescription;

            g2.setFont(new Font("MONOSPACED", Font.BOLD, 15));
            FontMetrics textSize = g2.getFontMetrics();
            int textWidth = Math.max(textSize.stringWidth(item.name), textSize.stringWidth(itemDescription));
            int textHeight = textSize.getHeight();
            g2.setColor(Color.black);
            g2.fillRoundRect((hoverX - 26 + hoverDisplayX / 2), (hoverY + 80 - hoverDisplayY / 2) - 46, textWidth + 17, (textHeight  * 4) + 2, 9, 9);
            g2.setColor(new Color(36, 33, 52, 230));
            g2.fillRoundRect((hoverX - 25 + hoverDisplayX / 2), (hoverY + 80 - hoverDisplayY / 2) - 45, textWidth + 15, (textHeight) * 4, 9, 9);

            //3d text, resource cost

            g2.setColor(Color.black);
            g2.drawString(Integer.toString(item.itemRequirment.get(hoverSlot).getQuantity()), hoverX - 20 + hoverDisplayX / 2, hoverY + 92 - hoverDisplayY);
            g2.setColor(Color.white);
            g2.drawString(Integer.toString(item.itemRequirment.get(hoverSlot).getQuantity()), hoverX - 19 + hoverDisplayX / 2, hoverY + 92 - hoverDisplayY);

            g2.setColor(Color.black);
            g2.drawString(item.itemRequirment.get(hoverSlot).getName(), hoverX - 5 + hoverDisplayX / 2, hoverY + 92 - hoverDisplayY);
            g2.setColor(Color.white);
            g2.drawString(item.itemRequirment.get(hoverSlot).getName(), hoverX - 4 + hoverDisplayX / 2, hoverY + 92 - hoverDisplayY);

            //3d text, item name
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
