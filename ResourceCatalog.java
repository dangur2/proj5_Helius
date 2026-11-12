
import java.awt.Rectangle;
import java.util.Random;
import javax.imageio.ImageIO;

public class ResourceCatalog {

    private final ResourceType[] resource;
    private final Random r = new Random();
    private final ItemCatalog itemCatalog;

    public ResourceCatalog(ItemCatalog itemCatalog) {
        resource = new ResourceType[5];
        this.itemCatalog = itemCatalog;
        loadGroundResources();
    }

    private void loadGroundResources() {
        try {
            resource[0] = new ResourceType();
            resource[0].name = "rock";
            resource[0].collision = true;
            resource[0].drop = new Item(itemCatalog.getItem(7));
            resource[0].dropAmount = r.nextInt(1,5);
            resource[0].health = 10;
            resource[0].image = ImageIO.read(getClass().getResourceAsStream("/assets/GroundResource/rock_resource.png"));
            resource[0].requiredTool = ItemType.PICKAXE;
            resource[0].ySortOffset = 22;
            resource[0].collArea = new Rectangle(2,21,28,9);

            resource[1] = new ResourceType();
            resource[1].name = "copper rock";
            resource[1].collision = true;
            resource[1].drop = new Item(itemCatalog.getItem(9));
            resource[1].dropAmount = r.nextInt(1,3);
            resource[1].health = 15;
            resource[1].image = ImageIO.read(getClass().getResourceAsStream("/assets/GroundResource/copper_resource.png"));
            resource[1].requiredTool = ItemType.PICKAXE;
            resource[1].ySortOffset = 22;
            resource[1].collArea = new Rectangle(2,21,28,8);

            resource[2] = new ResourceType();
            resource[2].name = "tree stump";
            resource[2].collision = true;
            resource[2].drop = new Item(itemCatalog.getItem(8));
            resource[2].dropAmount = r.nextInt(1,4);
            resource[2].health = 10;
            resource[2].image = ImageIO.read(getClass().getResourceAsStream("/assets/GroundResource/tree_resource.png"));
            resource[2].requiredTool = ItemType.AXE;
            resource[2].ySortOffset = 14;
            resource[2].collArea = new Rectangle(4,13,22,15);

            resource[3] = new ResourceType();
            resource[3].name = "rock";
            resource[3].collision = true;
            resource[3].drop = new Item(itemCatalog.getItem(7));
            resource[3].dropAmount = r.nextInt(1,5);
            resource[3].health = 10;
            resource[3].image = ImageIO.read(getClass().getResourceAsStream("/assets/GroundResource/rock_resource2.png"));
            resource[3].requiredTool = ItemType.PICKAXE;
            resource[3].ySortOffset = 25;
            resource[3].collArea = new Rectangle(2,24,29,7);

            resource[4] = new ResourceType();
            resource[4].name = "tree grown";
            resource[4].collision = true;
            resource[4].drop = new Item(itemCatalog.getItem(8));
            resource[4].dropAmount = r.nextInt(2,6);
            resource[4].health = 15;
            resource[4].image = ImageIO.read(getClass().getResourceAsStream("/assets/GroundResource/grownTree_resource.png"));
            resource[4].requiredTool = ItemType.AXE;
            resource[4].ySortOffset = 102;
            resource[4].collArea = new Rectangle(14,101,22,15);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ResourceType getResource(int x) {
        return resource[x];
    }
    public int getSize(){
        return resource.length;
    }
}
