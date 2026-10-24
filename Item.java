
import java.awt.Image;
import javax.swing.ImageIcon;

public class Item {
    private final String name;
    private final ItemType type;
    private final Image hotbarSprite;
    private final Image heldSprite;
    private int quantity;
    private final int heal;
    private final int dmg;

    public Item(String name, ItemType type, String hotbarPath, String heldPath, int quantity,int heal, int dmg){
        this.name = name;
        this.type = type;
        this.hotbarSprite = new ImageIcon(Item.class.getResource(hotbarPath)).getImage();
        this.heldSprite = new ImageIcon(Item.class.getResource(heldPath)).getImage();
        this.quantity = quantity;
        this.heal = heal;
        this.dmg = dmg;
    }
    public Item(Item other){
        this.name = other.name;
        this.type = other.type;
        this.hotbarSprite = other.hotbarSprite;
        this.heldSprite = other.heldSprite;
        this.quantity = other.quantity;
        this.heal = other.heal;
        this.dmg = other.dmg;
    }
    
    public int use(){
        if(type == ItemType.CONSUMABLE){
            return heal;
        }
        return 0;
    }
    public int getHeal(){
        return this.heal;
    }
    public void increaseQuantity(int x){
        this.quantity += x;
    }
    public void decreaseQuantity(){
        this.quantity--;
    }
    public int getQuantity(){
        return this.quantity;
    }
    public String getItemName(){
        return this.name;
    }
    public ItemType getItemType(){
        return this.type;
    }
    public boolean isTool(){
        return (this.type == ItemType.AXE || this.type == ItemType.HOE || this.type == ItemType.PICKAXE || this.type == ItemType.SHOVEL) ;
    }
    public int getDmg(){
        return this.dmg;
    }
    public Image getHotbarIcon(){
        return this.hotbarSprite;
    }
    public Image getHeldIcon(){
        return this.heldSprite;
    }
    public String getHoverDescription(){
        return switch(type){
            case CONSUMABLE -> "Heal:" + heal;
            case AXE -> "Chops wood";
            case PICKAXE -> "Mines rock";
            case HOE -> "Tills dirt";
            case SWORD -> "Dmg:"+dmg;
            case SHOVEL -> "Digs sand";
        };
    }
  
    
}
