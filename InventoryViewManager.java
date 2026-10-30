
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class InventoryViewManager {

    private final InventoryView inventoryView;
    private final CraftingView craftingView;
    private BufferedImage inventoryBackground;
    private final int inventoryX = 191, inventoryY = 150;
    private final ArrayList<Rectangle> menuButtonsCollision = new ArrayList<>();
    private final int menuButtons = 4;
    private boolean showInventory = true;
    private boolean showCrafting = false;

    public InventoryViewManager(Inventory inventory) {
        this.inventoryView = new InventoryView(inventory);
        this.craftingView = new CraftingView();
        loadSprites();
        loadMenuButtons();
    }

    public void LeftClick(int mouseX, int mouseY) {
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
        if (showInventory) {
            inventoryView.LeftClick(mouseX, mouseY);
        }
        if (showCrafting) {
            craftingView.LeftClick(mouseX, mouseY);
        }
    }

    public void tick() {
        if (showInventory) {

        }
    }

    public void Hover(int mouseX, int mouseY) {
        if (showInventory) {
            inventoryView.Hover(mouseX, mouseY);
        }
    }

    private void loadSprites() {
        try {
            inventoryBackground = ImageIO.read(getClass().getResourceAsStream("/assets/Background/inventory_background.png"));
        } catch (Exception e) {
        }
    }

    private void loadMenuButtons() {
        for (int i = 0; i < menuButtons; i++) {
            menuButtonsCollision.add(new Rectangle(inventoryX + 6 + (i * 98), inventoryY + 6, 95, 17));
        }
        menuButtonsCollision.add(new Rectangle(inventoryX + (398), inventoryY + 6, 46, 17));
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(inventoryBackground, inventoryX, inventoryY, null);
        if (showInventory) {
            inventoryView.draw(g2);
        } else if (showCrafting) {
            craftingView.draw(g2);
        }

        g2.setFont(new Font("MONOSPACED", Font.BOLD, 15));
        g2.setColor(Color.black);
        g2.drawString("Inventory", (int) menuButtonsCollision.get(0).getX() + 7, (int) menuButtonsCollision.get(0).getY() + 11);
        g2.setColor(Color.white);
        g2.drawString("Inventory", (int) menuButtonsCollision.get(0).getX() + 6, (int) menuButtonsCollision.get(0).getY() + 11);

        g2.setColor(Color.black);
        g2.drawString("Crafting", (int) menuButtonsCollision.get(1).getX() + 10, (int) menuButtonsCollision.get(1).getY() + 11);
        g2.setColor(Color.white);
        g2.drawString("Crafting", (int) menuButtonsCollision.get(1).getX() + 9, (int) menuButtonsCollision.get(1).getY() + 11);
    }
}
