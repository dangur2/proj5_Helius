
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Chest extends PlacedFurniture {

    private final ChestInventory chestInventory = new ChestInventory();
    private BufferedImage chestSprite;
    private boolean openChest;
    private final ArrayList<Rectangle> chestButtons = new ArrayList<>();
    private Rectangle exitButton;

    public Chest(FurnitureType type, int x, int y) {
        super(type, new Point(x,y));
        loadSprite();
        loadChestButtons();
    }
    public BufferedImage getChestSprite(){
        return chestSprite;
    }
    public Rectangle getExitButton(){
        return exitButton;
    }

    public ChestInventory getChestInventory() {
        return chestInventory;
    }
    public void closeChest(){
        openChest = false;
    }

    @Override
    public void Interact(Player player, Item selectedItem) {
        openChest = !openChest;
    }

    public ArrayList<Rectangle> getChestButtons() {
        return chestButtons;
    }

    @Override
    public void tick(double dt) {
    }

    @Override
    public void draw(Graphics2D g2, Camera camera, int x, int y) {
        if (openChest) {
            g2.drawImage(this.getFurniture().useImage, (int) x - (int) camera.getTx(), (int) y - (int) camera.getTy(), null);
        } else {
            g2.drawImage(this.getFurniture().image, (int) x - (int) camera.getTx(), (int) y- (int) camera.getTy(), null);

        }
    }

    private void loadSprite() {
        try {
            chestSprite = ImageIO.read(getClass().getResourceAsStream("/assets/Background/chestBackground.png"));
        } catch (Exception e) {
        }
    }

    private void loadChestButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                chestButtons.add(new Rectangle(197 + (j * 49), 26 + (i * 49),46,46));
            }
        }
        exitButton = new Rectangle(641, 23, 16, 16);
    }

    @Override
    public Chest CurrentChest() {
        if (openChest) {
            return this;
        }
        return null;
    }
    public boolean getOpenChest(){
        return openChest;
    }
    @Override
    public boolean isEmpty() {
        return chestInventory.isEmpty();
    }
}
