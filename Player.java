
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Player extends Entity {
    private final Inventory inventory;
    private int currHealth, currStamina;
    private final int maxHealth = 100, maxStamina = 100;
    private final GamePanel gp;
    private double length;
    private boolean attacking = false;
    private boolean harvesting = false;
    private boolean previewToolHit = false;
    private int targetCol, targetRow;
    private Image attImage;
    private double attImageRot;
    private final TileManager tm;
    private Item selectedItem;
    private Point handPoint, toolPoint;

    public Player(TileManager tm, GamePanel gp) {
        this.gp = gp;
        this.currHealth = 100;
        this.currStamina = 100;
        this.tm = tm;
        collArea = new Rectangle(10, 25, 12, 7);
        inventory = new Inventory(new Item[36]);
        setStartingValues(tm.findFreePosition());
        setPlayerImage();
    }

    public void increaseHealth(int x) {
        this.currHealth += x;
        if (currHealth > maxHealth) {
            currHealth = maxHealth;
        }
    }

    public void increaseStamina(int x) {
        this.currStamina += x;
        if (currStamina > maxStamina) {
            currStamina = maxStamina;
        }
    }

    public int getHealth() {
        return this.currHealth;
    }

    public int getStamina() {
        return this.currStamina;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public int getPlayerX() {
        return playerSizeX;
    }

    public int getPlayerY() {
        return playerSizeY;
    }

    public boolean isHarvesting() {
        return harvesting;
    }

    public void spendStamina(int x) {
        this.currStamina -= x;
        if (currStamina - x < 0) {
            currStamina = 0;
        }
    }

    public void updateTarget(int mx, int my, double tx, double ty, Item currItem) {

        double mdx = mx - ((x + playerSizeX / 2) - tx); //mus position/klick i relation till spelarens skärmposition
        double mdy = my - ((y + playerSizeY / 2) - ty);

        int pCol = (int) (x + playerSizeX / 2) / gp.tileSize; //spelarens world col baserad på mittpunkten av spelaren
        int pRow = (int) (y + playerSizeY / 2) / gp.tileSize;

        int dRow = 0;
        int dCol = 0;

        if (Math.abs(mdx) > 0.4 * Math.abs(mdy)) {
            dCol += mdx > 0 ? 1 : -1;   //Ternary operator, om mdx > 0, sätt dCol till 1, annars -1
        }
        if (Math.abs(mdy) > 0.4 * Math.abs(mdx)) {
            dRow += mdy > 0 ? 1 : -1;
        }
        if (Math.abs(mdx) > Math.abs(mdy)) {
            attDir = mdx > 0 ? attackDirection.RIGHT : attackDirection.LEFT;
        }
        targetCol = pCol + dCol; //col eller row som spelaren kan interagera med tools. alltså spelaren +/- 1 tile.
        targetRow = pRow + dRow;

        if (currItem == null) {
            previewToolHit = false;
            return;
        }

        previewToolHit = currItem.isTool() || currItem.getItemType() == ItemType.FURNITURE;
    }

    public int getTargetCol() {
        return targetCol;
    }

    public int getTargetRow() {
        return targetRow;
    }

    public void LeftClick(int mx, int my, int selectedSlot, ArrayList<Rectangle> slots, WorldSpawn ws) {
        if (attacking || harvesting) {
            return;
        }
        selectedItem = inventory.getItem(selectedSlot);

        if (selectedItem == null || slots.size() <= selectedSlot || slots.get(selectedSlot).contains(mx, my) || selectedItem.getItemType() == ItemType.CONSUMABLE) {
            //returnar tidigt om saken man håller i inte kan slåss
            return;
        }

        attArea = new Rectangle();
        attImageRot = 0; //resettar rotationen på itemspriten

        setAttackImage(selectedItem);

        boolean hit = false;

        Tile lookedTile = tm.getTileNum(targetCol, targetRow);
        switch (selectedItem.getItemType()) {
            case HOE, SHOVEL -> {
                hit = canHarvest(lookedTile, selectedItem);
                if (hit && currStamina > 0) {
                    tm.setTile(targetCol, targetRow, lookedTile.setTile);
                    if (selectedItem.getItemType() == ItemType.HOE) {
                        gp.playSound(SoundEffect.HOE);
                    }
                    if (selectedItem.getItemType() == ItemType.SHOVEL) {
                        gp.playSound(SoundEffect.SHOVEL);
                    }
                }
                harvesting = true;
            }
            case AXE, PICKAXE -> {
                if (currStamina > 0) {
                    hit = ws.hitResource(targetCol, targetRow, selectedItem.getItemType(), selectedItem.getDmg());
                    if (selectedItem.getItemType() == ItemType.AXE) {
                        gp.playSound(SoundEffect.AXE);
                    }
                    if (selectedItem.getItemType() == ItemType.PICKAXE) {
                        gp.playSound(SoundEffect.PICKAXE);
                    }
                    harvesting = true;
                }

            }
            case SWORD -> {
                attacking = true;
                gp.playSound(SoundEffect.SWORD);
            }
            case FURNITURE -> {
                if (!tm.isSolid((int) targetCol * gp.tileSize, (int) targetRow * gp.tileSize)) {
                    if (ws.PlaceFurniture(targetCol, targetRow, selectedItem)) {
                        inventory.decreaseQuantity(selectedSlot);
                    }
                }
            }
        }
        if (hit) {
            if (currStamina > 0) {
                spendStamina(1);
            }
        }
    }

    public void RightClick(int mouseX, int mouseY, WorldSpawn worldSpawn, ArrayList<Rectangle> slots) {
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).contains(mouseX, mouseY)) {
                return;
            }
        }
        worldSpawn.Interact(targetCol, targetRow, selectedItem);
    }

    private boolean canHarvest(Tile tileLookedAt, Item item) {
        return tileLookedAt.requiredTool == item.getItemType();
    }

    private void setAttackImage(Item item) {
        attImage = item.getHeldIcon();
    }

    private void setStartingValues(Point p) {
        speed = 150; //150 default
        x = p.x;
        y = p.y;
        playerCollision = true;
        direction = Direction.IDLE;
    }

    private void setPlayerImage() {
        //förladda sprites när man gör gubben
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/char_backWalk1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/char_backWalk2.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/char_backWalk3.png"));
            up4 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/char_backWalk4.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/char_frontWalk1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/char_frontWalk2.png"));
            down3 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/char_frontWalk3.png"));
            down4 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/char_frontWalk4.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/char_leftWalk1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/char_leftWalk2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/char_leftWalk3.png"));
            left4 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/char_leftWalk4.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/char_rightWalk1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/char_rightWalk2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/char_rightWalk3.png"));
            right4 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/char_rightWalk4.png"));
            idle1 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/char_idle1.png"));
            idle2 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/char_idle2.png"));
            idle3 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/char_idle3.png"));
            idle4 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/char_idle4.png"));
            attRight1 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/char_rightTool1.png"));
            attRight2 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/char_rightTool2.png"));
            attRight3 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/char_rightTool3.png"));
            attRight4 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/char_rightTool4.png"));
            attLeft1 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/char_leftTool1.png"));
            attLeft2 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/char_leftTool2.png"));
            attLeft3 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/char_leftTool3.png"));
            attLeft4 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/char_leftTool4.png"));
            tileHighLight = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/highlight_tile.png"));
        } catch (IOException | IllegalArgumentException e) {
            throw new IllegalStateException("no find sprite amigo", e);
        }
    }

    public void updateAttack() {
        if (attDir == null) {
            return;
        }
        if (attacking || harvesting) {
            attNum++;
            toolPoint = new Point(16,28);
            switch (attDir) {
                case RIGHT -> {
                    switch (attNum) {
                        case 5 -> {handPoint = new Point(20,15); attImageRot = Math.toRadians(30);}
                        case 10 -> {handPoint = new Point(22,17); attImageRot = Math.toRadians(60);}
                        case 15 -> {handPoint = new Point(22,20); attImageRot = Math.toRadians(90);}
                    }
                    attArea = new Rectangle((int) x + 23, (int) y + 19, 20, 10);
                }
                case LEFT -> {
                    switch (attNum) {
                        case 5 -> {handPoint = new Point(11,15); attImageRot = Math.toRadians(330);}
                        case 10 -> {handPoint = new Point(9,17); attImageRot = Math.toRadians(300);}
                        case 15 -> {handPoint = new Point(9,20); attImageRot = Math.toRadians(270);}
                    }
                    attArea = new Rectangle((int) x - 12, (int) y + 19, 20, 10);
                }
            }
            if (attNum > 20) {
                attacking = false;
                harvesting = false;
                attNum = 1;
            }
        }
    }

    public void move(double dt, double dx, double dy, int worldSizeX, int worldsizeY, TileManager tm) {
        length = Math.sqrt(dx * dx + dy * dy); //1,0 = 1, 0,0 = 0, 1,1 = 1,4

        if (length == 0 || (attacking || harvesting)) {
            direction = Direction.IDLE;
            updateWalkingSprite();
            return; //Om jag inte rör mig, ingen mening att fortsätta
        }

        dx = dx / length; //normalisera värdet, ex. 0,7 = 1 / 1.4 (om två directions hålls inne samtidigt)
        dy = dy / length;

        //kolla om tilen man går mot är solid för collision
        double newX = x + dx * speed * dt;
        double newY = y + dy * speed * dt;

        //kolla om du kan gå höger eller vänster
        if (tm.canStandAt(newX, y, collArea)) {
            x = newX;
        }
        //kolla om du kan gå upp eller ner
        if (tm.canStandAt(x, newY, collArea)) {
            y = newY;
        }

        //updatera riktningen på spelaren för matcha animationen
        if (dx >= 1) {
            direction = Direction.RIGHT;
        }
        if (dx <= -1) {
            direction = Direction.LEFT;
        }
        if (dy >= 1) {
            direction = Direction.DOWN;
        }
        if (dy <= -1) {
            direction = Direction.UP;
        }

        updateWalkingSprite();

    }

    private void updateWalkingSprite() {
        spriteCounter++;

        //hastigheten av hur snabbt den byter sprite vid gång
        if (spriteCounter > 10) {
            switch (walkNum) {
                case 1 -> {
                    if (length > 0) {
                        gp.playSound(SoundEffect.STEP);
                    }
                    walkNum = 2;
                }
                case 2 ->
                    walkNum = 3;
                case 3 -> {
                    if (length > 0) {
                        gp.playSound(SoundEffect.STEP);
                    }
                    walkNum = 4;
                }
                case 4 ->
                    walkNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2, Camera camera) {
        BufferedImage image = null;

        if (attacking || harvesting) {
            if (attDir == null) {
                return;
            }
            switch (attDir) {
                case attackDirection.LEFT -> {
                    if (attNum > 5) {
                        image = attLeft2;
                    }
                    else if (attNum > 10) {
                        image = attLeft3;
                    }
                    else  {
                        image = attLeft4;
                    }
                }
                case attackDirection.RIGHT -> {
                    if (attNum > 5) {
                        image = attRight2;
                    }
                    else if (attNum > 10) {
                        image = attRight3;
                    }
                    else {
                        image = attRight4;
                    }
                }
            }
        } else {
            switch (direction) {
                case Direction.UP -> {
                    switch (walkNum) {
                        case 1 ->
                            image = up1;
                        case 2 ->
                            image = up2;
                        case 3 ->
                            image = up3;
                        case 4 ->
                            image = up4;
                    }
                }
                case Direction.DOWN -> {
                    switch (walkNum) {
                        case 1 ->
                            image = down1;
                        case 2 ->
                            image = down2;
                        case 3 ->
                            image = down3;
                        case 4 ->
                            image = down4;
                    }
                }
                case Direction.RIGHT -> {
                    switch (walkNum) {
                        case 1 ->
                            image = right1;
                        case 2 ->
                            image = right2;
                        case 3 ->
                            image = right3;
                        case 4 ->
                            image = right4;
                    }
                }
                case Direction.LEFT -> {
                    switch (walkNum) {
                        case 1 ->
                            image = left1;
                        case 2 ->
                            image = left2;
                        case 3 ->
                            image = left3;
                        case 4 ->
                            image = left4;
                    }
                }
                case Direction.IDLE -> {
                    switch (walkNum) {
                        case 1 ->
                            image = idle1;
                        case 2 ->
                            image = idle2;
                        case 3 ->
                            image = idle3;
                        case 4 ->
                            image = idle4;
                    }
                }
            }
        }

        int playerX = ((int) x - (int) camera.getTx());
        int playerY = ((int) y - (int) camera.getTy());

        g2.drawImage(image, playerX, playerY, playerSizeX, playerSizeY, null);

        if (attacking || harvesting) {
            if (handPoint == null) {
                return;
            }
            int pivotX = (int) playerX + handPoint.x; 
            int pivotY = (int) playerY + handPoint.y;
            AffineTransform oldTrans = g2.getTransform();
            g2.rotate(attImageRot, pivotX, pivotY);
            if (attDir == attackDirection.LEFT) {
                g2.drawImage(attImage, (int) playerX + 26, (int) playerY-9, -32,32,null);
            }
            else{
                g2.drawImage(attImage, (int) pivotX - toolPoint.x, (int) pivotY - toolPoint.y,null);
            }
            g2.setTransform(oldTrans);
        }
        if (previewToolHit) {
            g2.drawImage(tileHighLight, targetCol * gp.tileSize - (int) camera.getTx(), targetRow * gp.tileSize - (int) camera.getTy(), null);
        }

    }

    public Rectangle getBounds() {
        return collArea;
    }

    public Rectangle getCollison() {
        return new Rectangle((int) x + collArea.x, (int) y + collArea.y, playerSizeX, playerSizeY);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}
