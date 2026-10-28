
import java.util.Random;
import javax.imageio.ImageIO;

public class ResourceCatalog {

    private final ResourceType[] resource;
    private final Random r = new Random();

    public ResourceCatalog() {
        resource = new ResourceType[3];
        loadGroundResources();
    }

    private void loadGroundResources() {
        try {
            resource[0] = new ResourceType();
            resource[0].name = "rock";
            resource[0].collision = true;
            resource[0].drop = new Item("rock", ItemType.MATERIAL, "/assets/Resource/rock.png", "/assets/Resource/rock.png", 1, 0, 0);
            resource[0].health = 10;
            resource[0].image = ImageIO.read(getClass().getResourceAsStream("/assets/GroundResource/rock_resource.png"));
            resource[0].requiredTool = ItemType.PICKAXE;

            resource[1] = new ResourceType();
            resource[1].name = "copper rock";
            resource[1].collision = true;
            resource[1].drop = new Item("copper ore", ItemType.MATERIAL, "/assets/Resource/copperOre.png", "/assets/Resource/copperOre.png", 1, 0, 0);
            resource[1].health = 15;
            resource[1].image = ImageIO.read(getClass().getResourceAsStream("/assets/GroundResource/copper_resource.png"));
            resource[1].requiredTool = ItemType.PICKAXE;

            resource[2] = new ResourceType();
            resource[2].name = "tree stump";
            resource[2].collision = true;
            resource[2].drop = new Item("log", ItemType.MATERIAL, "/assets/Resource/log.png", "/assets/Resource/log.png", 1, 0, 0);
            resource[2].health = 15;
            resource[2].image = ImageIO.read(getClass().getResourceAsStream("/assets/GroundResource/tree_resource.png"));
            resource[2].requiredTool = ItemType.AXE;

        } catch (Exception e) {

        }

    }

    public ResourceType getResource(int x) {
        return resource[x];
    }
    public int getSize(){
        return resource.length;
    }
}
