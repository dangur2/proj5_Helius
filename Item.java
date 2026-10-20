
import java.awt.Image;
import javax.swing.ImageIcon;

public class Item {
    private final String name;
    private final ItemType type;
    private final Image sprite;
    private int quantity;
    private final int heal;
    private final int dmg;

    public Item(String name, ItemType type, String spritePath, int quantity,int heal, int dmg){
        this.name = name;
        this.type = type;
        this.sprite = new ImageIcon(Item.class.getResource(spritePath)).getImage();
        this.quantity = quantity;
        this.heal = heal;
        this.dmg = dmg;
    }
    public int use(){
        if(type == ItemType.CONSUMABLE){
            return heal;
        }
        return 0;
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
    public int getDmg(){
        return this.dmg;
    }
    public Image getItemImage(){
        return this.sprite;
    }
    
}
