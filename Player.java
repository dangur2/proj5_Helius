
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
    private int currHealth;
    private final int maxHealth = 100;
    private int dmgCounter, attackCounter;
    private final GamePanel gp;
    private double length;
    private boolean playerLost = false;
    private boolean attacking = false;
    private boolean harvesting = false;
    private Image attImage;
    private double attImageRot;
    private final TileManager tm;

    public Player(TileManager tm, GamePanel gp) {
        this.gp = gp;
        this.currHealth = 100;
        this.tm = tm;
        collArea = new Rectangle(10, 25, 12, 7);
        inventory = new Inventory(new ArrayList<>());
        setStartingValues(tm.findFreePosition());
        setPlayerImage();
    }

    public void increaseHealth(int x) {
        this.currHealth += x;
        if (currHealth > maxHealth) {
            currHealth = maxHealth;
        }
    }

    public int getHealth() {
        return this.currHealth;
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

    public void LeftClick(int mx, int my, int selectedSlot, ArrayList<Rectangle> slots, double tx, double ty) {

        double mdx = mx - (x - tx); //mus klick i relation till spelarens skärmposition
        double mdy = my - (y - ty);

        int k = (int) (mx + tx) / 32;
        int l = (int) (my + ty) / 32;

        int pCol = (int) (x + playerSizeX / 2) / gp.tileSize;
        int pRow = (int) (y + playerSizeY / 2) / gp.tileSize;
        int dRow = 0;
        int dCol = 0;

        if (inventory.getInventorySize() == 0 || slots.size() < selectedSlot || slots.get(selectedSlot).contains(mx, my) || selectedSlot >= inventory.getInventorySize() || inventory.getItem(selectedSlot).getItemType() == ItemType.CONSUMABLE) {
            //returnar tidigt om saken man håller i inte kan slåss
            return;
        }
        attImageRot = 0; //resettar rotationen på itemspriten
        if (Math.abs(mdx) > Math.abs(mdy)) {
            if (mdx > 0) {
                attDir = attackDirection.RIGHT;
                dCol += 1;
            } else {
                attDir = attackDirection.LEFT;
                dCol -= 1;
            }
        } else {
            if (mdy > 0) {
                attDir = attackDirection.DOWN;
                dRow += 1;
            } else {
                attDir = attackDirection.UP;
                dRow -= 1;
            }
        }
        int targetCol = pCol + dCol;
        int targetRow = pRow + dRow;
        Tile lookedTile = getTileLookedAt(k,l);
        boolean canHarvest = canHarvest(lookedTile, inventory.getItem(selectedSlot));
        setAttackImage(inventory.getItem(selectedSlot));
        toolUsage(targetCol,targetRow, lookedTile, canHarvest);
    }

    private void toolUsage(int targetCol, int targetRow, Tile lookedTile, boolean canHarvest) {
        if (canHarvest) {
            harvesting = true;
            tm.setTile((int) targetCol,(int) targetRow, lookedTile.setTile);
        }
    }

    private Tile getTileLookedAt(int x, int y) {
        return tm.getTileNum(x, y);
    }

    private boolean canHarvest(Tile tileLookedAt, Item item) {
        return tileLookedAt.requiredTool == item.getItemType();
    }

    private void setAttackImage(Item item) {
        attImage = item.getHeldIcon();
    }

    public void hurt(int score) {
        if (currHealth > 0) {
            dmgCounter++;
            if (dmgCounter >= 60) { //tappa 1 hp varje sekund
                currHealth -= Math.min(1 + (score / 10), 3);
                dmgCounter = 0;
            }
            if (currHealth <= 5 && dmgCounter % 15 == 0) { //under 5hp, spela low hp ljud 4ggr per sekund
                gp.playSound(SoundEffect.LOWHP);
            }
        } else {
            currHealth = 0;
            playerLose();
        }
    }

    public boolean isDead() {
        return playerLost;
    }

    private void playerLose() {
        direction = Direction.LOST;
        playerLost = true;
        spriteNum = 2;
    }

    private void setStartingValues(Point p) {
        speed = 150;
        x = p.x;
        y = p.y;
        playerCollision = true;
        direction = Direction.IDLE;
    }

    private void setPlayerImage() {
        //förladda sprites när man gör gubben
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/character_up_walk1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/character_up_walk2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/character_down_walk1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/character_down_walk2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/character_left_walk1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/character_left_walk2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/character_right_walk1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/character_right_walk2.png"));
            idle1 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/character_idle1.png"));
            idle2 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/character_idle2.png"));
            lost1 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/character_lose1.png"));
            lost2 = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/character_lose2.png"));
            attDown = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/character_attack_down.png"));
            attLeft = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/character_attack_left.png"));
            attRight = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/character_attack_right.png"));
            attUp = ImageIO.read(getClass().getResourceAsStream("/assets/Animation/character_attack_up.png"));
        } catch (IOException | IllegalArgumentException e) {
            throw new IllegalStateException("no find sprite amigo", e);
        }
    }

    public void updateTool() {

    }

    public void updateAttack() {
        if (attacking == true) {
            attackCounter++;
            switch (attDir) {
                case UP -> {
                    attArea = new Rectangle((int) x + 17, (int) y - 2, 10, 20);
                    attImageRot = Math.toRadians(0);
                }
                case DOWN -> {
                    attArea = new Rectangle((int) x + 17, (int) y + 25, 10, 20);
                    attImageRot = Math.toRadians(180);
                }
                case RIGHT -> {
                    attArea = new Rectangle((int) x + 23, (int) y + 19, 20, 10);
                    attImageRot = Math.toRadians(90);
                }
                case LEFT -> {
                    attArea = new Rectangle((int) x - 12, (int) y + 19, 20, 10);
                    attImageRot = Math.toRadians(270);
                }
            }
            if (attackCounter > 15) {
                attacking = false;
                attackCounter = 0;
            }

        }
    }

    public void move(double dt, double dx, double dy, int worldSizeX, int worldsizeY, TileManager tm) {
        length = Math.sqrt(dx * dx + dy * dy); //1,0 = 1, 0,0 = 0, 1,1 = 1,4

        if (length == 0) {
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
        if (spriteCounter > 15) {
            if (spriteNum == 1) {
                playSound();
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

    }

    private void playSound() {
        if (spriteNum == 1 && length > 0) {
            gp.playSound(SoundEffect.STEP);
        }
    }

    public void draw(Graphics2D g2, Camera camera) {
        BufferedImage image = null;

        if (attacking == true) {
            switch (attDir) {
                case attackDirection.DOWN -> {
                    image = attDown;
                }
                case attackDirection.UP -> {
                    image = attUp;
                }
                case attackDirection.LEFT -> {
                    image = attLeft;
                }
                case attackDirection.RIGHT -> {
                    image = attRight;
                }
            }
        } else {
            switch (direction) {
                case Direction.UP -> {
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                }
                case Direction.DOWN -> {
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                }
                case Direction.RIGHT -> {
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                }
                case Direction.LEFT -> {
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                }
                case Direction.IDLE -> {
                    if (spriteNum == 1) {
                        image = idle1;
                    }
                    if (spriteNum == 2) {
                        image = idle2;
                    }
                }
                case Direction.LOST -> {
                    if (spriteNum == 1) {
                        image = lost1;
                    }
                    if (spriteNum == 2) {
                        image = lost2;
                    }
                }
            }

        }
        int playerX = ((int) x - (int) camera.getTx());
        int playerY = ((int) y - (int) camera.getTy());

        g2.drawImage(image, playerX, playerY, playerSizeX, playerSizeY, null);

        if (attacking) {
            int pivotX = (int) attArea.x - (int) camera.getTx() + attArea.width / 2; //attArea mittpunkt i x
            int pivotY = (int) attArea.y - (int) camera.getTy() + attArea.height / 2; //attArea mittpunkt i y
            AffineTransform oldTrans = g2.getTransform();
            g2.rotate(attImageRot, pivotX, pivotY);
            g2.drawImage(attImage, pivotX - attImage.getWidth(gp) / 2, pivotY - attImage.getHeight(gp) / 2, null);
            g2.setTransform(oldTrans);
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
